package cz.commons.layoutManager;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public interface ITreeLayoutManager {


    ElementInfo getElementInfo(Integer elementId);

    Point2D getNodePosition(Integer elementId);

    Pane getCanvas();

    DepthManager getDepthManager();

    void clear();

    boolean removeElement(Integer elementId);

    boolean removeElement(Integer elementId,boolean removeFromCanvas);

    void rebuildElements();
}
