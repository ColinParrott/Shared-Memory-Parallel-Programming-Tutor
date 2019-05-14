package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.MemoryLocation;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
import com.colinparrott.parallelsimulator.engine.instructions.Load;
import com.colinparrott.parallelsimulator.engine.instructions.Store;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.gui.anim.TableViewAnimator;
import com.colinparrott.parallelsimulator.engine.simulator.gui.controls.JFXHistoryButton;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.colinparrott.parallelsimulator.engine.simulator.programs.game.OutcomeCalculator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.GenMethod;
import com.colinparrott.parallelsimulator.engine.simulator.programs.generators.askoutcome.ThreadSequenceGen;
import com.colinparrott.parallelsimulator.programs.ProgramFile;
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;
import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane selectorAnchor;

    @FXML
    private ListView<String> programList;

    @FXML
    private ListView<String> threadOneList;

    @FXML
    private ListView<String> threadTwoList;

    @FXML
    private ListView<String> threadThreeList;

    @FXML
    private ListView<String> threadFourList;

    @FXML
    private ListView<String> codeList;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnBackward;

    @FXML
    private HBox historyBox;

    @FXML
    private HBox gameBox;

    @FXML
    private HBox gameBox2;

    @FXML
    private Label lblChallengeOutcome;

    @FXML
    private Label lblCorrectOutcome;

    @FXML
    private JFXTextField challengeInputFieldSequence;

    @FXML
    private JFXButton btnSubmitOutcome;

    @FXML
    private JFXButton btnShowAnswerOutcome;

    @FXML
    private JFXButton btnExploreMode2;

    @FXML
    private Label lblChallengeSequence;

    @FXML
    private Label lblChallengeQuestion;

    @FXML
    private Label lblCorrect;

    @FXML
    private JFXTextField challengeInputField;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXButton btnExploreMode;

    @FXML
    private JFXButton btnShowAnswer;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnQuestion;

    @FXML
    private Button btnReset;

    @FXML
    private JFXTabPane threadTabPane;

    @FXML
    private JFXNodesList historyNodes;

    @FXML
    private TableColumn tableRegisterValue;

    @FXML
    private TableView<LabelValue> tableRegisters;

    @FXML
    private TableView<LabelValue> tableMemory;


    @FXML
    private Label titleLabel;

    @FXML
    private Label registersTitle;

    @FXML
    private Label memoryTitle;

    @FXML
    private HBox modeBox;

    @FXML
    private JFXToggleButton modeToggle;

    private JFXSnackbar snackbar;

    private Simulator simulator;
    ArrayList<Tab> tabs;
    private ListView<String>[] threadListViews;
    private ArrayList<LabelValue[]> threadRegisters;
    private TableViewAnimator tableViewAnimator;
    private static final Logger logger = LoggerFactory.getLogger(GUIController.class);

    private ArrayList<Program> programs;

    private Stage mainWindow;
    private Stage programSelectorWindow;

    private MemoryLocation chosenVariable;
    private int chosenOutcome;
    private int correctAnswer;
    private int[] correctAnswerSequence;

    @FXML
    private JFXButton btnHint;

    private GameMode gameMode;
    private String hintString = "";
    private String allowedThreadIDs = "";

    enum GameMode
    {
        OUTCOME, SEQUENCE
    }


    public void init() {
        System.out.println("initialise");
        tableViewAnimator = new TableViewAnimator();
        snackbar = new JFXSnackbar(rootPane);

        historyNodes.setSpacing(5);
        historyNodes.setRotate(-90);


        btnBackward.setDisable(true);

        btnReset.setOnAction(e -> {
            rewindSimulator(0);
        });

        btnHint.setOnAction(event -> {
            if (gameMode == GameMode.SEQUENCE)
            {
                if (hintString.length() < correctAnswerSequence.length)
                {
                    hintString = hintString + correctAnswerSequence[hintString.length()];
                    challengeInputFieldSequence.setText(hintString);

                    if (hintString.length() == correctAnswerSequence.length)
                    {
                        onCorrectOutcome();
                    }
                }
            }
        });

        JFXHistoryButton historyNodeButton = new JFXHistoryButton("[]", 0);
//        historyNodeButton.setTooltip(new Tooltip("Reset to initial program state"));
        historyNodeButton.setStyle("-fx-font-size: 20px");
        historyNodeButton.setOnAction(event -> {
            rewindSimulator(historyNodeButton.getMachineStep());
        });

        historyNodeButton.setOnMouseEntered(event -> {
            historyNodeButton.setScaleX(1.1);
            historyNodeButton.setScaleY(1.1);
        });

        historyNodeButton.setOnMouseExited(event -> {
            historyNodeButton.setScaleX(1);
            historyNodeButton.setScaleY(1);
        });

        historyNodeButton.getStyleClass().addAll("animated-option-button");
        historyNodeButton.setButtonType(JFXButton.ButtonType.RAISED);
        historyNodes.addAnimatedNode(historyNodeButton);

        btnForward.setOnAction(event -> {

            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();

                // For checking if await failed
                int oldPointer = simulator.getMachine().getThread(id).getInstructionPointer();

                simulator.stepForward(id);
                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();
                Instruction nextInstruction = simulator.getMachine().getThread(id).getNextInstruction();

                // Handles case where next instruction is null (there is none)
                if (nextInstruction == null)
                    highlightInstruction(id, pointer, null);
                else
                    // Going backwards must check if previous instruction was atomic (Atomic signifies this)
                    highlightInstruction(id, pointer, nextInstruction.getKeyword());


                addHistoryNodes(id);
                updateUIState(id);
                checkAwaitFlash(id, oldPointer);
                checkThreadsFinished();
            }

        });


        challengeInputField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue)
            {
                if (newValue.length() > 9)
                {
                    challengeInputField.setText(newValue.substring(0, 9));
                }
                else if (!newValue.matches("\\d*"))
                {
                    challengeInputField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        challengeInputFieldSequence.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue)
            {
                System.out.println(newValue);
                if (newValue.length() > 15)
                {
                    challengeInputFieldSequence.setText(newValue.substring(0, 15));
                }
                else if (!newValue.matches(String.format("[%s]*", allowedThreadIDs)))
                {
                    System.out.println("shit");
                    challengeInputFieldSequence.setText(newValue.replaceAll(String.format("[^%s]", allowedThreadIDs), ""));
                }

                if (challengeInputFieldSequence.getLength() < hintString.length())
                {
                    challengeInputFieldSequence.setText(hintString);
                }
            }
        });

        btnBackward.setOnAction(event -> {
            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();
                simulator.stepBackward();

                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();

                // Going backwards must check if previous instruction was atomic
                highlightInstruction(id, pointer, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());

                if (historyNodes.getChildren().size() > 1)
                    deleteLastHistoryNode();
                updateUIState(id);

            }

        });
//        btnBackward.setDisable(true);

        threadRegisters = new ArrayList<>();
        for (int i = 0; i < Machine.MAX_THREADS; i++) {
            threadRegisters.add(new LabelValue[SimulatorThread.REGISTERS_PER_THREAD]);
        }

        threadTabPane.getSelectionModel().selectedIndexProperty().addListener(this::onThreadTabChanged);


        btnQuestion.setOnAction(event -> {
            rewindSimulator(0);
            historyBox.setVisible(false);
            btnLoad.setVisible(false);
            btnQuestion.setVisible(false);
            gameBox.setVisible(true);
            modeBox.setVisible(true);
            beginChallengeMode();
        });

        modeToggle.selectedProperty().addListener(((observable, oldValue, newValue) -> {

            // guess outcome
            if (newValue == false)
            {
                rewindSimulator(0);
                boolean switchedModes = gameBox2.isVisible();


                // Only show guess outcome mode if switched from guess sequence mode (rather than when trying to going back to explore mode button which resets toggle to false)
                if (switchedModes)
                {
                    gameBox2.setVisible(false);
                    gameBox.setVisible(true);
                    beginChallengeMode();
                }

            }
            else
            {
                System.out.println("hi");
                rewindSimulator(0);
                gameBox.setVisible(false);
                gameBox2.setVisible(true);
                beginChallengeModeGuessSequence();
            }
        }));

        btnExploreMode2.setOnAction(event -> {
            System.out.println("BACK 2");
            rewindSimulator(0);
            gameBox2.setVisible(false);
            historyBox.setVisible(true);
            btnLoad.setVisible(true);
            btnQuestion.setVisible(true);
            modeToggle.setSelected(false);
            modeBox.setVisible(false);
        });

        btnExploreMode.setOnAction(event -> {
            rewindSimulator(0);
            gameBox.setVisible(false);
            historyBox.setVisible(true);
            btnLoad.setVisible(true);
            btnQuestion.setVisible(true);
//            modeToggle.setSelected(false);
            modeBox.setVisible(false);
        });

        btnShowAnswer.setOnAction(event -> {
            if (btnShowAnswer.getText().equals("Show Answer"))
            {
                challengeInputField.setEditable(false);
                challengeInputField.setText("" + correctAnswer);
                lblChallengeQuestion.setText("The value of " + chosenVariable + " would be:");
                resetGuessStylesheet();
                lblCorrect.setText("");
                btnSubmit.setVisible(false);
                btnShowAnswer.setText("New Challenge");
            }
            else
            {
                beginChallengeMode();
            }

        });

        btnShowAnswerOutcome.setOnAction(event -> {
            if (btnShowAnswerOutcome.getText().equals("Show Answer"))
            {
                challengeInputFieldSequence.setEditable(false);

                StringBuilder answer = new StringBuilder();
                for (int i : correctAnswerSequence) answer.append(i).append(" ");
                challengeInputFieldSequence.setText("" + answer.toString());
                lblChallengeQuestion.setText("A valid sequence would be for " + chosenVariable + " = " + chosenOutcome + ":");
                resetGuessStylesheet();
                lblCorrectOutcome.setText("");
                btnSubmitOutcome.setVisible(false);
                btnShowAnswerOutcome.setText("New Challenge");
            }
            else
            {
                beginChallengeModeGuessSequence();
            }

        });


    }

    @FXML
    private void onKeyPressed(KeyEvent ke)
    {
        if (ke.getCode() == KeyCode.RIGHT)
        {
            btnForward.fire();
        }
        else if (ke.getCode() == KeyCode.LEFT)
        {
            btnBackward.fire();
        }
    }

    private void beginChallengeModeGuessSequence()
    {

        gameMode = GameMode.SEQUENCE;
        hintString = "";
        btnHint.setVisible(true);

        StringBuilder sb = new StringBuilder();
        for (int i : simulator.getCurrentProgram().getUsedThreadIDs())
        {
            sb.append(i);
        }
        allowedThreadIDs = sb.toString();

        ProgramFile programFile = simulator.getCurrentProgram().getProgramFile();
        int[][] interestingSequences = programFile.getInterestingSequences();
        int[] chosenSeq;

        if (interestingSequences.length > 0)
            chosenSeq = programFile.getInterestingSequences()[new Random().nextInt(programFile.getInterestingSequences().length)];
        else
            chosenSeq = ThreadSequenceGen.generateThreadSequence(simulator.getCurrentProgram(), 10, GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);


        correctAnswerSequence = chosenSeq;

        btnSubmitOutcome.setVisible(true);
        btnShowAnswerOutcome.setText("Show Answer");
        btnShowAnswerOutcome.setVisible(true);

        MemoryLocation[] relevantVariables = getRelevantVariables(simulator.getCurrentProgram());
        chosenVariable = relevantVariables[new Random().nextInt(relevantVariables.length)];

        chosenOutcome = OutcomeCalculator.calculateVariableOutcome(chosenVariable, chosenSeq, simulator.getCurrentProgram().getInitialMemory(), simulator.getCurrentProgram());

        lblChallengeOutcome.setText(chosenVariable + " = " + chosenOutcome);
        resetGuessStylesheet();
        challengeInputFieldSequence.clear();
        challengeInputFieldSequence.setEditable(true);
        lblCorrectOutcome.setText("");

        System.out.println("GUESS SEQUENCE");
        System.out.println("CHOSEN VARIABLE: " + chosenVariable);
        System.out.println("CHOSEN VALUE: " + chosenOutcome);

        btnSubmitOutcome.setOnAction(event -> {
            if (challengeInputFieldSequence.getText() != null && challengeInputFieldSequence.getText().length() > 0)
            {
                String input = challengeInputFieldSequence.getText().replaceAll("\\s+", "");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i : chosenSeq) stringBuilder.append(i);
                String answer = stringBuilder.toString().trim();
                int[] seqAnswer = new int[input.length()];

                for (int i = 0; i < input.length(); i++)
                {
                    seqAnswer[i] = Integer.valueOf(String.valueOf(input.charAt(i)));
                }

                int calculatedOutcome = OutcomeCalculator.calculateVariableOutcome(chosenVariable, seqAnswer, simulator.getCurrentProgram().getInitialMemory(), simulator.getCurrentProgram());

                System.out.println("CALCULATED OUTCOME: " + calculatedOutcome);

                if (input.equals(answer) || chosenOutcome == calculatedOutcome)
                {
                    System.out.println("correct!");

                    onCorrectOutcome();
                }
                else
                {
                    System.out.println("incorrect!");

                    // Define the Durations
                    Duration startDuration = Duration.ZERO;
                    Duration endDuration = Duration.millis(100);


                    // Create the start and end Key Frames
                    KeyValue startKeyValue = new KeyValue(challengeInputFieldSequence.translateXProperty(), 2);
                    KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
                    KeyValue endKeyValue = new KeyValue(challengeInputFieldSequence.translateXProperty(), -2);
                    KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);

                    // Create a Timeline
                    Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
                    // Let the animation run forever
                    timeline.setCycleCount(2);
                    // Run the animation
                    timeline.play();

                    onIncorrectOutcome();
                }
            }
        });

    }

    private void beginChallengeMode()
    {
        gameMode = GameMode.OUTCOME;

        ProgramFile programFile = simulator.getCurrentProgram().getProgramFile();
        int[][] interestingSequences = programFile.getInterestingSequences();
        int[] chosenSeq;

        if (interestingSequences.length > 0)
            chosenSeq = programFile.getInterestingSequences()[new Random().nextInt(programFile.getInterestingSequences().length)];
        else
            chosenSeq = ThreadSequenceGen.generateThreadSequence(simulator.getCurrentProgram(), 10, GenMethod.RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS);

        btnSubmit.setVisible(true);
        btnShowAnswer.setText("Show Answer");

        StringBuilder sequenceString = new StringBuilder();
        for (int i : chosenSeq) sequenceString.append(i).append(" ");
        lblChallengeSequence.setText(sequenceString.toString());

        MemoryLocation[] relevantVariables = getRelevantVariables(simulator.getCurrentProgram());
        chosenVariable = relevantVariables[new Random().nextInt(relevantVariables.length)];
        lblChallengeQuestion.setText("What is the final value of " + chosenVariable + "?");

        resetGuessStylesheet();
        challengeInputField.clear();

        challengeInputField.setEditable(true);
        lblCorrect.setText("");

        correctAnswer = OutcomeCalculator.calculateVariableOutcome(chosenVariable, chosenSeq, simulator.getCurrentProgram().getInitialMemory(), simulator.getCurrentProgram());
        btnSubmit.setOnAction(event -> {

            if (challengeInputField.getText() != null && challengeInputField.getText().length() > 0)
            {
                int input = Integer.valueOf(challengeInputField.getText());

                if (input == correctAnswer)
                {
                    System.out.println("correct!");

                    onCorrect();
                }
                else
                {
                    System.out.println("incorrect!");

                    // Define the Durations
                    Duration startDuration = Duration.ZERO;
                    Duration endDuration = Duration.millis(100);


                    // Create the start and end Key Frames
                    KeyValue startKeyValue = new KeyValue(challengeInputField.translateXProperty(), 2);
                    KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
                    KeyValue endKeyValue = new KeyValue(challengeInputField.translateXProperty(), -2);
                    KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);

                    // Create a Timeline
                    Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
                    // Let the animation run forever
                    timeline.setCycleCount(2);
                    // Run the animation
                    timeline.play();

                    onIncorrect();
                }
            }
            // INPUT EMPTY
            else
            {

            }

        });
    }

    private void onCorrect()
    {
        lblCorrect.setText("Correct!");
        lblCorrect.setStyle("-fx-text-fill: #008000");
        challengeInputField.getStylesheets().clear();
        challengeInputField.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield_correct.css").toExternalForm());
        challengeInputField.setEditable(false);
        btnSubmit.setVisible(false);
        btnShowAnswer.setText("New Challenge");
    }

    private void onCorrectOutcome()
    {
        lblCorrectOutcome.setText("Correct!");
        lblCorrectOutcome.setStyle("-fx-text-fill: #008000");
        challengeInputFieldSequence.getStylesheets().clear();
        challengeInputFieldSequence.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield_correct.css").toExternalForm());
        challengeInputFieldSequence.setEditable(false);
        btnSubmitOutcome.setVisible(false);
        btnShowAnswerOutcome.setText("New Challenge");
        btnHint.setVisible(false);

    }

    private void onIncorrect()
    {
        lblCorrect.setText("Incorrect");
        lblCorrect.setStyle("-fx-text-fill: red");
        challengeInputField.getStylesheets().clear();
        challengeInputField.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield_error.css").toExternalForm());

        Duration startDuration = Duration.ZERO;
        Duration endDuration = Duration.millis(100);


        // Create the start and end Key Frames
        KeyValue startKeyValue = new KeyValue(lblCorrect.translateXProperty(), 2);
        KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
        KeyValue endKeyValue = new KeyValue(lblCorrect.translateXProperty(), -2);
        KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);

        // Create a Timeline
        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
        // Let the animation run forever
        timeline.setCycleCount(2);
        // Run the animation
        timeline.play();
    }

    private void onIncorrectOutcome()
    {
        lblCorrectOutcome.setText("Incorrect");
        lblCorrectOutcome.setStyle("-fx-text-fill: red");
        challengeInputFieldSequence.getStylesheets().clear();
        challengeInputFieldSequence.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield_error.css").toExternalForm());

        Duration startDuration = Duration.ZERO;
        Duration endDuration = Duration.millis(100);


        // Create the start and end Key Frames
        KeyValue startKeyValue = new KeyValue(lblCorrectOutcome.translateXProperty(), 2);
        KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
        KeyValue endKeyValue = new KeyValue(lblCorrectOutcome.translateXProperty(), -2);
        KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);

        // Create a Timeline
        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
        // Let the animation run forever
        timeline.setCycleCount(2);
        // Run the animation
        timeline.play();
    }

    private void resetGuessStylesheet()
    {
        challengeInputField.getStylesheets().clear();
        challengeInputField.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield.css").toExternalForm());
        challengeInputFieldSequence.getStylesheets().clear();
        challengeInputFieldSequence.getStylesheets().add(getClass().getClassLoader().getResource("css/textfield.css").toExternalForm());
    }

    private void updateUIState(int id) {
        updateRegisterView(id, false);
        updateMemoryView();
        updateButtons(id);
        titleLabel.setText("Program: " + simulator.getCurrentProgram().getName());
    }

    private void deleteLastHistoryNode() {
        historyNodes.getChildren().remove(historyNodes.getChildren().size() - 1);

        if (historyNodes.getChildren().size() > 0) {
            historyNodes.animateList(false);
            historyNodes.animateList(true);
        }

    }

    private void rewindSimulator(int step) {
        logger.debug("rewindSimulator(" + step + ")");
        if(simulator.getStepsTaken() > 0){
            int numToRewind = simulator.getStepsTaken() - step;
            for (int i = 0; i < numToRewind; i++) {
                simulator.stepBackward();
                historyNodes.getChildren().remove(step + 1, historyNodes.getChildren().size());
            }

            updateUIState(threadTabPane.getSelectionModel().getSelectedIndex());
            for (int i = 0; i < simulator.getCurrentProgram().getUsedThreadIDs().length; i++) {
                System.out.println("thread id: " + i);
                System.out.println(simulator.getMachine().getThread(i) == null);
                if(simulator.getMachine().getThread(i).getNextInstruction() != null)
                    highlightInstruction(i, simulator.getMachine().getThread(i).getInstructionPointer(), simulator.getMachine().getThread(i).getNextInstruction().getKeyword());
            }
        }

    }

    private void addHistoryNodes(int id) {
        JFXHistoryButton historyNodeButton = new JFXHistoryButton(id + "", simulator.getStepsTaken());
        historyNodeButton.setOnAction(event -> {
            rewindSimulator(historyNodeButton.getMachineStep());
        });

        historyNodeButton.getStyleClass().addAll("animated-option-button");
        historyNodeButton.setButtonType(JFXButton.ButtonType.RAISED);

        historyNodeButton.setOnMouseEntered(event -> {
            historyNodeButton.setScaleX(1.1);
            historyNodeButton.setScaleY(1.1);
        });

        historyNodeButton.setOnMouseExited(event -> {
            historyNodeButton.setScaleX(1);
            historyNodeButton.setScaleY(1);
        });


        historyNodes.addAnimatedNode(historyNodeButton);
        historyNodes.animateList(false);
        historyNodes.animateList(true);
    }

    private void checkThreadsFinished() {
        boolean allFinished = true;

        for (int id : simulator.getCurrentProgram().getUsedThreadIDs()) {
            if (simulator.getMachine().getThread(id).getInstructionPointer() < simulator.getMachine().getThread(id).getInstructionsList().size()) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            Node snackbarContent = snackbar.getChildren().get(0);
            snackbarContent.setStyle("-fx-background-color: green");
            snackbar.show("All threads complete!", 3000);
        }
    }

    private void checkAwaitFlash(int id, int oldPointer) {
        SimulatorThread thread = simulator.getMachine().getThread(id);
        if (thread.getInstructionsList().get(oldPointer).getKeyword() == InstructionKeyword.ATOMIC && thread.getInstructionsList().get(oldPointer + 1).getKeyword() == InstructionKeyword.AWAIT) {
            if (thread.getInstructionPointer() == oldPointer) {
                // TODO: Remove and highlight rows
                Node snackbarContent = snackbar.getChildren().get(0);
                snackbarContent.setStyle("-fx-background-color: red");
                snackbar.show("Await failed", 3000);
            }
        }
    }

    private void onThreadTabChanged(ObservableValue<? extends Number> observableValue, Number oldTab, Number
            newTab) {
        if (newTab.intValue() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
            if (tableRegisters.isDisabled()) tableRegisters.setDisable(false);

            highlightInstruction(newTab.intValue(), simulator.getMachine().getThread(newTab.intValue()).getInstructionPointer(), simulator.getMachine().getThread(newTab.intValue()).getNextInstruction().getKeyword());
            updateRegisterView(newTab.intValue(), true);
            updateButtons(newTab.intValue());
        } else {
//            btnBackward.setDisable(true);
            btnForward.setDisable(true);
            tableRegisters.setDisable(true);
        }

    }

    private void updateButtons(int id) {
        SimulatorThread thread = simulator.getMachine().getThread(id);
        if (thread.getInstructionPointer() >= thread.getInstructionsList().size()) {
            btnForward.setDisable(true);
        } else if (btnForward.isDisabled()) {
            btnForward.setDisable(false);
        }

        boolean canGoBackwards = false;

        for(int i : simulator.getCurrentProgram().getUsedThreadIDs()){
            if(simulator.getMachine().getThread(i).getInstructionPointer() > 0){
                canGoBackwards = true;
                break;
            }
        }

        System.out.println(historyNodes.getChildren().size());
        if (canGoBackwards || historyNodes.getChildren().size() > 1)
        {
            btnBackward.setDisable(false);
        }
        else{
            btnBackward.setDisable(true);
        }

//        if (thread.getInstructionPointer() <= 0)
//        {
//            btnBackward.setDisable(true);
//        }
//        else
//        {
//            btnBackward.setDisable(false);
//        }

    }

    private void updateMemoryView() {
        boolean foundUpdated = false;
        for (Object o : tableMemory.getItems()) {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getMemory().getValue(v.getLocationName());
            v.setValue(newValue);
            if (((LabelValue) o).getLocationName().toLowerCase().equals(simulator.getMachine().getMemory().getLastUpdatedLocation().name().toLowerCase())) {
                foundUpdated = true;
                tableViewAnimator.highlightRowByValueMatch(tableMemory, ((LabelValue) o).getLocationName());
            }

        }

        if (!foundUpdated) {
            tableViewAnimator.clearTableHighlighting(tableMemory);
        }


        tableMemory.refresh();
    }

    private MemoryLocation[] getRelevantVariables(Program p) {
        HashSet<MemoryLocation> variables = new HashSet<>();

        for (int i : p.getUsedThreadIDs()) {
            for (Instruction instruction : p.getInstructionsForThread(i)) {
                if (instruction.getKeyword() == InstructionKeyword.ST) {
                    Store s = (Store) instruction;
                    variables.add(s.getMemoryLocation());
                } else if (instruction.getKeyword() == InstructionKeyword.LD) {
                    Load l = (Load) instruction;
                    variables.add(l.getMemoryLocation());
                }
            }
        }

        for (String varStr : p.getProgramFile().getInitialMemory().keySet())
        {
            variables.add(MemoryLocation.valueOf(varStr));
        }

        return variables.toArray(new MemoryLocation[0]);
    }

    private void updateRegisterView(int id, boolean switchedThreads) {
        LabelValue[] vals = new LabelValue[threadRegisters.get(id).length];
        for (int j = 0; j < threadRegisters.get(id).length; j++) {
            vals[j] = new LabelValue("R" + id, simulator.getMachine().getThread(id).getRegisters()[j].getValue());
        }
        threadRegisters.set(id, vals);


//        int i = 0;
//        for (Object o : tableRegisters.getItems())
//        {
//            LabelValue v = (LabelValue) o;
//            int oldValue = v.getValue();
//            int newValue = simulator.getMachine().getThread(id).getRegisters()[i].getValue();
//            v.setValue(newValue);
//            if (!switchedThreads)
//            {
//                if (oldValue != newValue)
//                    tableViewAnimator.highlightRowByIndex(tableRegisters, id, i);
//            }
//            i++;
//        }

        int i = 0;
        for (Object o : tableRegisters.getItems()) {
            LabelValue v = (LabelValue) o;
            int newValue = simulator.getMachine().getThread(id).getRegisters()[i].getValue();
            v.setValue(newValue);
            i++;
        }

        tableViewAnimator.highlightRowByIndex(tableRegisters, id, simulator.getMachine().getThread(id).getLastUpdatedRegister());
        tableRegisters.refresh();
    }

    void create(Simulator simulator, Stage window, Program program) {
        this.mainWindow = window;

        // Close program select window when main window closed
        this.mainWindow.setOnCloseRequest(e -> {
            if(programSelectorWindow != null && programSelectorWindow.isShowing())
                programSelectorWindow.close();
        });

        init();
        programs = ProgramFileReader.readPrograms();
        for (Program p : programs) {
            System.out.println(p.getName());
        }


        Program p;
        if(program == null)
            p = programs.get(0);
        else
            p = program;

        this.simulator = simulator;
        initThreadLists(p);

        int maxThreads = 0;
        for (int i : p.getUsedThreadIDs()) {
            threadListViews[i].setMouseTransparent(true);
            threadListViews[i].setFocusTraversable(false);
            threadListViews[i].getSelectionModel().select(0);
            maxThreads = i;
        }
        System.out.println(maxThreads);

        tabs = new ArrayList<>(threadTabPane.getTabs().subList(0, threadTabPane.getTabs().size() - 1));
        threadTabPane.getTabs().remove(maxThreads + 1, Machine.MAX_THREADS);

        simulator.loadProgram(p);
        titleLabel.setText("Program: " + simulator.getCurrentProgram().getName());
        for (int id : simulator.getCurrentProgram().getUsedThreadIDs()) {
            highlightInstruction(id, 0, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());
        }

        setInitialMemoryTable();

        btnLoad.setOnAction(event -> {
            VBox secondaryLayout = new VBox(30);
            secondaryLayout.setAlignment(Pos.CENTER);
            secondaryLayout.getStylesheets().add(getClass().getClassLoader().getResource("css/main.css").toExternalForm());
            Scene secondScene = new Scene(secondaryLayout, 300, 500);

            ListView<String> list = new ListView<String>();
            int i = 1;
            for (Program pr : programs) {
                list.getItems().add(i + "| " + pr.getName());
                i++;
            }

//            list.setPrefWidth(300);
//            list.setPrefHeight(400);
            secondaryLayout.getChildren().add(list);

            JFXButton button = new JFXButton("Load");
            button.setPrefWidth(150);
            button.setPrefHeight(40);
            button.setAlignment(Pos.CENTER);
            secondaryLayout.getChildren().add(button);

            button.setOnAction(e -> {
//                create(new InternalSimulator(), mainWindow, programs.get(list.getSelectionModel().getSelectedIndex()));
                Program pro = programs.get(list.getSelectionModel().getSelectedIndex());
                simulator.loadProgram(pro);
                reset(pro);
                programSelectorWindow.close();
            });

            // New window (Stage)
            programSelectorWindow = new Stage();
            programSelectorWindow.setTitle("Select Program");
            programSelectorWindow.setScene(secondScene);
            programSelectorWindow.show();
        });
    }

    private void reset(Program p){

        if(threadTabPane.getTabs().size() < p.getUsedThreadIDs().length + 1)
        {
            while(threadTabPane.getTabs().size() < p.getUsedThreadIDs().length + 1){
                int lastIndex = threadTabPane.getTabs().size() - 1;
                Tab codeTab = threadTabPane.getTabs().get(lastIndex);
                threadTabPane.getTabs().remove(lastIndex);
                threadTabPane.getTabs().add(tabs.get(lastIndex));
                threadTabPane.getTabs().add(codeTab);
            }
        }
        else if(threadTabPane.getTabs().size() > p.getUsedThreadIDs().length + 1){
            int lastIndex = threadTabPane.getTabs().size() - 1;
            Tab codeTab = threadTabPane.getTabs().get(lastIndex);
            while(threadTabPane.getTabs().size() > p.getUsedThreadIDs().length){
                threadTabPane.getTabs().remove(threadTabPane.getTabs().size() - 1);
            }
            threadTabPane.getTabs().add(codeTab);
        }

        setInitialMemoryTable();


        for(int i = 0; i < 4; i++){
            updateUIState(i);
        }

        System.out.println(p.getUsedThreadIDs().length);
//        threadTabPane.getTabs().clear();
        initThreadLists(p);
//        threadTabPane.getTabs().remove(p.getUsedThreadIDs().length + 1, p.getu);

        for(int i : p.getUsedThreadIDs()){
            highlightInstruction(i, 0, p.getInstructionsForThread(i).get(0).getKeyword());
        }

        historyNodes.getChildren().remove(1, historyNodes.getChildren().size());
    }


    private void populateProgramList() {
//        programList = new ListView<String>();
        System.out.println(programList == null);
        for (Program p : programs) {
            programList.getItems().add(p.getName());
        }
    }

    private void setInitialMemoryTable() {
        MemoryLocation[] relevantVariables = getRelevantVariables(simulator.getCurrentProgram());
        LabelValue[] rows = new LabelValue[relevantVariables.length];

        int i = 0;
        for (MemoryLocation location : relevantVariables) {
            rows[i] = new LabelValue(location.name(), simulator.getMachine().getMemory().getValue(location));
            i++;
        }

        tableMemory.getItems().clear();
        tableMemory.getItems().addAll(rows);
        tableMemory.refresh();
    }

    private void initThreadLists(Program p) {
        threadOneList.getItems().clear();
        threadTwoList.getItems().clear();
        threadThreeList.getItems().clear();
        threadFourList.getItems().clear();

        threadListViews = new ListView[p.getUsedThreadIDs().length];
        for (int i : p.getUsedThreadIDs()) {
            ListView<String> chosen = null;

            switch (i) {
                case 0:
                    chosen = threadOneList;
                    break;
                case 1:
                    chosen = threadTwoList;
                    break;
                case 2:
                    chosen = threadThreeList;
                    break;
                case 3:
                    chosen = threadFourList;
                    break;
            }

            threadListViews[i] = chosen;
//            threadTabPane.getTabs().


            boolean foundLabel = false;
            for (Instruction instruction : p.getInstructionsForThread(i)) {
                String mainString = instruction.toString().replace(" ", "\t");
                if (instruction.getKeyword() != InstructionKeyword.LABEL) {
                    if (foundLabel) {
                        chosen.getItems().add("\t" + mainString);
                        System.out.println(mainString);
                    } else {
                        chosen.getItems().add(mainString);
                    }
                } else {
                    chosen.getItems().add(mainString);
                    foundLabel = true;
                }


            }

            chosen.getItems().add(" ");
            chosen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
        codeList.getItems().clear();
        codeList.getItems().addAll(p.getHighLevelCodeLines());
    }

    private void highlightInstruction(int threadID, int instructionNumber, InstructionKeyword keyword) {
        logger.debug("highlightInstruction(" + threadID + ")");
        threadListViews[threadID].getSelectionModel().clearSelection();
        if (keyword != InstructionKeyword.ATOMIC && keyword != InstructionKeyword.ENDATOMIC) {
            threadListViews[threadID].getSelectionModel().select(instructionNumber);
        } else {
            SimulatorThread thread = simulator.getMachine().getThread(threadID);
            int min = instructionNumber;
            int max = instructionNumber;

            if (keyword == InstructionKeyword.ATOMIC) {

                for (int i = instructionNumber; i < thread.getInstructionsList().size(); i++) {
                    if (thread.getInstructionsList().get(i).getKeyword() == InstructionKeyword.ENDATOMIC) break;
                    max = i + 1;
                }

                threadListViews[threadID].getSelectionModel().selectRange(min, max + 1);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
