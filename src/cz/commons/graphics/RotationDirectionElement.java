package cz.commons.graphics;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * @author Vojtěch Müller
 */
public class RotationDirectionElement extends Parent {

    private Path path;
    private Path pathArrow;
    private final int width;
    private final int height;
    private final boolean isLeft;
    private Color color;

    public RotationDirectionElement(int width,int height,boolean isLeft){
        this(0,0,width,height,isLeft);
    }

    public RotationDirectionElement(int width,int height,boolean isLeft,Color color){
        this(0,0,width,height,isLeft,color);
    }

    public RotationDirectionElement(int x, int y,int width,int height,boolean isLeft){
        this(x,y,width,height,isLeft,Color.BLACK);
    }

    public RotationDirectionElement(int x, int y,int width,int height,boolean isLeft, Color color) {
        this.width = width;
        this.height = height;
        this.isLeft = isLeft;
        this.color = color;
        setPoint(new Point2D(x,y));
        initPath();
        this.getChildren().addAll(path, pathArrow);
    }

    private void initPath() {
        path = new Path();
        path.getElements().addAll(
                new MoveTo(0, height),
                new QuadCurveTo(width / 2, 0, width, height)
        );
        path.setFill(null);
        pathArrow = new Path();
        if (isLeft) {
            initLeftArrow();
        } else {
            initRightArrow();
        }
        initPath(path);
        initPath(pathArrow);
    }

    private void initLeftArrow(){
        pathArrow.getElements().addAll(
                new MoveTo(0, height-10),
                new LineTo(0,height),
                new LineTo(10,height)
        );
    }

    private void initRightArrow(){
        pathArrow.getElements().addAll(
                new MoveTo(width-10, height),
                new LineTo(width,height),
                new LineTo(width,height-10)
        );
    }

    private void initPath(Path path){
        path.setFill(null);
        path.setStroke(color);
    }

    public void setColor(Color color){
        this.color=color;
    }

    public void setPoint(Point2D point){
        setTranslateX(point.getX());
        setTranslateY(point.getY());
    }

}
