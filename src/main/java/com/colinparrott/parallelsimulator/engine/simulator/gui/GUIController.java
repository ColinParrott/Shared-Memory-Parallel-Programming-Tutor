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
import com.colinparrott.parallelsimulator.programs.ProgramFileReader;
import com.colinparrott.parallelsimulator.programs.parser.AssemblyParser;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    @FXML
    private AnchorPane rootPane;

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
    private JFXTabPane threadTabPane;

    @FXML
    private JFXNodesList historyNodes;

    @FXML
    private TableColumn tableRegisterValue;

    @FXML
    private TableView<LabelValue> tableRegisters;

    @FXML
    private TableView<LabelValue> tableMemory;

    private JFXSnackbar snackbar;

    private Simulator simulator;
    private ListView<String>[] threadListViews;
    private ArrayList<LabelValue[]> threadRegisters;
    private TableViewAnimator tableViewAnimator;
    private static final Logger logger = LoggerFactory.getLogger(GUIController.class);

    private ArrayList<Program> programs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewAnimator = new TableViewAnimator();
        snackbar = new JFXSnackbar(rootPane);



        historyNodes.setSpacing(5);
        historyNodes.setRotate(-90);

        JFXHistoryButton historyNodeButton = new JFXHistoryButton("Initial", 0);
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


                updateUIState(id);
                checkAwaitFlash(id, oldPointer);
                checkThreadsFinished();
                addHistoryNodes(id);
            }

        });

        btnBackward.setOnAction(event -> {
            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();
                simulator.stepBackward();

                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();

                // Going backwards must check if previous instruction was atomic
                highlightInstruction(id, pointer, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());


                updateUIState(id);
                if (historyNodes.getChildren().size() > 1)
                    deleteLastHistoryNode();
            }

        });
//        btnBackward.setDisable(true);

        threadRegisters = new ArrayList<>();
        for (int i = 0; i < Machine.MAX_THREADS; i++) {
            threadRegisters.add(new LabelValue[SimulatorThread.REGISTERS_PER_THREAD]);
        }

        threadTabPane.getSelectionModel().selectedIndexProperty().addListener(this::onThreadTabChanged);


    }

    private void updateUIState(int id) {
        updateRegisterView(id, false);
        updateMemoryView();
        updateButtons(id);
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
        int numToRewind = simulator.getStepsTaken() - step;
        for (int i = 0; i < numToRewind; i++) {
            simulator.stepBackward();
            historyNodes.getChildren().remove(step + 1, historyNodes.getChildren().size());
        }

        updateUIState(threadTabPane.getSelectionModel().getSelectedIndex());
        for (int i = 0; i < simulator.getMachine().numberUsedThreads(); i++) {
            highlightInstruction(i, simulator.getMachine().getThread(i).getInstructionPointer(), simulator.getMachine().getThread(i).getNextInstruction().getKeyword());
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

    void create(Simulator simulator) {
        programs = ProgramFileReader.readPrograms();
        for(Program p : programs){
            System.out.println(p.getName());
        }
        Program p = programs.get(0);
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


        threadTabPane.getTabs().remove(maxThreads + 1, Machine.MAX_THREADS);

        simulator.loadProgram(p);
        for (int id : simulator.getCurrentProgram().getUsedThreadIDs()) {
            highlightInstruction(id, 0, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());
        }

        setInitialMemoryTable();
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
        codeList.getItems().addAll(p.getHighLevelCodeLines());
    }

    private void highlightInstruction(int threadID, int instructionNumber, InstructionKeyword keyword) {
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


}
