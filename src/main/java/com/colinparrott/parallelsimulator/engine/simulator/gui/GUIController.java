package com.colinparrott.parallelsimulator.engine.simulator.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable
{
    @FXML
    private TextFlow textFlow;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Text text = new Text("SEQUENCE: 0 1 2 0 1");
        text.setFont(new Font(32));
        textFlow.getChildren().add(text);
    }
}
