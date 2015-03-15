package cz.commons.example.graphics.branchNode;

import cz.commons.animation.StepEventHandler;
import cz.commons.example.AbstractExample;
import cz.commons.graphics.BranchNodeElement;
import cz.commons.graphics.LineElement;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 * @author Vojtěch Müller
 */
public class BranchElementExample extends AbstractExample {

    private Timeline timeline;

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
        final LineElement line22 = new LineElement(child2.getChildConnector(1),child22);

        canvas.getChildren().addAll(line11,line12,line21,line22,child11,child12,child21,child22);


        ParallelTransition parallelTransition;
          parallelTransition = ParallelTransitionBuilder.create()

                .children(
                        FadeTransitionBuilder.create()
                                .node(line22)
                                .duration(Duration.millis(3))
                                .fromValue(1)
                                .toValue(0)
                                .cycleCount(1)
                                .build(),
                        TranslateTransitionBuilder.create()
                                .duration(Duration.seconds(5))
                                .fromX(50)
                                .toX(350)
                                .cycleCount(1)
                                .node(child22)
                                .autoReverse(true)
                                .build()
                )
              //  .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
//
        parallelTransition.setOnFinished(new StepEventHandler() {
            @Override
            protected void handleForward(ActionEvent actionEvent) {
                FadeTransitionBuilder.create()
                        .node(line22)
                        .duration(Duration.millis(1))
                        .fromValue(0)
                        .toValue(1)
                        .cycleCount(1)
                        .build().play();
            }

            @Override
            protected void handleBack(ActionEvent actionEvent) {
                System.out.println("testback");

            }
        });
    parallelTransition.play();

        /**---------- More nodes ---------**/

        BranchNodeElement node2 = new ExampleBranchNode(1,350,50,3);

        BranchNodeElement child3 = new ExampleBranchNode(1,320,100,3);
        BranchNodeElement child4 = new ExampleBranchNode(1,350,100,3);
        BranchNodeElement child5 = new ExampleBranchNode(1,380,100,3);

        LineElement line4 = new LineElement(node2.getChildConnector(0),child3);
        LineElement line5 = new LineElement(node2.getChildConnector(1),child4);
        LineElement line6 = new LineElement(node2.getChildConnector(2),child5);
        canvas.getChildren().addAll(line4,line5,line6,node2,child3,child4,child5);

        ExampleBranchNode n1 = new ExampleBranchNode(2,100,0,2);
        n1.setRectangleColor(Color.BLUE);
        ExampleBranchNode n2 = new ExampleBranchNode(2,100,300,2);

        canvas.getChildren().addAll(n1,n2);

        parallelTransition = ParallelTransitionBuilder.create()
                .children(
                        FadeTransitionBuilder.create()
                                .node(n2)
                                .duration(Duration.seconds(0))
                                .fromValue(0)
                                .toValue(0)
                                .cycleCount(1)
                                .build(),
                        TranslateTransitionBuilder.create()
                                .node(n1)
                                .duration(Duration.seconds(50))
                                .fromY(0)
                                .toY(300)
                                .cycleCount(1)
                                .build(),
                        FadeTransitionBuilder.create()
                                .node(n1)
                                .duration(Duration.seconds(0))
                                .fromValue(0)
                                .toValue(0)
                                .cycleCount(1)
                                .build(),
                        FadeTransitionBuilder.create()
                                .node(n2)
                                .duration(Duration.seconds(0))
                                .fromValue(0)
                                .toValue(1)
                                .cycleCount(1)
                        .build()
                        )
                .autoReverse(false)
                .build();
        parallelTransition.play();





    }

    @Override
    protected String getTitle() {
        return "Branch elements exapmle";
    }

    public static void main(String[] args) {launch(args);}
}
