package cz.commons.graphics;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Zakladni trida pro spojovaci cary.
 * @author Martin Šára
 */
public class LineElement extends Parent {
    
    /**
     * Cara.
     */
    protected Line line = new Line();
    
    /**
     * Pocatecni graficky element.
     */
    private ConnectibleElement start;
    /**
     * Koncovy graficky element.
     */
    private ConnectibleElement end;

    
    
    /**
     * Konstrukce spojovaci cary mezi start a end.
     * @param start
     * @param end 
     * @throws IllegalArgumentException pokud je jeden z elementu null, nebo si jsou rovny
     */
    public LineElement(ConnectibleElement start, ConnectibleElement end) {
        if (start == null || end == null || start.equals(end)) {
            throw new IllegalArgumentException("Start or end is null or equals.");
        }

        this.start = start;
        this.end = end;

        bindStart(start);
        bindEnd(end);
                
        getChildren().add(line);
    }
    

    
    /**
     * Nabinduje pocatecni bod cary k danemu pripojnemu bodu.
     * @param start 
     */
    private void bindStart(ConnectibleElement start) {
        line.startXProperty().bind(start.connectXProperty());
        line.startYProperty().bind(start.connectYProperty());
    }
    
    /**
     * Nabinduje koncovy bod cary k danemu pripojnemu bodu.
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
