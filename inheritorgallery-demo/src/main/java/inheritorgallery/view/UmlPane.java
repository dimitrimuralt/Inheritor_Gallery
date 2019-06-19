package inheritorgallery.view;

import inheritorgallery.view.util.UmlClass;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import presentationmodel.uml.UmlPM;

import java.util.ArrayList;

public class UmlPane extends GridPane implements ViewMixin{

    private static Logger logger = LoggerFactory.getLogger(UmlPane.class);

    private final UmlPM model;

    private ArrayList<Label> labels = new ArrayList<>();
    private ArrayList<UmlClass> umlClasses = new ArrayList<>();


    public UmlPane(UmlPM model) {
        this.model = model;
        init();
        logger.info("Finished initializing UmlPane");
    }

    @Override
    public void initializeControls() {

        for (int i=0 ; i < model.getClasses().size(); i++) {
            labels.add(new Label());
            umlClasses.add(new UmlClass());
        }

    }

    @Override
    public void layoutControls() {
        for (int i=0 ; i < model.getClasses().size(); i++) {
            add(labels.get(i), 0,i);
            add(umlClasses.get(i), 1,i);
        }
    }

    @Override
    public void setupBindings() {

        for (int i=0 ; i < model.getClasses().size(); i++) {
            labels.get(i).textProperty().bind(model.getClasses().get(i).nameProperty());
        }
    }

}
