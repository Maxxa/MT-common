package cz.commons.example.graphics.layoutManager;

import cz.commons.graphics.BinaryNodeElement;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


/**
 * @author Vojtěch Müller
 */
public class BinaryElement extends BinaryNodeElement {

    private Rectangle backgroundRectangle;

    private Integer width = 25;
    private Integer height = 20;

    public BinaryElement(int id, int x, int y, String val) {
        super(id, 25, 20);
        setTranslateX(x);
        setTranslateY(y);
        Tooltip tooltip = new Tooltip();
        tooltip.setText(String.valueOf(id));
        Tooltip.install(this,tooltip);
        backgroundRectangle = new Rectangle(width, height);
        backgroundRectangle.setStrokeType(StrokeType.INSIDE);
        backgroundRectangle.setStroke(Color.BLACK);
        backgroundRectangle.setFill(Color.YELLOW);
        doParentBindings();
        this.getChildren().add(backgroundRectangle);
    }

    @Override
    protected void doParentBindings() {
        centerX.bind(Bindings.add(Bindings.divide(backgroundRectangle.widthProperty(), 2), translateXProperty()));
        centerY.bind(Bindings.add(translateYProperty(), 1));
    }

}
