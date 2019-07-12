package inheritorgallery.view.instances;


import inheritorgallery.view.ViewMixin;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import presentationmodel.instance.ObjectPM;
import presentationmodel.instance.ReferencePM;
import presentationmodel.uml.ClassPM;

import java.util.ArrayList;
import java.util.List;


public class ObjectUnit extends VBox implements ViewMixin {
    private static Logger logger = LoggerFactory.getLogger(ObjectUnit.class);
    private List<Label> references;
    private List<ObjectPartUnit> objectParts;
    private ObjectPM model;

    public ObjectUnit(ObjectPM model){
        this.model = model;
        init();
    }

    @Override
    public void initializeControls() {
        //objectLabels = new ArrayList<>();
        objectParts = new ArrayList<>();
        references = new ArrayList<>();

        for(ClassPM part : model.getObjectParts())
            objectParts.add(new ObjectPartUnit(part));
            //objectLabels.add(new Label(part.getFullClassName()));

        for(ReferencePM referencePM : model.getReferences()){
            logger.info(referencePM.getReferenceName());
            references.add(new Label(referencePM.getReferenceName()));
        }
    }

    @Override
    public void layoutControls() {

        if(references != null ) getChildren().addAll(references);
        getChildren().addAll(objectParts);
    }

    @Override
    public void setupBindings() {
        //objectLabel.textProperty().bind(model.objectFullNameProperty());
    }
}
