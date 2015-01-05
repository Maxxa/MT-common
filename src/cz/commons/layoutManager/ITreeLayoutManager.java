package cz.commons.layoutManager;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public interface ITreeLayoutManager {


    Point2D getNodePosition(Integer elementId);

    Pane getCanvas();

    DepthManager getDepthManager();

    void clear();

}
