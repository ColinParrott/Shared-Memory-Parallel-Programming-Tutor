package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.ProgramList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GUISimulator extends Application
{

    private GUIController guiController;

    public static void main(String[] args)
    {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));
        Parent root = loader.load();
        guiController = loader.getController();

        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("PP Tutor");
        stage.setScene(scene);
        stage.show();

        Program p = new ProgramList().loadXPlusPlusAtomicTwoThreads();
        guiController.create(p, new InternalSimulator());

    }

    private class InternalSimulator extends Simulator
    {

    }


}
