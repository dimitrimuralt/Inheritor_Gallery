package inheritorgallery.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import presentationmodel.uml.UmlPM;

public class ApplicationUI extends BorderPane implements ViewMixin {

    private static Logger logger = LoggerFactory.getLogger(ApplicationUI.class);

    private UmlPM model;

    private InteractionPane interactionPane;
    private InstancePane instancePane;
    private UmlPane umlPane;

    public ApplicationUI(UmlPM model) {
        this.model = model;
        init();
        logger.info("Finished initializing ApplicationUI");
    }

    @Override
    public void initializeControls() {
        // Initialize panes
        interactionPane = new InteractionPane();
        instancePane = new InstancePane();
        umlPane = new UmlPane(model);
    }

    @Override
    public void layoutControls() {
        // Set IDs
        interactionPane.setId("interactionPane");
        instancePane.setId("instancePane");
        umlPane.setId("umlPane");

        ScrollPane umlScrollPane = new ScrollPane(umlPane);
        umlScrollPane.setPannable(true);
        umlScrollPane.setPrefWidth(600);

        // Layouts
        this.setLeft(interactionPane);
        this.setCenter(instancePane);
        this.setRight(umlScrollPane);

    }
}