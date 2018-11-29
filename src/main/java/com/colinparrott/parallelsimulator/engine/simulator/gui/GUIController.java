package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.engine.hardware.Machine;
import com.colinparrott.parallelsimulator.engine.hardware.SimulatorThread;
import com.colinparrott.parallelsimulator.engine.instructions.Atomic;
import com.colinparrott.parallelsimulator.engine.instructions.EndAtomic;
import com.colinparrott.parallelsimulator.engine.instructions.Instruction;
import com.colinparrott.parallelsimulator.engine.simulator.Simulator;
import com.colinparrott.parallelsimulator.engine.simulator.programs.Program;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView tableRegisters;

    private Program program;
    private Simulator simulator;
    private ListView[] threadListViews;
    private ArrayList<MemoryValue[]> threadRegisters;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Text text = new Text("Sequence History: 0 1 2 0 1");
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
                    highlightInstruction(id, pointer, false);
                else
                    // Going backwards must check if previous instruction was atomic (Atomic signifies this)
                    highlightInstruction(id, pointer, nextInstruction instanceof Atomic);

                updateRegisterView(id);
            }

        });

        btnBackward.setOnAction(event -> {
            if (threadTabPane.getSelectionModel().getSelectedIndex() < simulator.getCurrentProgram().getUsedThreadIDs().length)
            {
                int id = threadTabPane.getSelectionModel().getSelectedIndex();
                simulator.stepBackward();

                int pointer = simulator.getMachine().getThread(id).getInstructionPointer();

                // Going backwards must check if previous instruction was atomic (End Atomic signifies this)
                highlightInstruction(id, pointer, simulator.getMachine().getThread(id).getNextInstruction() instanceof EndAtomic);

                updateRegisterView(id);
            }

        });

        threadRegisters = new ArrayList<>();
        for (int i = 0; i < Machine.MAX_THREADS; i++)
        {
            threadRegisters.add(new MemoryValue[SimulatorThread.REGISTERS_PER_THREAD]);
        }

        threadTabPane.getSelectionModel().selectedIndexProperty().addListener(this::onThreadTabChanged);

    }

    private void onThreadTabChanged(ObservableValue<? extends Number> observableValue, Number oldTab, Number newTab)
    {
        if (newTab.intValue() < simulator.getCurrentProgram().getUsedThreadIDs().length)
            updateRegisterView(newTab.intValue());

    }

    private void updateRegisterView(int id)
    {
        MemoryValue[] vals = new MemoryValue[threadRegisters.get(id).length];
        for (int j = 0; j < threadRegisters.get(id).length; j++)
        {
            vals[j] = new MemoryValue("R" + id, simulator.getMachine().getThread(id).getRegisters()[j].getValue());
        }
        threadRegisters.set(id, vals);


        int i = 0;
        for (Object o : tableRegisters.getItems())
        {
            MemoryValue v = (MemoryValue) o;
            v.setValue(simulator.getMachine().getThread(id).getRegisters()[i].getValue());
            i++;
        }

        tableRegisters.refresh();
    }

    void create(Program p, Simulator simulator)
    {
        this.program = p;
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
        }
    }

    private void highlightInstruction(int threadID, int instructionNumber, boolean isAtomic)
    {
        if (!isAtomic)
        {
            threadListViews[threadID].getSelectionModel().select(instructionNumber);
        }
        else
        {

        }
    }


}
