package cz.commons.utils.handlers;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

/**
 * Handler zajistujici horizonatalni posunovani {@link ScrollPane} pri rotaci
 * kolecka mysi.
 * @author Martin Šára
 */
public class HorizontalScrollMouseWheelHandler implements EventHandler<ScrollEvent> {

    private final ScrollPane scrollPane;
    /**
     * Koeficient zmeny posunuti.
     */
    private final double coeff;

    
    
    /**
     * Konstrukce handleru zajistujici horizontalni posunovani pri rotaci
     * kolecka mysi.
     * @param scrollPane ovladany panel
     * @param coef koeficient posunuti - v intervalu 0-1
     * @throws IllegalArgumentException pokud je koeficient mimo rozmezi 0-1
     */
    public HorizontalScrollMouseWheelHandler(ScrollPane scrollPane, double coef) {
        if (coef < 0 || coef > 1) {
            throw new IllegalArgumentException("Koeficient musi byt v rozmezi 0-1");
        }
        this.coeff = coef;
        this.scrollPane = scrollPane;
    }
        
    
    
    @Override
    public void handle(ScrollEvent event) {
        double position = scrollPane.getHvalue();
        if (event.getDeltaY() < 0) { // dolu
            position += coeff;
        } else {
            position -= coeff;
        }
        scrollPane.setHvalue(position);
        event.consume();
    }

}
