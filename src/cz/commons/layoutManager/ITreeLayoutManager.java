package cz.commons.layoutManager;

import cz.commons.graphics.Element;
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

    ElementInfo addElement(Element element, Integer idParent, boolean isLeftChild);

    ElementInfo addElement(Element element, Integer idParent, boolean isLeftChild, boolean insertToCanvas);

    boolean removeElement(Integer elementId);

    boolean removeElement(Integer elementId,boolean removeFromCanvas);

    void rebuildElements();

    ILayoutChange getLayoutChange();
}
