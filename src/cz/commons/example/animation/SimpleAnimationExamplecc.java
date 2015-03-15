package cz.commons.example.animation;

import cz.commons.animation.AnimationControl;
import cz.commons.animation.TransitionEndPositionType;
import cz.commons.animation.TransitionFinishedEvent;
import cz.commons.example.AbstractExample;
import cz.commons.example.graphics.branchNode.ExampleBranchNode;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SimpleAnimationExamplecc extends AbstractExample {

    private Button goForward = new Button("Forward");

    private Button goBack = new Button("Back");
    private ExampleBranchNode node = new ExampleBranchNode(50, 60, 50, 2);

    private final Boolean IS_STEPPING = true;

    Timeline timeline;
    ParallelTransition pt = new ParallelTransition();

    private AnimationControl ac = new AnimationControl();

    public SimpleAnimationExamplecc() {
        super();
        initHandlers();
        createAnimation();

    }

    private void initHandlers() {
        ac.markAsStepping(IS_STEPPING);

        goForward.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                pt.setRate(1);
                pt.play();
            }
        });
        goBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                pt.setRate(-1);
                pt.play();

            }
        });

    }


    @Override
    protected void initPane(Pane canvas) {
        HBox menu = new HBox();
        menu.getChildren().addAll(goBack, goForward);
        canvas.getChildren().add(menu);
        // node.setTranslateY(50);
        canvas.getChildren().add(node);

    }

    @Override
    protected String getTitle() {
        // TODO Auto-generated method stub
        return "Simple Animation Example";
    }


    private void createAnimation() {
        timeline = new Timeline();
        Duration duration = Duration.seconds(1);
        if (duration.equals(Duration.ONE)) {
//            background.setWidth(KEY_WIDTH * nKeys);
        }
        //KeyValue kvBegin = new KeyValue(node.widthProperty(),20, Interpolator.EASE_IN);

//        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(
                new KeyFrame(
                        Duration.ZERO
                        , new KeyValue(node.widthProperty(), 25)
                ),
                new KeyFrame(
                        duration
                        , new KeyValue(node.widthProperty(), 25 * 2)
                )
        );

        TranslateTransition tt = TranslateTransitionBuilder.create().node(node).fromX(50).toX(50-12.5).build();
       tt.setDuration(Duration.seconds(1));
        pt.getChildren().addAll(tt,timeline);



    }

    public static void main(String[] args) {
        launch(args);
    }
}
