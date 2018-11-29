package com.colinparrott.parallelsimulator.engine.simulator.gui.anim;


import com.colinparrott.parallelsimulator.engine.simulator.gui.LabelValue;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class TableViewAnimator
{
    // Sets row with index to certain colour
    public static void highlightRowByIndex(TableView<LabelValue> view, int i)
    {
        view.setRowFactory(tv -> {
            TableRow<LabelValue> row = new TableRow<>();
            BooleanBinding critical = row.indexProperty().isEqualTo(i);
            row.styleProperty().bind(Bindings.when(critical)
                    .then("-fx-background-color: #42f48c;")
                    .otherwise(""));
            return row;
        });

    }

    // Sets row if value matches target to certain colour
    public static void highlightRowByValueMatch(TableView<LabelValue> view, String label, int value)
    {
        view.setRowFactory(tv -> new TableRow<LabelValue>()
        {
            @Override
            public void updateItem(LabelValue item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null)
                {
                    setStyle("");
                }
                else if (item.getLocationName().equals(label))
                {
                    setStyle("-fx-background-color: #42f48c;");
                }
                else
                {
                    setStyle("");
                }
            }
        });

    }
}
