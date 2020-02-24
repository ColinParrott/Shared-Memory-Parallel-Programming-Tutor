package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.gui.tutorial.GUIControllerTutorial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GUISimulator extends Application {

    private GUIController guiController;
    private GUIControllerTutorial guiControllerTutorial;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        boolean tutorialMode = true;
        String fxmlFile = tutorialMode ? "fxml/main_tutorial.fxml" : "fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(fxmlFile)));
        Parent root = loader.load();


        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("PP Tutor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        root.requestFocus();

        if (!tutorialMode) {
            guiController = loader.getController();
            guiController.create(new InternalSimulator(), stage, null, tutorialMode);
        } else {
            guiControllerTutorial = loader.getController();
            Popup popup = new Popup();
            CustomController controller = new CustomController();
            FXMLLoader tloader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/tutorial_overlay.fxml")));
//            tloader.setController(controller);
            popup.getContent().add((Parent)tloader.load());
            popup.show(stage);
            guiControllerTutorial.create(new InternalSimulator(), stage, null, tutorialMode);
        }


    }

    private class InternalSimulator extends Simulator {

    }


    private class CustomController {
    }
}
