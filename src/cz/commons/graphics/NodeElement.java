package cz.commons.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Zakladni trida pro prvky s jednim propojnym bodem.
 * @author Martin Šára
 */
public abstract class NodeElement extends Element implements ConnectibleElement {
        
    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final DoubleProperty centerY = new SimpleDoubleProperty();

    /**
     * Vytvoreni elementu na absolutnich souradnicich.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param id 
     */
    protected NodeElement(int x, int y, int width, int height, int id) {
        this.id = id;
        setTranslateX(x);
        setTranslateY(y);
        doBindings(width, height);
    }
    
    /**
     * Vytvoreni elementu relativne posunuteho od vztazneho elementu.
     * @param anchor
     * @param x
     * @param y
     * @param width
     * @param height
     * @param id 
     */
    protected NodeElement(NodeElement anchor, int x, int y, int width, int height, int id) {
        this.id = id;
        setTranslateX(x + anchor.getTranslateX());
        setTranslateY(y + anchor.getTranslateY());
        setAnchorNode(anchor);
        doBindings(width, height);
    }

    /**
     * Navazani vlastnosti centerX/Y na aktualni posuti.
     * @param width
     * @param height 
     */
    private void doBindings(int width, int height) {
        centerX.bind(Bindings.add(width/2, translateXProperty()));
        centerY.bind(Bindings.add(height/2, translateYProperty()));
    }
        
    @Override
    public DoubleProperty connectXProperty() {
        return centerX;
    }

    @Override
    public DoubleProperty connectYProperty() {
        return centerY;
    }
    
    /**
     * Navazani pozice na vztazny element.
     * @param anchor vztazny element
     */
    protected final void setAnchorNode(NodeElement anchor) {       
        // zachovani celych cisel (animace pracuji s realnymi cisly)
        long dx = Math.round(getTranslateX() - anchor.getTranslateX());
        long dy = Math.round(getTranslateY() - anchor.getTranslateY());
        
        translateXProperty().bind(Bindings.add(dx, anchor.translateXProperty()));
        translateYProperty().bind(Bindings.add(dy, anchor.translateYProperty()));
    }
    
    /**
     * Odpoutani od vztazneho elementu.
     */
    protected void removeAnchorNode() {        
        translateXProperty().unbind();
        translateYProperty().unbind();
    }
    
}
