package com.colinparrott.parallelsimulator.engine.simulator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GUISimulator extends Application
{


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));

        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("PP Tutor");
        stage.setScene(scene);
        stage.show();
    }


}
