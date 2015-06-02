package cz.commons.layoutManager;

import javafx.geometry.Point2D;

/**
 * @author Vojtěch Müller
 */
class DepthRowNode {

    private Integer elementId;
    private Point2D point;

    DepthRowNode(Point2D point) {
        this.point = point;
    }

    Integer getElementId() {
        return elementId;
    }

    void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    Point2D getPoint() {
        return point;
    }

    void setPoint(Point2D point) {
        this.point = point;
    }

    boolean containsElement(){
        return elementId!=null;
    }

}
