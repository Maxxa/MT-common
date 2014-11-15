package cz.commons.example.graphics.node;

import cz.commons.example.AbstractExample;
import cz.commons.graphics.LineElement;
import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public class DefaultNodeTest extends AbstractExample {

    @Override
    protected void initPane(Pane canvas) {
        TestNode testNode1 = new TestNode(50,50,1);
        TestNode testNode2 = new TestNode(100,150,2);

        LineElement line1 = new LineElement(testNode1,testNode2);
        canvas.getChildren().addAll(line1,testNode1,testNode2);

        TestNode testNode3 = new TestNode(200,50,3);
        TestNode testNode4 = new TestNode(testNode3,0,50,4);
        LineElement line2 = new LineElement(testNode3,testNode4);
        canvas.getChildren().addAll(line2,testNode3,testNode4);
    }

    @Override
    protected String getTitle() {
        return "Default Node Element Example";
    }

    public static void main(String[] args) {launch(args);}
}
