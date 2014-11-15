package cz.commons.example.graphics.node;

import cz.commons.graphics.NodeElement;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Vojtěch Müller
 */
public class ExampleNode extends NodeElement {

    private static final int width = 20;
    private static final int height = 20;

    private Rectangle rectangle = new Rectangle(width,height);

    protected ExampleNode(int x, int y, int id) {
        super(x, y, width, height, id);
        initRect();
    }

    private void initRect() {
        rectangle.setFill(Color.BLUE);
        this.getChildren().addAll(rectangle);
    }

    public ExampleNode(NodeElement anchor, int dx, int dy, int id) {
        super(anchor, dx, dy, width, height, id);
        initRect();
    }
}
