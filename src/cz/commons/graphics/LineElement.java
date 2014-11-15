package cz.commons.graphics;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Basic class for connectible line.
 * @author Martin Šára
 */
public class LineElement extends Parent {
    
    protected Line line = new Line();

    /**
     * Begin connectible element.
     */
    private ConnectibleElement start;
    /**
     * Ending connectible element.
     */
    private ConnectibleElement end;

    /**
     * Construction connectible line between start and end elements.
     * @param start
     * @param end 
     * @throws IllegalArgumentException element can not be null
     */
    public LineElement(ConnectibleElement start, ConnectibleElement end) {
        setStart(start);
        setEnd(end);
        getChildren().add(line);
    }
    
    /**
     * Binding begin point line to the connection point
     * @param start 
     */
    private void bindStart(ConnectibleElement start) {
        line.startXProperty().bind(start.connectXProperty());
        line.startYProperty().bind(start.connectYProperty());
    }
    
    /**
     * Binding end point line to the connection point
     * @param end 
     */
    private void bindEnd(ConnectibleElement end) {
        line.endXProperty().bind(end.connectXProperty());
        line.endYProperty().bind(end.connectYProperty());
    }

    public ConnectibleElement getStart() {
        return start;
    }

    public void setStart(ConnectibleElement start) {
        if (start == null || start.equals(end)) {
            throw new IllegalArgumentException("Start is null or equals end.");
        }
        this.start = start;
        bindStart(start);
    }

    public ConnectibleElement getEnd() {
        return end;
    }

    public void setEnd(ConnectibleElement end) {
        if (end == null || end.equals(start)) {
            throw new IllegalArgumentException("End is null or equals start.");
        }
        this.end = end;
        bindEnd(end);
    }

    public void setLineColor(Color color){
        line.setStroke(color);
    }
}
