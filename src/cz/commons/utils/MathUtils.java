package cz.commons.utils;

import java.awt.Point;
import javafx.scene.shape.Line;

/**
 * Pomocne matematicke vypocty.
 * @author Martin Šára
 */
public class MathUtils {
    
    /**
     * Vrati vzdalenost mezi dvema zadanymi body.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    /**
     * Vrati bod uprostred dane cary.
     * @param l
     * @return 
     */
    public static Point getLineCenter(Line l) {
        return new Point((int) (l.getStartX() + l.getEndX()) / 2, (int) (l.getStartY() + l.getEndY()) / 2);
    }

}
