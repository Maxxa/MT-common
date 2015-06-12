package cz.commons.example.graphics.layoutManager;

import cz.commons.example.AbstractExample;
import cz.commons.layoutManager.BinaryTreeLayoutManager;
import cz.commons.layoutManager.TreeLayoutSettings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @author Vojtěch Müller
 */
public class ExampleWithSecondPointCalculation extends AbstractExample {

    protected Integer width = 1000;
    protected Integer height = 600;
    TreeLayoutSettings settings;
    BinaryTreeLayoutManager manager;

    @Override
    protected void initPane(Pane canvas) {
        settings=  new TreeLayoutSettings(10,4,25,20);
        manager= new BinaryTreeLayoutManager(settings,canvas, BinaryTreeLayoutManager.PositionsChange.CALC_ONLY_INSERTED_NODES);
        initCenterLine(canvas);
        BinaryElement[] elements = new BinaryElement[]{
                new BinaryElement(1,1,1,"s"),

                new BinaryElement(2,1,1,"s"),
                new BinaryElement(3,1,1,"s"),

                new BinaryElement(4,1,1,"s"),
                new BinaryElement(5,1,1,"s"),
                new BinaryElement(6,1,1,"s"),
                new BinaryElement(7,1,1,"s"),

                new BinaryElement(8,1,1,"s"),
                new BinaryElement(9,1,1,"s"),
                new BinaryElement(10,1,1,"s"),
                new BinaryElement(11,1,1,"s"),
                new BinaryElement(12,1,1,"s"),
                new BinaryElement(13,1,1,"s"),
                new BinaryElement(14,1,1,"s"),
                new BinaryElement(15,1,1,"s"),

                new BinaryElement(16,1,1,"s"),
                new BinaryElement(17,1,1,"s"),
                new BinaryElement(18,1,1,"s"),
                new BinaryElement(19,1,1,"s"),
                new BinaryElement(20,1,1,"s"),
                new BinaryElement(21,1,1,"s"),
                new BinaryElement(22,1,1,"s"),
                new BinaryElement(23,1,1,"s"),
                new BinaryElement(24,1,1,"s"),
                new BinaryElement(25,1,1,"s"),
                new BinaryElement(26,1,1,"s"),
                new BinaryElement(27,1,1,"s"),
                new BinaryElement(28,1,1,"s"),
                new BinaryElement(29,1,1,"s"),
                new BinaryElement(30,1,1,"s"),
                new BinaryElement(31,1,1,"s"),
        };

        manager.addElement(elements[0],null,false);

        manager.addElement(elements[1],1,true);
        manager.addElement(elements[2],1,false);

        manager.addElement(elements[3],2,true);
        manager.addElement(elements[4],2,false);
//        manager.addElement(elements[5],3,true);
        manager.addElement(elements[6],3,false);

        manager.addElement(elements[7],4,true);
        manager.addElement(elements[8],4,false);
        manager.addElement(elements[9],5,true);
        manager.addElement(elements[10],5,false);
//        manager.addElement(elements[11],6,true);
//        manager.addElement(elements[12],6,false);
        manager.addElement(elements[13],7,true);
        manager.addElement(elements[14],7,false);

        manager.addElement(elements[15],8,true);
        manager.addElement(elements[16],8,false);
        manager.addElement(elements[17],9,true);
        manager.addElement(elements[18],9,false);
        manager.addElement(elements[19],10,true);
        manager.addElement(elements[20],10,false);
        manager.addElement(elements[21],11,true);
        manager.addElement(elements[22],11,false);
//        manager.addElement(elements[23],12,true);
//        manager.addElement(elements[24],12,false);
//        manager.addElement(elements[25],13,true);
//        manager.addElement(elements[26],13,false);
        manager.addElement(elements[27],14,true);
        manager.addElement(elements[28],14,false);
        manager.addElement(elements[29],15,true);
        manager.addElement(elements[30],15,false);

        manager.rebuildElements();
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
