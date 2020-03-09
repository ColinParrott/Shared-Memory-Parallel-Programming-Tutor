package com.colinparrott.parallelsimulator.engine.simulator.gui.tutorial;

import animatefx.animation.Pulse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class PopupController implements Initializable {

    @FXML
    private Label pressSpace;

    @FXML
    private HBox dialogCodePanel1;

    @FXML
    private Polygon dialogCodePanel1Arrow;

    @FXML
    private Label lblDescribeCode1;


    @FXML
    private HBox dialogCodePanel5;

    @FXML
    private Polygon dialogCodePanel5Arrow;

    @FXML
    private Label lblDescribeCode5;

    @FXML
    private HBox dialogCodePanel2;

    @FXML
    private Polygon dialogCodePanel2Arrow;

    @FXML
    private Label lblDescribeCode2;

    @FXML
    private VBox dialogCodePanel3;

    @FXML
    private Polygon dialogCodePanel3Arrow;

    @FXML
    private Label lblDescribeCode3;

    @FXML
    private VBox dialogCodePanel4;

    @FXML
    private Polygon dialogCodePanel4Arrow;

    @FXML
    private Label lblDescribeCode4;

    private static PopupController instance;
    private static GUIControllerTutorial guiController;
    private int tutorialStage = 1;
    private static AtomicBoolean eventsReady;

    private boolean disableSpace = false;

    private static String[] tutLabels = new String[]{
            "null",
            "Click on \"Thread 1\" to see the assembly code for thread 1",
            "Click on \"Code\" to see the psuedocode for the program",
            "This table displays the values of the registers for the currently selected thread. It is hidden when the psuedocode tab is selected",
            "This table displays the values of the variables (memory locations) at the current point in the program execution",
            "This executes the next atomic instruction (highlighted in blue) for the currently selected thread",
            "This undoes the last instruction execution and restores the state of the registers and memory to their previous states",
            "This resets the whole execution back to the start",
            "This box shows the thread IDs of the current execution sequence, with \"[]\" being the initial state",
            "Clicking on a node takes you to that point in the execution. Try it out!",
            "You can load different programs into the simulator. Try loading the atomic version of \"x++\"",
            "Several instructions are highlighted in blue. This means they are part of an atomic block and will be executed in a single step",
            "The psuedocode shows why, the code was declared atomic",
            "Execute the atomic block. Notice the changes in the registers and memory",
            "You can also add your own custom programs to the tool in high-level pseudocode (locally)",
            "These programs will be automatically compiled to assembly. Give it a try!",
            "Any programs you've added are stored locally and can be loaded from here",
            "You can enter challenge mode and try to answer some questions on the currently loaded program",
            "This toggles between the game modes, guess the outcome of a variable and guess the thread execution that could result in a given variable's value",
            "This is the thread execution sequence for the question, your job is to figure out what the value of the chosen variable would be after this execution",
            "Toggle to the guess the sequence outcome",
            "This is a possible value for one of the variables of the currently loaded program, you have to type a valid sequence of thread IDs (ints) that would result in this variable value",
            "This mode has a hint button which will give you parts of a pre-computed solution sequence as you click it",
            "Keep in mind you that can still explore the program whilst in challenge mode to help you find a solution",
            "You can exit challenge mode and go back to the normal exploration mode",
            "You can view a solution to the current question here",
            "gg"


    };

    private static String continueFormatString = "Press SPACE to continue (%d/%d)";


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        eventsReady = new AtomicBoolean(false);
        new Pulse(dialogCodePanel1Arrow).setCycleCount(-1).setSpeed(1.3).play();
        new Pulse(dialogCodePanel2Arrow).setCycleCount(-1).setSpeed(1.3).play();
        new Pulse(dialogCodePanel3Arrow).setCycleCount(-1).setSpeed(1.3).play();
        new Pulse(dialogCodePanel4Arrow).setCycleCount(-1).setSpeed(1.3).play();
        new Pulse(dialogCodePanel5Arrow).setCycleCount(-1).setSpeed(1.3).play();
        instance = this;
        pressSpace.setText(String.format(continueFormatString, tutorialStage, tutLabels.length));

        new Thread(() -> {
            while (guiController == null) {
                guiController = GUIControllerTutorial.getInstance();
//                System.out.println("null!");
                eventsReady.set(true);
            }
        }).start();


        System.out.println("Hello");

    }


    public static PopupController getInstance() {
        return instance;
    }


    @FXML
    private void onKeyPressed(KeyCode keyCode) {
        boolean customMessage = false;
        if (tutorialStage < tutLabels.length && !disableSpace) {

            if (keyCode == KeyCode.SPACE) {
                tutorialStage++;
                System.out.println("Tutorial stage: " + tutorialStage);
                if (tutorialStage <= 3) {
                    lblDescribeCode1.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 4) {
                    dialogCodePanel1.setVisible(false);
                    dialogCodePanel2.setVisible(true);
                    guiController.threadTabPane.getSelectionModel().select(0);
                    guiController.tableRegisters.setEffect(null);
                    lblDescribeCode2.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 5) {
                    dialogCodePanel2.setVisible(false);
                    dialogCodePanel3.setVisible(true);
                    guiController.tableMemory.setEffect(null);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 6) {
                    guiController.btnForwardAllowed = true;
                    guiController.btnForward.setDisable(false);
                    dialogCodePanel3.setLayoutX(guiController.btnForward.getLayoutX() + 15);
                    dialogCodePanel3.setLayoutY(guiController.btnForward.getLayoutY() + 610);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 7) {
                    guiController.btnBackwardAllowed = true;
                    guiController.btnBackward.setDisable(false);
                    dialogCodePanel3.setLayoutX(guiController.btnBackward.getLayoutX() + 15);
                    dialogCodePanel3.setLayoutY(guiController.btnBackward.getLayoutY() + 610);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 8) {
                    guiController.btnResetAllowed = true;
                    guiController.btnReset.setDisable(false);
                    dialogCodePanel3.setVisible(false);
                    dialogCodePanel4.setVisible(true);
                    dialogCodePanel4.setLayoutX(guiController.btnReset.getLayoutX() - 50);
                    dialogCodePanel4.setLayoutY(guiController.btnReset.getLayoutY() - 125);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 9) {
                    guiController.historyNodesAllowed = true;
                    guiController.historyBox.setEffect(null);
                    dialogCodePanel4.setLayoutX(guiController.historyBox.getLayoutX() + 170);
                    dialogCodePanel4.setLayoutY(guiController.historyBox.getLayoutY() - 125);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 10) {
                    guiController.historyNodes.setDisable(false);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 11) {
                    pressSpace.setText("Click button and load <x++> (atomic version)");
                    customMessage = true;
                    disableSpace = true;
                    guiController.btnLoadAllowed = true;
                    guiController.btnLoad.setDisable(false);
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() - 30);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 122);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 12) {
                    dialogCodePanel4.setVisible(false);
                    dialogCodePanel3.setVisible(true);
                    dialogCodePanel3.setLayoutX(guiController.threadTabPane.getLayoutX() + 100);
                    dialogCodePanel3.setLayoutY(guiController.threadTabPane.getLayoutY() + 170);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                    guiController.disableAllButtons();
                } else if (tutorialStage == 13) {
                    guiController.threadTabPane.getSelectionModel().select(guiController.threadTabPane.getTabs().size() - 1);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 14) {
                    guiController.threadTabPane.getSelectionModel().select(0);
                    dialogCodePanel3.setLayoutX(guiController.btnForward.getLayoutX() + 15);
                    dialogCodePanel3.setLayoutY(guiController.btnForward.getLayoutY() + 610);
                    lblDescribeCode3.setText(tutLabels[tutorialStage - 1]);
                    guiController.enableAllButtons();
                    guiController.btnAddProgramWindow.setDisable(true);
                } else if (tutorialStage == 15) {
                    guiController.btnAddProgramWindowAllowed = true;
                    dialogCodePanel3.setVisible(false);
                    dialogCodePanel4.setVisible(true);
                    dialogCodePanel4.setLayoutX(guiController.btnAddProgramWindow.getLayoutX() - 30);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 140);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 16) {
                    guiController.btnAddProgramWindow.setDisable(false);
                    dialogCodePanel4.setLayoutX(guiController.btnAddProgramWindow.getLayoutX() - 30);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 140);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 17) {
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() - 30);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 122);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 18) {
                    dialogCodePanel4.setLayoutX(guiController.btnQuestion.getLayoutX() - 30);
                    dialogCodePanel4.setLayoutY(guiController.btnQuestion.getLayoutY() - 122);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 19) {
                    guiController.btnSubmit.setDisable(true);
                    guiController.btnSubmitOutcome.setDisable(true);
                    guiController.btnShowAnswerOutcome.setDisable(true);
                    guiController.btnShowAnswer.setDisable(true);
                    guiController.btnExploreMode.setDisable(true);
                    guiController.btnExploreMode2.setDisable(true);
                    guiController.modeToggle.setDisable(true);
                    guiController.btnHint.setDisable(true);
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() + 40);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 210);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 20) {
                    dialogCodePanel1.setVisible(true);
                    dialogCodePanel4.setVisible(false);
                    dialogCodePanel1.setLayoutX(guiController.btnLoad.getLayoutX() + 220);
                    dialogCodePanel1.setLayoutY(guiController.btnLoad.getLayoutY() + 10);
                    lblDescribeCode1.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 21) {
                    disableSpace = true;
                    customMessage = true;
                    pressSpace.setText("Click the mode toggle button");
                    dialogCodePanel4.setVisible(true);
                    dialogCodePanel1.setVisible(false);
                    guiController.modeToggle.setDisable(false);
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() + 40);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() - 210);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 22) {
                    disableSpace = false;
                    dialogCodePanel1.setVisible(true);
                    dialogCodePanel4.setVisible(false);
                    dialogCodePanel1.setLayoutX(guiController.btnLoad.getLayoutX() + 220);
                    dialogCodePanel1.setLayoutY(guiController.btnLoad.getLayoutY() + 10);
                    lblDescribeCode1.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 23) {
                    disableSpace = false;
                    dialogCodePanel1.setVisible(false);
                    dialogCodePanel5.setVisible(true);
                    dialogCodePanel5.setLayoutX(guiController.btnLoad.getLayoutX() - 480);
                    dialogCodePanel5.setLayoutY(guiController.btnLoad.getLayoutY() + 100);
                    lblDescribeCode5.setText(tutLabels[tutorialStage - 1]);
                } else if (tutorialStage == 24) {
                    dialogCodePanel5.setVisible(false);
                    dialogCodePanel1.setVisible(true);
                    dialogCodePanel1.setLayoutX(guiController.btnLoad.getLayoutX() - 300);
                    dialogCodePanel1.setLayoutY(guiController.btnLoad.getLayoutY() + 40);
                    lblDescribeCode1.setText(tutLabels[tutorialStage - 1]);
                }
                else if(tutorialStage == 25){
                    dialogCodePanel4.setVisible(true);
                    dialogCodePanel1.setVisible(false);
                    guiController.modeToggle.setDisable(false);
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() - 100);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() + 100);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                }
                else if(tutorialStage == 26){
                    guiController.modeToggle.setDisable(false);
                    dialogCodePanel4.setLayoutX(guiController.btnLoad.getLayoutX() + 100);
                    dialogCodePanel4.setLayoutY(guiController.btnLoad.getLayoutY() + 100);
                    lblDescribeCode4.setText(tutLabels[tutorialStage - 1]);
                }
                else if (tutorialStage == 27) {
                    guiController.btnSubmit.setDisable(false);
                    guiController.btnSubmitOutcome.setDisable(false);
                    guiController.btnShowAnswerOutcome.setDisable(false);
                    guiController.btnShowAnswer.setDisable(false);
                    guiController.btnExploreMode.setDisable(false);
                    guiController.btnExploreMode2.setDisable(false);
                    guiController.modeToggle.setDisable(false);
                    guiController.btnHint.setDisable(false);
                    customMessage = true;
                    dialogCodePanel4.setVisible(false);
                    pressSpace.setText("Congratulations! Tutorial completed");
                }


                if (!customMessage)
                    pressSpace.setText(String.format(continueFormatString, tutorialStage, tutLabels.length));
            }


        }

    }

    public void onKeyPassedPass(KeyEvent ke) {
        onKeyPressed(ke.getCode());
    }

    public void loadedProgramForStage12() {
        disableSpace = false;
        onKeyPressed(KeyCode.SPACE);
    }

    public void challengeModePressed() {
        if (tutorialStage == 18) {
            onKeyPressed(KeyCode.SPACE);
        }
    }

    public void togglePressed() {
        if (tutorialStage == 21) {

            disableSpace = false;
            onKeyPressed(KeyCode.SPACE);
        }
    }


    public int getTutorialStage() {
        return this.tutorialStage;
    }


}