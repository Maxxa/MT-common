package cz.commons.utils;

import cz.commons.resources.CommonResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * @author Vojtěch Müller
 */
public class ViewSwitchButton extends Button {

    private final ImageView treeView = new ImageView(CommonResources.getResource("icons/treeview.png").toExternalForm());
    private final ImageView gridView = new ImageView(CommonResources.getResource("icons/gridview.png").toExternalForm());

    public ViewSwitchButton() {
            setGraphic(gridView);
            this.addEventHandler(ActionEvent.ACTION,switchButton());
    }

    private EventHandler<ActionEvent> switchButton() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (getGraphic().equals(treeView)) {
                    setGraphic(gridView);
                } else {
                    setGraphic(treeView);
                }
            }
        };
    }

    public boolean isTreeView(){
        return getGraphic().equals(treeView);
    }

    public boolean isGridView(){
        return !isTreeView();
    }

}
