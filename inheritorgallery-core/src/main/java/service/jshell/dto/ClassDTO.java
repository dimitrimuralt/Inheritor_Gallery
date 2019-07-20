package service.jshell.dto;

import java.io.Serializable;
import java.util.List;

public class ClassDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Boolean isAbstract;
    private final Boolean isInterface;
    private final String fullClassName;
    private final String simpleClassName;
    private final String superClassName;
    private final List<String> implementedInterfaces;
    private final List<FieldDTO> fields;
    private final List<ConstructorDTO> constructors;
    private final List<MethodDTO> methods;

    public ClassDTO(Boolean isAbstract,
                    Boolean isInterface,
                    String fullClassName,
                    String simpleClassName,
                    String superClassName,
                    List<String> implementedInterfaces,
                    List<FieldDTO> fields,
                    List<ConstructorDTO> constructors,
                    List<MethodDTO> methods){

        this.isAbstract = isAbstract;
        this.isInterface = isInterface;
        this.fullClassName = fullClassName;
        this.simpleClassName = simpleClassName;
        this.superClassName = superClassName;
        this.implementedInterfaces = implementedInterfaces;
        this.fields = fields;
        this.constructors = constructors;
        this.methods = methods;
    }

    public Boolean isAbstract() {
        return isAbstract;
    }

    public Boolean isInterface() {
        return isInterface;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public List<String> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public List<FieldDTO> getFields() {
        return fields;
    }

    public List<ConstructorDTO> getConstructors() {
        return constructors;
    }

    public List<MethodDTO> getMethods() {
        return methods;
    }
}