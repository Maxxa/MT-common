package cz.commons.layoutManager;

import javafx.geometry.Point2D;

/**
 * @author Vojtěch Müller
 */
public class MoveTreeElement {

    private final Integer elementId;
    private final Point2D oldPoint;
    private final Point2D newPoint;

    public MoveTreeElement(Integer elementId, Point2D oldPoint, Point2D newPoint) {
        this.elementId = elementId;
        this.oldPoint = oldPoint;
        this.newPoint = newPoint;
    }
}
