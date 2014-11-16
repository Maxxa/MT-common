package cz.commons.example.graphics.branchNode;

import cz.commons.graphics.BranchNodeElement;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * @author Vojtěch Müller
 */
public class ExampleBranchNode extends BranchNodeElement {

    /**
     * Sirka policka pro klic.
     */
    public static final int KEY_WIDTH = (int) (25.0);

    /**
     * Vodorovna mezera mezi bloky (listy).
     */
    public static final int HSPACE = (int) (3 ) + KEY_WIDTH; // + potrebna mezera pro pridani noveho klice

    /**
     * Vyska bloku.
     */
    public static final int HEIGHT = (int) (20.0);
    /**
     * Vertikalni mezera mezi bloky.
     */
    public static final int VSPACE = (int) (50.0);

    /**
     * Pozadi bloku.
     */
    private final Rectangle background;

    public ExampleBranchNode(int id, int x,int y,int maxChildren) {
        super(id, maxChildren, KEY_WIDTH, HEIGHT);

        setTranslateX(x);
        setTranslateY(y);

        background = new Rectangle(KEY_WIDTH, HEIGHT);
        background.setStrokeType(StrokeType.INSIDE);
        background.setStroke(Color.BLACK);
        background.setFill(Color.YELLOW);
        doParentBindings();

        getChildren().addAll(background);
    }

    @Override
    protected void doParentBindings() {
        centerX.bind(Bindings.add(Bindings.divide(background.widthProperty(), 2), translateXProperty()));
        centerY.bind(Bindings.add(translateYProperty(), 1));
    }
}
