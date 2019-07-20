package service.jshell.dto;

import java.io.Serializable;
import java.util.List;

public class ObjectDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String objectId;
    private String objectFullName; //e.g. Car, instatiated with "... = new Car()"
    private List<FieldDTO> fieldValues;

    public ObjectDTO(String objectId, String objectFullName, List<FieldDTO> fieldValues){
        this.objectFullName = objectFullName;
        this.objectId = objectId;
        this.fieldValues = fieldValues;
    }

    public String getObjectFullName() {
        return objectFullName;
    }
    public String getObjectId() {
        return objectId;
    }

    public List<FieldDTO> getFieldValues() {
        return fieldValues;
    }
}