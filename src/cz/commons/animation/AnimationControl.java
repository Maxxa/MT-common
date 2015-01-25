package cz.commons.animation;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Viktor Krejčíř
 */
public class AnimationControl implements IAnimationControl{

    private ParallelTransition actualTransition;

    private IntegerProperty nextTransition = new SimpleIntegerProperty(0);
    private ArrayList<ParallelTransition> transitions;
    private double rate;
    private boolean markedAsStepping;
    private LinkedList<AnimationEvent> finishedEvents;

    public AnimationControl() {
        transitions = new ArrayList<>();
        markedAsStepping = false;
        finishedEvents = new LinkedList<>();
		rate = 1;
    }

    /***
     * Get transition queue collection
     *
     * @return list of transitions
     */
    @Override
    public ArrayList<ParallelTransition> getTransitions() {
        return transitions;
    }

	/***
	 * Add handler that handles state of end of animation in forward direction
	 * (last animation)
	 *
	 * @param ae
	 */
    @Override
    public void addAnimationFinishedListener(AnimationEvent ae) {
        finishedEvents.add(ae);
    }

	/**
	 * Toggles playing - if running pauses, if paused resumes
	 */
    @Override
    public void togglePlaying() {
        if (actualTransition != null) {
            if (actualTransition.getStatus() == Animation.Status.RUNNING) {
                actualTransition.pause();
            }
            if (actualTransition.getStatus() == Animation.Status.PAUSED) {
                actualTransition.play();
            }
        }
    }

	/**
	 * Go step backward
	 */
    @Override
    public void goBack() {
        if(actualTransition==null) return;
        if (actualTransition.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (nextTransition.get() == 0) {
            return;
        }
        playBack();
    }

    /**
	 * Go step forward
	 */
    @Override
    public void goForth() {
        if(actualTransition==null || transitions.size()==0) {
            return;
        }else{
            if (actualTransition.getStatus() == Animation.Status.RUNNING) {
                return;
            }
            if (nextTransition.get() == transitions.size()) {
                return;
            }
        }
        playForward();
    }

    @Override
    public void playForward() {
        int index = nextTransition.get();
        actualTransition = transitions.get(index);
        setNodesToVisible(actualTransition);
        actualTransition.setOnFinished(createForwardTransitionHandler());
        actualTransition.setRate(1 * rate);
        actualTransition.play();
    }

	/***
	 * Sets rate (speed multiplier)
	 *
	 * @param rate
	 */
    @Override
    public void setRate(double rate) {
        if (actualTransition != null) {
            actualTransition.setRate(actualTransition.getRate() * rate);
        }
        this.rate = rate;
    }

    protected EventHandler<ActionEvent> createForwardTransitionHandler() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                nextTransition.set(nextTransition.get() + 1);

                if (!markedAsStepping) {
                    if (nextTransition.get() < transitions.size()) {
                        playNextTransition();
                    } else {
                        animationFinished(true);
                    }
                }
            }
        };
    }

    protected EventHandler<ActionEvent> createBackTransitionHandler() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (!markedAsStepping) {
                    if (nextTransition.get() >= 0) {
                        playPrevTransition();
                    } else {
                        animationFinished(false);
                    }
                }
                nextTransition.set(nextTransition.get() - 1);

            }
        };
    }

    protected void playPrevTransition() {
        int index = nextTransition.get();
        actualTransition = transitions.get(index);
        setNodesToVisible(actualTransition);
        actualTransition.setOnFinished(createBackTransitionHandler());
        actualTransition.setRate(-1 * rate);
        actualTransition.play();
    }

    protected void playNextTransition() {
        int index = nextTransition.get();
        actualTransition = transitions.get(index);
        setNodesToVisible(actualTransition);
        actualTransition.setOnFinished(createForwardTransitionHandler());
        actualTransition.setRate(1 * rate);
        actualTransition.play();
    }

    protected void animationFinished(boolean wentForward) {
        if (wentForward) {
            for (AnimationEvent ae : finishedEvents) {
                ae.handle();
            }
        }
    }

	/**
	 * Mark as step-by step animation
	 *
	 * @param stepping
	 *            if true animation pauses after each transition
	 */
    public void markAsStepping(boolean stepping) {
        markedAsStepping = stepping;
    }

    private void playBack() {
        int index = nextTransition.get() - 1;
        actualTransition = transitions.get(index);
        setNodesToVisible(actualTransition);
        actualTransition.setOnFinished(createBackTransitionHandler());
        actualTransition.setRate(-1 * rate);
        actualTransition.play();
    }

	/**
	 * Sets all children of paralel transition to visible. It can be useful for
	 * preventing image flickering. (blink effect) It also maintains that all
	 * nodes will be visible while playing (if they were hidden for some reason)
	 * 
	 * @param pt
	 */
    private void setNodesToVisible(ParallelTransition pt) {
        for (Animation a : pt.getChildren()) {

            if (a instanceof ScaleTransition) {
                ScaleTransition st = (ScaleTransition) a;
                st.getNode().setScaleX(0);
                st.getNode().setScaleY(0);
                st.getNode().setVisible(true);
            }
            if (a instanceof TranslateTransition) {
                TranslateTransition tt = (TranslateTransition) a;

                tt.getNode().setVisible(true);
            }
            if (a instanceof StrokeTransition) {
                StrokeTransition st = (StrokeTransition) a;

                st.getShape().setVisible(true);
            }
            if (a instanceof ParallelTransition) {
                ParallelTransition p = (ParallelTransition) a;
                setNodesToVisible(p);
            }
        }
    }

	/**
	 * Clear (flushes) the animation control and transition queue
	 * 
	 */
    @Override
    public void clear() {
        nextTransition.setValue(0);
        transitions.clear();
    }

    @Override
    public void clearFinishedEvents(){
        finishedEvents.clear();
    }

    public boolean isMarkedAsStepping() {
        return markedAsStepping;
    }
}
