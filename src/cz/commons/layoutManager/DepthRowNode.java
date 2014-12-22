package cz.commons.layoutManager;

import javafx.geometry.Point2D;

/**
 * @author Vojtěch Müller
 */
public class DepthRowNode {

    private Integer elementId;
    private Point2D point;

    public DepthRowNode(Point2D point) {
        this.point = point;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }
}
