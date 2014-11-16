package cz.commons.example.graphics.branchNode;

import cz.commons.example.AbstractExample;
import cz.commons.graphics.BranchNodeElement;
import cz.commons.graphics.LineElement;
import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public class BranchElementExample extends AbstractExample {
    @Override
    protected void initPane(Pane canvas) {
        BranchNodeElement node1 = new ExampleBranchNode(1,50,50,2);

        BranchNodeElement child1 = new ExampleBranchNode(1,30,150,2);
        BranchNodeElement child2 = new ExampleBranchNode(1,90,170,2);

        LineElement line1 = new LineElement(node1.getChildConnector(0),child1);
        LineElement line2 = new LineElement(node1.getChildConnector(1),child2);
        canvas.getChildren().addAll(line1,line2,child1,child2,node1);

        //next level...
        BranchNodeElement child11 = new ExampleBranchNode(1,20,230,2);
        BranchNodeElement child12 = new ExampleBranchNode(1,45,200,2);
        BranchNodeElement child21 = new ExampleBranchNode(1,70,230,2);
        BranchNodeElement child22 = new ExampleBranchNode(1,100,230,2);

        LineElement line11 = new LineElement(child1.getChildConnector(0),child11);
        LineElement line12= new LineElement(child1.getChildConnector(1),child12);
        LineElement line21 = new LineElement(child2.getChildConnector(0),child21);
        LineElement line22 = new LineElement(child2.getChildConnector(1),child22);


        canvas.getChildren().addAll(line11,line12,line21,line22,child11,child12,child21,child22);

        /**---------- More nodes ---------**/

        BranchNodeElement node2 = new ExampleBranchNode(1,350,50,3);

        BranchNodeElement child3 = new ExampleBranchNode(1,320,100,3);
        BranchNodeElement child4 = new ExampleBranchNode(1,350,100,3);
        BranchNodeElement child5 = new ExampleBranchNode(1,380,100,3);

        LineElement line4 = new LineElement(node2.getChildConnector(0),child3);
        LineElement line5 = new LineElement(node2.getChildConnector(1),child4);
        LineElement line6 = new LineElement(node2.getChildConnector(2),child5);
        canvas.getChildren().addAll(line4,line5,line6,node2,child3,child4,child5);
    }

    @Override
    protected String getTitle() {
        return "Branch elements exapmle";
    }

    public static void main(String[] args) {launch(args);}
}
