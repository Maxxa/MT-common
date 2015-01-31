package cz.commons.utils.transitions;

import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Builder pro animaci relativniho posunuti.
 * @author Martin Šára
 */
public class RelativeTranslateTransition {

    /**
     * Vrati animaci posunuti s relativnim posunem od aktualni pozice.
     * @param node posunovany objekt
     * @param dx relativni posun x
     * @param dy relativni posun y
     * @param duration trvani
     * @return 
     */
    public static TranslateTransition build(Node node, int dx, int dy, Duration duration) {
        TranslateTransition tt = new TranslateTransition();
        
        tt.setNode(node);
        tt.setToX(node.getTranslateX() + dx);
        tt.setToY(node.getTranslateY() + dy);
        tt.setDuration(duration);
        
        return tt;
    }

    public static TranslateTransition build(Node node, Point2D from,Point2D to, Duration duration) {
        return TranslateTransitionBuilder.create()
                .fromX(from.getX()).fromY(from.getY())
                .toX(to.getX()).toY(to.getY())
                .duration(duration).node(node).build();
    }
    
}
