package presentationmodel.uml;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.jshell.JShellService;
import service.jshell.dto.ClassDTO;

import java.util.*;
import java.util.stream.Collectors;

public class UmlPM {

    private static Logger logger = LoggerFactory.getLogger(UmlPM.class);
    private final ObjectProperty<List<ClassPM>> classesObject = new SimpleObjectProperty<>();
    private final ObservableList<ClassPM> classes = FXCollections.observableArrayList();
    private final ObservableList<EdgePM> edges = FXCollections.observableArrayList();
    private final IntegerProperty inheritanceDeepness = new SimpleIntegerProperty();
    private JShellService jshellService = JShellService.getInstance();

    public UmlPM() {
        init();
    }

    public void init(){
        classes.clear();
        edges.clear();

        for(ClassDTO c : jshellService.getClassDTOs()){
            classes.add(new ClassPM(
                    c.isAbstract(),
                    c.isInterface(),
                    c.getFullClassName(),
                    c.getSimpleClassName(),
                    c.getSuperClassName(),
                    null, //superclass ClassPM has to be created first, superclass is added below
                    c.getImplementedInterfaces(),
                    null,//interfaces ClassPM have to be created first, added below
                    c.getFields(),
                    c.getConstructors(),
                    c.getMethods()
            ));
        }
        for(ClassPM classPM : classes){ //add superclass ClassPM from superclass name
            classPM.setSuperClass(getClassByFullName(classPM.getSuperClassName()));
        }

        for(ClassPM classPM : classes){ //add implemented Interfaces ClassPM
            for(String implementedInterface : classPM.getImplementedInterfacesAsString())
                classPM.addInterface(getClassByFullName(implementedInterface));
        }

        setClassInheritanceLevelToHashMap(classes);

        edges.addAll(getEdgesForClasses(classes));

        setClassesObject(classes);
    }




    public ClassPM getClassByName(String s){
        Optional<ClassPM> targetClass =
                classes.stream().filter(c -> c.getName().equals(s)).findFirst();
        return targetClass.orElse(null);
    }

    public ClassPM getClassByFullName(String s){
        Optional<ClassPM> targetClass =
                classes.stream().filter(c -> c.getFullClassName().equals(s)).findFirst();
        return targetClass.orElse(null);
    }

    private void setClassInheritanceLevelToHashMap(List<ClassPM> classes){
        List<ClassPM> classesLevelNotSetYet = new ArrayList<>(classes);

        int i = 1;

        while(!classesLevelNotSetYet.isEmpty()){
            i--;

            List<String> classesExtended = classesLevelNotSetYet.stream()
                    .filter(e -> e.hasSuperClass())
                    .map(e -> e.getSuperClassName())
                    .distinct()
                    .collect(Collectors.toList());

            List<String> classesImplemented = classesLevelNotSetYet.stream()
                    .flatMap(e -> e.getImplementedInterfacesAsString().stream())
                    .collect(Collectors.toList());

            List<String> classesExtendedOrImplemented = new ArrayList<>(classesExtended);
            classesExtendedOrImplemented.addAll(classesImplemented);

            List<ClassPM>  classesNeverInherited = classesLevelNotSetYet.stream()
                    .filter(c -> !classesExtendedOrImplemented.contains(c.getFullClassName()))
                    .collect(Collectors.toList());

            for(ClassPM c : classesNeverInherited){
                c.setInheritanceLevel(i);
                classesLevelNotSetYet.remove(c);
            }

        }

        setInheritanceDeepness(i * -1);
        classes.forEach(c -> c.setInheritanceLevel(c.getInheritanceLevel()+getInheritanceDeepness()));
    }

    private List<EdgePM> getEdgesForClasses(List<ClassPM> classes) {
        List<EdgePM> edgePMs = new ArrayList<>();

        for(ClassPM clazz : classes) {
            ClassPM superClass =   getClassByFullName(clazz.getSuperClassName());
            //Todo: Include Object class
            if(superClass != null && !superClass.getName().equals("Object"))
                edgePMs.add(new EdgePM(clazz.getName(),superClass.getName(),"extends"));

            List<ClassPM> implementedInterfaces = clazz.getImplementedInterfacesAsString().stream()
                    .map(e -> getClassByFullName(e))
                    .collect(Collectors.toList());

            for (ClassPM implementedInterface : implementedInterfaces)
                edgePMs.add(new EdgePM(clazz.getName(),implementedInterface.getName(),"implements"));
        }
        return edgePMs;
    }

    public ObservableList<ClassPM> getClasses() {
        return classes;
    }

    public ObservableList<EdgePM> getEdges() {
        return edges;
    }

    public int getInheritanceDeepness() {
        return inheritanceDeepness.get();
    }

    public IntegerProperty inheritanceDeepnessProperty() {
        return inheritanceDeepness;
    }

    public void setInheritanceDeepness(int inheritanceDeepness) {
        this.inheritanceDeepness.set(inheritanceDeepness);
    }

    public List<ClassPM> getClassesObject() {
        return classesObject.get();
    }

    public ObjectProperty<List<ClassPM>> classesObjectProperty() {
        return classesObject;
    }

    public void setClassesObject(List<ClassPM> classesObject) {
        this.classesObject.set(classesObject);
    }
}
