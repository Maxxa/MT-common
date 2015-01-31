package cz.commons.utils;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 * Hadler zajistujici priblizeni/oddaleni platna pri rotaci kolecka mysi.
 * @author Martin Šára
 */
public class ZoomHandler implements EventHandler<ScrollEvent> {

    /**
     * Obalujici posunovatelny panel.
     */
    private final ScrollPane scrollPane;
    /**
     * Zvetsovane/zmensovane platno.
     * Melo by byt uvnitr {@link #scrollPane}
     */
    private final Pane canvas;
    
    /**
     * Minimalni zvetseni.
     */
    public static double MIN_SCALE = 0.1;
    /**
     * Maximalni zvetseni.
     */
    public static double MAX_SCALE = 10;

    public ZoomHandler(ScrollPane scrollPane, Pane canvas) {
        this.scrollPane = scrollPane;
        this.canvas = canvas;
    }

    @Override
    public void handle(ScrollEvent e) {
        if (e.isControlDown()) {
            double actualScale = canvas.getScaleX();
            
            if (actualScale > MAX_SCALE || actualScale < MIN_SCALE) {
                e.consume();
                return;
            }
            
            double hVal = scrollPane.getHvalue();
            double vVal = scrollPane.getVvalue();
            
            double scale, factor;           
            if (e.getDeltaY() > 0) { // nahoru
                factor = 1.1;
            } else {
                factor = 0.9;
            }
            scale = actualScale * factor;
            
            scale = Math.min(scale, MAX_SCALE);
            scale = Math.max(scale, MIN_SCALE);
            
            canvas.setScaleX(scale);
            canvas.setScaleY(scale);

            scrollPane.setHvalue(hVal);
            scrollPane.setVvalue(vVal);

            e.consume();
        }
    }

}
