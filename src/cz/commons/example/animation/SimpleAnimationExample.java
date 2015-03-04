package cz.commons.example.animation;

import cz.commons.animation.TransitionEndPositionType;
import cz.commons.animation.TransitionFinishedEvent;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import cz.commons.animation.AnimationControl;
import cz.commons.example.AbstractExample;
import cz.commons.example.graphics.branchNode.ExampleBranchNode;

public class SimpleAnimationExample extends AbstractExample {

    private Button goForward = new Button("Forward");

    private Button goBack = new Button("Back");
    private ExampleBranchNode node = new ExampleBranchNode(0, 0, 50, 2);

    private final Boolean IS_STEPPING = true;

    private AnimationControl ac = new AnimationControl();

    public SimpleAnimationExample() {
        super();
        initHandlers();
        createAnimation();

    }

    private void initHandlers() {
        ac.markAsStepping(IS_STEPPING);

        goForward.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ac.playForward();
                controlIsNext();

            }
        });
        goBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ac.goBack();
                controlIsNext();

            }
        });

        ac.addTransitionFinishedListener(new TransitionFinishedEvent() {
            @Override
            public void handle(TransitionEndPositionType type) {
                System.out.println(type);
            }
        });
    }

    private void controlIsNext() {
        System.out.println("Is previous: " + ac.isPreviousTransition());
        System.out.println("Is next: " + ac.isNextTransition());
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
        TranslateTransition tt;
        TranslateTransition tt2;
        TranslateTransition tt12;
        //  ScaleTransition sc = ScaleTransitionBuilder.create().node(node).fromX(0).fromY(0).toX(1).toY(1).duration(Duration.seconds(5)).build();
        tt = TranslateTransitionBuilder.create().fromX(0).fromY(50).toX(250)
                .toY(50).duration(new Duration(1000)).node(node).build();


        tt12 = TranslateTransitionBuilder.create().fromX(250).fromY(50).toX(500)
                .toY(50).duration(new Duration(1000)).node(node).build();
        tt2 = TranslateTransitionBuilder.create().fromX(500).fromY(50).toX(500)
                .toY(250).duration(new Duration(1000)).node(node).build();


        Timeline timeline = new Timeline();
        Duration duration = Duration.seconds(1);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(node.widthProperty(), 25, Interpolator.EASE_IN)),
                new KeyFrame(duration, new KeyValue(node.widthProperty(), 25 * 2, Interpolator.EASE_IN))
        );

        ParallelTransition pt = ParallelTransitionBuilder.create().build();
        pt.getChildren().addAll(timeline);

        SequentialTransition sq = SequentialTransitionBuilder.create().
                children(tt, tt12, pt).build();
        sq.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.err.println("END 1 sequention animation");
            }
        });
        tt2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.err.println("END 2 animation");
            }
        });

        TranslateTransition tt3 = TranslateTransitionBuilder.create().fromX(500).fromY(250).toX(0)
                .toY(250).duration(new Duration(1000)).node(node).build();
        tt3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.err.println("END 3 animation");
            }
        });
        TranslateTransition tt4 = TranslateTransitionBuilder.create().fromX(0).fromY(250).toX(0)
                .toY(50).duration(new Duration(1000)).node(node).build();
        tt4.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.err.println("END 4 animation");
            }
        });
        //ac.getTransitions().add(sc);
        ac.getTransitions().add(sq);
        ac.getTransitions().add(new ParallelTransition(tt2));
        ac.getTransitions().add(new ParallelTransition(tt3));
        ac.getTransitions().add(new ParallelTransition(tt4));

        if (!IS_STEPPING) {
            ac.playForward();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
