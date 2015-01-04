package cz.commons.example.graphics.layoutManager;

import cz.commons.example.AbstractExample;
import cz.commons.layoutManager.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @author Vojtěch Müller
 */
public class Example extends AbstractExample {

    protected Integer width = 1000;
    protected Integer height = 600;
    TreeLayoutSettings settings;
    BinaryTreeLayoutManager manager;

    @Override
    protected void initPane(Pane canvas) {
        settings=  new TreeLayoutSettings(10,10,25,20);
        manager= new BinaryTreeLayoutManager(settings,canvas);


        initCenterLine(canvas);
        Integer depth = 1;
//        ElementInfo info  = manager.addElement(factory,depth,null);
//        canvas.getChildren().addAll(info.element);

//        positionY += heightStep;
//        depth++;
//        positionX = (int)(positionX/2*depth);
//        BinaryElement element1 = new BinaryElement(2, (int) (positionX-halfElementWidth),positionY,"test");
//        BinaryElement element2 = new BinaryElement(3, (int) (positionX*depth-halfElementWidth),positionY,"test");
//
//        canvas.getChildren().addAll(element1,element2);
//        positionY += heightStep;
//
//        Integer temp = (int)(positionX/2-halfElementWidth);
//        BinaryElement element3 = new BinaryElement(4,temp,positionY,"test");
//        BinaryElement element4 = new BinaryElement(5,temp*2 ,positionY,"test");
//        BinaryElement element5 = new BinaryElement(6,temp*3,positionY,"test");
//        BinaryElement element6 = new BinaryElement(7,temp*4,positionY,"test");

      //  canvas.getChildren().addAll(element3,element4,element5,element6);
    }

    private void initCenterLine(Pane canvas) {
        Line line= new Line();
        line.setFill(Color.BLACK);
        line.setStartX(canvas.getWidth()/2);
        line.setStartY(0);
        line.setEndX(canvas.getWidth()/2);
        line.setEndY(canvas.getHeight());
        canvas.getChildren().add(line);
    }

    @Override
    protected String getTitle() {
        return "Test examples.";
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected Integer getWidth() {
        return width;
    }

    @Override
    protected Integer getHeight() {
        return height;
    }
}
