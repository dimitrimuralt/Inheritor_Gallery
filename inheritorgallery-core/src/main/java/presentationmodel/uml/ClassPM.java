package presentationmodel.uml;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.jshell.dto.ConstructorDTO;
import service.jshell.dto.FieldDTO;
import service.jshell.dto.MethodDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Presentationmodel that stores the current state of a class
 */
public class ClassPM {

    private static Logger logger = LoggerFactory.getLogger(ClassPM.class);

    private final BooleanProperty isAbstract = new SimpleBooleanProperty();
    private final IntegerProperty inheritanceLevel = new SimpleIntegerProperty();
    private final BooleanProperty isInterface = new SimpleBooleanProperty();
    private final StringProperty fullClassName = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty superClassName = new SimpleStringProperty();
    private final ObjectProperty<ClassPM> superClass = new SimpleObjectProperty<>();
    private final ObservableList<ClassPM> implementedInterfaces = FXCollections.observableArrayList();
    private final ObservableList<String> implementedInterfacesAsString = FXCollections.observableArrayList();
    private final ObservableList<FieldPM> fields = FXCollections.observableArrayList();
    private final ObservableList<ConstructorPM> constructors = FXCollections.observableArrayList();
    private final ObservableList<MethodPM> methods = FXCollections.observableArrayList();

    /**
     * Create the ClassPM
     * @param isAbstract Mark the ClassPM as abstract
     * @param isInterface Mark the ClassPM as interface
     * @param fullClassName The full name including package of the class
     * @param name The simple name of the class
     * @param superClassName The name of the class inherited from
     * @param superclass The full name of the class inherited from
     * @param implementedInterfacesAsString The interfaces the class implements in String form
     * @param implementedInterfaces The interfaces the class implements as ClassPMs
     * @param fields The fields the class implements
     * @param constructors The constructors the class implements
     * @param methods The methods the class implements
     */
    public ClassPM(
            Boolean isAbstract,
            Boolean isInterface,
            String fullClassName,
            String name,
            String superClassName,
            ClassPM superclass,
            List<String> implementedInterfacesAsString,
            List<ClassPM> implementedInterfaces,
            List<FieldDTO> fields,
            List<ConstructorDTO> constructors,
            List<MethodDTO> methods) {

        setIsAbstract(isAbstract);
        setIsInterface(isInterface);
        setFullClassName(fullClassName);
        setName(name);
        setSuperClassName(superClassName);
        this.implementedInterfacesAsString.addAll(implementedInterfacesAsString);
        if(implementedInterfaces != null)
            for(ClassPM classPM : implementedInterfaces) addInterface(classPM);

        for(FieldDTO f : fields){
            this.fields.add(new FieldPM(
                    f.getDeclaringClass(),
                    f.getModifier(),
                    f.getType(),
                    f.getName(),
                    null));}

        for(ConstructorDTO c : constructors){
            this.constructors.add((new ConstructorPM(
                    c.getModifier(),
                    c.getName(),
                    c.getInputParameters())));}

        for(MethodDTO m : methods){this.methods.add((new MethodPM(
                this,
                m.getModifier(),
                m.getReturnType(),
                m.getName(),
                m.getInputParameters())));}
    }

    /**
     * Clone the ClassPM
     * @return A new ClassPM
     */
    @Override
    public ClassPM clone(){
        List<FieldDTO> fields = new ArrayList<>();
        List<ConstructorDTO> constructors  = new ArrayList<>();
        List<MethodDTO> methods  = new ArrayList<>();
        for(FieldPM f : getFields())
            fields.add(new FieldDTO(f.getDeclaringClass(),f.getModifier(),f.getType(),f.getName(),f.getValue()));
        for(ConstructorPM c : getConstructors())
            constructors.add(new ConstructorDTO(c.getModifier(),c.getName(),c.getInputParameters()));
        for(MethodPM m : getMethods())
            methods.add(new MethodDTO(m.getModifier(), m.getReturnType(),m.getName(),m.getInputParameters()));
        List<ClassPM> implementedInterfaces = new ArrayList<>(getImplementedInterfaces());

        return new ClassPM(
                this.isAbstract.getValue(),
                this.isInterface.getValue(),
                this.fullClassName.getValue(),
                this.name.getValue(),
                this.superClassName.getValue(),
                superClass.getValue() != null ? this.superClass.getValue().clone() : null,
                this.implementedInterfacesAsString,
                implementedInterfaces,
                fields,
                constructors,
                methods
        );
    }

    /**
     * Check for a class that the current class might inherit from
     * @return
     */
    public boolean hasSuperClass(){
        return this.getSuperClassName() != null && !this.getSuperClassName().equals("java.lang.Object");
    }

    public int getInheritanceLevel() {
        return inheritanceLevel.get();
    }

    public IntegerProperty inheritanceLevelProperty() {
        return inheritanceLevel;
    }

    public void setInheritanceLevel(int inheritanceLevel) {
        this.inheritanceLevel.set(inheritanceLevel);
    }

    public boolean isIsAbstract() {
        return isAbstract.get();
    }

    public BooleanProperty isAbstractProperty() {
        return isAbstract;
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract.set(isAbstract);
    }

    public boolean isIsInterface() {
        return isInterface.get();
    }

    public BooleanProperty isInterfaceProperty() {
        return isInterface;
    }

    public void setIsInterface(boolean isInterface) {
        this.isInterface.set(isInterface);
    }

    public String getFullClassName() {
        return fullClassName.get();
    }

    public StringProperty fullClassNameProperty() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName.set(fullClassName);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSuperClassName() {
        return superClassName.get();
    }

    public StringProperty superClassNameProperty() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName.set(superClassName);
    }

    public ClassPM getSuperClass() {
        return superClass.get();
    }

    public ObjectProperty<ClassPM> superClassProperty() {
        return superClass;
    }

    public void setSuperClass(ClassPM superClass) {
        this.superClass.set(superClass);
    }

    public ObservableList<String> getImplementedInterfacesAsString() {
        return implementedInterfacesAsString;
    }

    public ObservableList<ClassPM> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public void setImplementedInterfaces(List<ClassPM> interfaceList) {
        this.implementedInterfaces.clear();
        this.implementedInterfaces.addAll(interfaceList);
    }

    public void addInterface(ClassPM classPM) {
        this.implementedInterfaces.add(classPM);
    }

    public ObservableList<FieldPM> getFields() {
        return fields;
    }

    public ObservableList<ConstructorPM> getConstructors() {
        return constructors;
    }

    public ObservableList<MethodPM> getMethods() {
        return methods;
    }
}
