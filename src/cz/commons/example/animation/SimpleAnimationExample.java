package cz.commons.example.animation;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
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

	private AnimationControl ac = new AnimationControl();
	public SimpleAnimationExample() {
		super();
		initHandlers();
		createAnimation();

	}

	private void initHandlers() {
		ac.markAsStepping(true);

		
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
	}

    private void controlIsNext(){
        System.out.println("Is previous: "+ ac.isPreviousTransition());
        System.out.println("Is next: "+ ac.isNextTransition());
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
		tt = TranslateTransitionBuilder.create().fromX(0).fromY(50).toX(500)
				.toY(50).duration(new Duration(1000)).node(node).build();
		tt2 = TranslateTransitionBuilder.create().fromX(500).fromY(50).toX(500)
				.toY(250).duration(new Duration(1000)).node(node).build();

		ac.getTransitions().add(new ParallelTransition(tt));
		ac.getTransitions().add(new ParallelTransition(tt2));

	}

	public static void main(String[] args) {
		launch(args);
	}
}
