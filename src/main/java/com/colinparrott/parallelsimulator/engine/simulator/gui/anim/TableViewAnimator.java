package com.colinparrott.parallelsimulator.engine.simulator.gui.anim;


import com.colinparrott.parallelsimulator.engine.simulator.gui.LabelValue;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TableViewAnimator {
    private static final Logger logger = LoggerFactory.getLogger(TableViewAnimator.class);
    private HashMap<Integer, Integer> registersToHighlight;

    public TableViewAnimator() {
        registersToHighlight = new HashMap<>();
    }

    // Sets row with index to certain colour
    public void highlightRowByIndex(TableView<LabelValue> view, int key, int i) {
        logger.debug("highlightRowByIndex Thread: {} \t Register: {}", key, i);
        registersToHighlight.put(key, i);
        view.setRowFactory(tv -> {
            TableRow<LabelValue> row = new TableRow<>();
            BooleanBinding critical = row.indexProperty().isEqualTo(i);
            row.styleProperty().bind(Bindings.when(critical)
                    .then("-fx-background-color: #EBCE7D;")
                    .otherwise(""));
            return row;
        });
    }


    // Sets row if value matches target to certain colour
    public void highlightRowByValueMatch(TableView<LabelValue> view, String label) {
        view.setRowFactory(tv -> new TableRow<LabelValue>() {
            @Override
            public void updateItem(LabelValue item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getLocationName().equals(label)) {
                    setStyle("-fx-background-color: #EBCE7D;");
                } else {
                    setStyle("");
                }
            }
        });

    }

    // Sets row if value matches target to certain colour
    public void clearTableHighlighting(TableView<LabelValue> view)
    {
        view.setRowFactory(tv -> new TableRow<LabelValue>()
        {
            @Override
            public void updateItem(LabelValue item, boolean empty)
            {
                setStyle("");
            }
        });

    }

    public void restoreRegisterHighlighting(TableView<LabelValue> view, int id) {
        logger.debug("restoreRegisterHighlighting({})", id);
        view.setRowFactory(tv -> new TableRow<LabelValue>() {
            @Override
            public void updateItem(LabelValue item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                {
                    setStyle("");
                }
                else if (item.getLocationName().equals("R" + registersToHighlight.get(id)))
                {
                    System.out.println("restore set green");
                    setStyle("-fx-background-color: #EBCE7D;");
                }
                else
                    {
                    setStyle("");
                }
            }
        });
    }
}
