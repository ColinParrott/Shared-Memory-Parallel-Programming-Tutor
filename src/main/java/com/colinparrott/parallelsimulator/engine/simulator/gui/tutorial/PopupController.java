package com.colinparrott.parallelsimulator.engine.simulator.gui.tutorial;

import animatefx.animation.Pulse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {

    @FXML
    private HBox dialogCodePanel1;

    @FXML
    private Polygon dialogCodePanel1Arrow;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Pulse(dialogCodePanel1Arrow).setCycleCount(-1).setSpeed(1.3).play();
    }


}
