package cz.commons.animation;

import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Viktor Krejčíř
 * @author Vojtěch Müller
 */
public class AnimationControl implements IAnimationControl{

    private final TransitionControl transitionControl;
    private final ArrayList<Transition> transitions;
    private final LinkedList<AnimationEvent> finishedEvents;

    private boolean markedAsStepping = false;

    private IntegerProperty nextTransition = new SimpleIntegerProperty(0);

    public AnimationControl() {
        transitions = new ArrayList<>();
        finishedEvents = new LinkedList<>();
        transitionControl = new TransitionControl();
    }

    /***
     * Get transition queue collection
     *
     * @return list of transitions
     */
    @Override
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

	/***
	 * Add handler that handles state of end of animation in forward direction
	 * (last animation)
	 *
	 * @param ae animation event
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
        transitionControl.togglePlaying();
    }

	/**
	 * Go step backward
	 */
    @Override
    public void goBack() {
        if(!transitionControl.isInitialize() ||
                transitionControl.isPlayActualTransition()){
            return;
        }
        playBack();
    }

    /**
	 * Go step forward
	 */
    @Override
    public void goForth() {
        if(transitions.size()==0
                || transitionControl.isPlayActualTransition()
                || nextTransition.get() == transitions.size()) {
                return;
        }
        System.out.println("begin");
        playForward();
    }

    @Override
    public void playForward() {
        int index = nextTransition.get();

        if(index < transitions.size()){
            transitionControl.actualTransition = transitions.get(index);
            transitionControl.playActualTransition(MovingType.FORWARD, createForwardTransitionHandler());
        }
    }

    private void playBack() {
        int index = nextTransition.get()-1;
        if(index>=0){
            transitionControl.actualTransition = transitions.get(index);
            transitionControl.playActualTransition(MovingType.BACK,createBackTransitionHandler());
        }
    }

    protected void playPrevTransition() {
        int index = nextTransition.get();
        if(index<transitions.size()){
            transitionControl.actualTransition = transitions.get(index);
            transitionControl.playActualTransition(MovingType.BACK,createBackTransitionHandler());
        }
    }

    protected void playNextTransition() {
        transitionControl.actualTransition = transitions.get(nextTransition.get());
        transitionControl.playActualTransition(MovingType.FORWARD, createForwardTransitionHandler());
    }


    /***
	 * Sets rate (speed multiplier)
	 *
	 * @param rate animation speed rate
	 */
    @Override
    public void setRate(double rate) {
        transitionControl.setRate(rate);
    }
    protected EventHandler<ActionEvent> createForwardTransitionHandler() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                nextTransition();

                if (!markedAsStepping) {
                    if (nextTransition.get() < transitions.size()) {
                        playNextTransition();
                    } else {
                        animationFinished();
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
                        animationFinished();
                    }
                }
                backTransition();
            }
        };
    }

    protected void animationFinished() {
        for (AnimationEvent ae : finishedEvents) {
            ae.handle();
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

	/**
	 * Clear (flushes) the animation control and transition queue
	 *
	 */
    @Override
    public void clear() {
        nextTransition.setValue(0);
        transitions.clear();
        transitionControl.clear();
    }

    @Override
    public void removeFinishedHandlers(){
        finishedEvents.clear();
    }

    @Override
    public boolean isMarkedAsStepping() {
        return markedAsStepping;
    }

    @Override
    public Boolean isNextTransition(){
       if(!transitionControl.isInitialize())
           return false;
       return getNextIndex()<transitions.size();
	}

    @Override
	public Boolean isPreviousTransition() {
        if(!transitionControl.isInitialize())
            return false;
        return getNextIndex()>0 && transitions.size() > 1;
    }

    private Integer getNextIndex(){
        int result = nextTransition.get();
        if(transitionControl.isPlayActualTransition()){
            if(transitionControl.actualTransition.getRate()<0){
                result--;
            }else{
                result++;
            }
        }
        return result;
    }

    private void nextTransition(){
        nextTransition.setValue(nextTransition.get() + 1);
    }

    private void backTransition(){
        nextTransition.setValue(nextTransition.getValue() - 1);
    }
}
