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
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
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
    private TableColumn tableRegisterValue;

    @FXML
    private TableView<LabelValue> tableRegisters;

    @FXML
    private TableView<LabelValue> tableMemory;

    private Simulator simulator;
    private ListView[] threadListViews;
    private ArrayList<LabelValue[]> threadRegisters;
    private TableViewAnimator tableViewAnimator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewAnimator = new TableViewAnimator();

        btnForward.setOnAction(event -> {

            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();

                simulator.stepForward(id);
                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();
                Instruction nextInstruction = simulator.getMachine().getThread(id).getNextInstruction();

                // Handles case where next instruction is null (there is none)
                if (nextInstruction == null)
                    highlightInstruction(id, pointer, null);
                else
                    // Going backwards must check if previous instruction was atomic (Atomic signifies this)
                    highlightInstruction(id, pointer, nextInstruction.getKeyword());


                updateRegisterView(id, false);
                updateMemoryView();
                updateButtons(id);
            }

        });

        btnBackward.setOnAction(event -> {
            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();
                simulator.stepBackward();

                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();

                // Going backwards must check if previous instruction was atomic
                highlightInstruction(id, pointer, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());


                updateRegisterView(id, false);
                updateMemoryView();
                updateButtons(id);
            }

        });
        btnBackward.setDisable(true);

        threadRegisters = new ArrayList<>();
        for (int i = 0; i < Machine.MAX_THREADS; i++) {
            threadRegisters.add(new LabelValue[SimulatorThread.REGISTERS_PER_THREAD]);
        }

        threadTabPane.getSelectionModel().selectedIndexProperty().addListener(this::onThreadTabChanged);


    }

    private void onThreadTabChanged(ObservableValue<? extends Number> observableValue, Number oldTab, Number newTab) {
        if (newTab.intValue() < simulator.getCurrentProgram().getUsedThreadIDs().length) {
            if (tableRegisters.isDisabled()) tableRegisters.setDisable(false);

            updateRegisterView(newTab.intValue(), true);
            updateButtons(newTab.intValue());
        } else {
            btnBackward.setDisable(true);
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


        if (thread.getInstructionPointer() <= 0) {
            btnBackward.setDisable(true);
        } else {
            btnBackward.setDisable(false);
        }

    }

    private void updateMemoryView() {
        for (Object o : tableMemory.getItems()) {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getMemory().getValue(v.getLocationName());
            v.setValue(newValue);
            if (oldValue != newValue) {
                tableViewAnimator.highlightRowByValueMatch(tableMemory, ((LabelValue) o).getLocationName());
            }

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


        int i = 0;
        for (Object o : tableRegisters.getItems()) {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getThread(id).getRegisters()[i].getValue();
            v.setValue(newValue);
            if (!switchedThreads) {
                if (oldValue != newValue)
                    tableViewAnimator.highlightRowByIndex(tableRegisters, id, i);
            }
            i++;
        }

        if (switchedThreads)
            tableViewAnimator.restoreRegisterHighlighting(tableRegisters, id);

        tableRegisters.refresh();
    }

    void create(Program p, Simulator simulator) {
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
