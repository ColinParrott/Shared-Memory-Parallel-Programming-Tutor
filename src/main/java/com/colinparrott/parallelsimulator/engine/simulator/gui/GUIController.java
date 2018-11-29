package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.instructions.InstructionKeyword;
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
import java.util.ResourceBundle;

public class GUIController implements Initializable
{
    @FXML
    private TextFlow textFlow;

    @FXML
    private ListView<String> threadOneList;

    @FXML
    private ListView<String> threadTwoList;

    @FXML
    private ListView<String> threadThreeList;

    @FXML
    private ListView<String> threadFourList;

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

    @FXML
    private TableView tableMemoryTwo;

    private Simulator simulator;
    private ListView[] threadListViews;
    private ArrayList<LabelValue[]> threadRegisters;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Text text = new Text("Sequence History: ");
        text.setFont(new Font(32));
        textFlow.getChildren().add(text);


        btnForward.setOnAction(event -> {

            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length)
            {
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


                updateRegisterView(id);
                updateMemoryView();
            }

        });

        btnBackward.setOnAction(event -> {
            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length)
            {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();
                simulator.stepBackward();

                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();

                // Going backwards must check if previous instruction was atomic
                highlightInstruction(id, pointer, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());


                updateRegisterView(id);
                updateMemoryView();
            }

        });

        threadRegisters = new ArrayList<>();
        for (int i = 0; i < Machine.MAX_THREADS; i++)
        {
            threadRegisters.add(new LabelValue[SimulatorThread.REGISTERS_PER_THREAD]);
        }

        threadTabPane.getSelectionModel().selectedIndexProperty().addListener(this::onThreadTabChanged);


    }

    private void onThreadTabChanged(ObservableValue<? extends Number> observableValue, Number oldTab, Number newTab)
    {
        if (newTab.intValue() < simulator.getCurrentProgram().getUsedThreadIDs().length)
            updateRegisterView(newTab.intValue());

    }

    private void updateMemoryView()
    {
        for (Object o : tableMemory.getItems())
        {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getMemory().getValue(v.getLocationName());
            v.setValue(newValue);
            if (oldValue != newValue)
            {
                System.out.println("meme");
                TableViewAnimator.highlightRowByValueMatch(tableMemory, ((LabelValue) o).getLocationName(), oldValue);
            }


        }

        for (Object o : tableMemoryTwo.getItems())
        {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getMemory().getValue(v.getLocationName());
            v.setValue(newValue);
            if (oldValue != newValue)
            {
                System.out.println("meme2");
                TableViewAnimator.highlightRowByValueMatch(tableMemoryTwo, ((LabelValue) o).getLocationName(), oldValue);
            }

        }

        tableMemory.refresh();
        tableMemoryTwo.refresh();
    }

    private void updateRegisterView(int id)
    {
        LabelValue[] vals = new LabelValue[threadRegisters.get(id).length];
        for (int j = 0; j < threadRegisters.get(id).length; j++)
        {
            vals[j] = new LabelValue("R" + id, simulator.getMachine().getThread(id).getRegisters()[j].getValue());
        }
        threadRegisters.set(id, vals);


        int i = 0;
        for (Object o : tableRegisters.getItems())
        {
            LabelValue v = (LabelValue) o;
            int oldValue = v.getValue();
            int newValue = simulator.getMachine().getThread(id).getRegisters()[i].getValue();
            v.setValue(newValue);
            if (oldValue != newValue)
                TableViewAnimator.highlightRowByIndex(tableRegisters, i);
            i++;
        }

        tableRegisters.refresh();
    }

    void create(Program p, Simulator simulator)
    {
        this.simulator = simulator;
        initThreadLists(p);

        for (int i : p.getUsedThreadIDs())
        {
            threadListViews[i].setMouseTransparent(true);
            threadListViews[i].setFocusTraversable(false);
            threadListViews[i].scrollTo(0);
            threadListViews[i].getSelectionModel().select(0);
        }

        simulator.loadProgram(p);

        for (int id : simulator.getCurrentProgram().getUsedThreadIDs())
        {
            highlightInstruction(id, 0, simulator.getMachine().getThread(id).getNextInstruction().getKeyword());
        }
    }

    private void initThreadLists(Program p)
    {
        threadListViews = new ListView[p.getUsedThreadIDs().length];
        for (int i : p.getUsedThreadIDs())
        {
            ListView<String> chosen = null;

            switch (i)
            {
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

            for (Instruction instruction : p.getInstructionsForThread(i))
            {
                chosen.getItems().add(instruction.toString().replace(" ", "\t"));
            }

            chosen.getItems().add(" ");
            chosen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    private void highlightInstruction(int threadID, int instructionNumber, InstructionKeyword keyword)
    {
        threadListViews[threadID].getSelectionModel().clearSelection();
        if (keyword != InstructionKeyword.ATOMIC && keyword != InstructionKeyword.ENDATOMIC)
        {
            threadListViews[threadID].getSelectionModel().select(instructionNumber);
        }
        else
        {
            SimulatorThread thread = simulator.getMachine().getThread(threadID);
            int min = instructionNumber;
            int max = instructionNumber;

            if (keyword == InstructionKeyword.ATOMIC)
            {

                for (int i = instructionNumber; i < thread.getInstructionsList().size(); i++)
                {
                    if (thread.getInstructionsList().get(i).getKeyword() == InstructionKeyword.ENDATOMIC) break;
                    max = i + 1;
                }

                threadListViews[threadID].getSelectionModel().selectRange(min, max + 1);
            }
        }
    }


}
