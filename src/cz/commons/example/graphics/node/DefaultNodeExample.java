package cz.commons.example.graphics.node;

import cz.commons.example.AbstractExample;
import cz.commons.graphics.LineElement;
import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public class DefaultNodeExample extends AbstractExample {

    @Override
    protected void initPane(Pane canvas) {
        ExampleNode testNode1 = new ExampleNode(50,50,1);
        ExampleNode testNode2 = new ExampleNode(100,150,2);

        LineElement line1 = new LineElement(testNode1,testNode2);
        canvas.getChildren().addAll(line1,testNode1,testNode2);

        ExampleNode testNode3 = new ExampleNode(200,50,3);
        ExampleNode testNode4 = new ExampleNode(testNode3,0,50,4);
        LineElement line2 = new LineElement(testNode3,testNode4);
        canvas.getChildren().addAll(line2,testNode3,testNode4);
    }

    @Override
    protected String getTitle() {
        return "Default Node Element Example";
    }

    public static void main(String[] args) {launch(args);}
}
