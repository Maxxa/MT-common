package cz.commons.animation;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Viktor Krejčíř
 */
public class AnimationControl {

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
    }

    public ArrayList<ParallelTransition> getTransitions() {
        return transitions;
    }

    public void addAnimationFinishedListener(AnimationEvent ae) {
        finishedEvents.add(ae);
    }

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

    public void goBack() {
        if (actualTransition.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (nextTransition.get() == 0) {
            return;
        }
        playBack();

    }

    /**
     * Krok vpřed, pokud jsme se vraceli naveme v dopředně animaci tam, kam jsme se vrátili
     */
    public void goForth() {
        if (actualTransition.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        if (nextTransition.get() == transitions.size()) {
            return;
        }
        playForward();
    }

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

    public void playForward() {
        int index = nextTransition.get();
        actualTransition = transitions.get(index);
        setNodesToVisible(actualTransition);
        actualTransition.setOnFinished(createForwardTransitionHandler());
        actualTransition.setRate(1 * rate);
        actualTransition.play();
    }

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

    public void clear() {
        nextTransition.setValue(0);
        transitions.clear();
        finishedEvents.clear();
    }

}
