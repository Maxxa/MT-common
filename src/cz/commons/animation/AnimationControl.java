package cz.commons.animation;

import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Viktor Krejčíř
 * @author Vojtěch Müller
 */
public class AnimationControl implements IAnimationControl{

    private final TransitionControl transitionControl;
    private final ArrayList<Transition> transitions;
    private final LinkedList<TransitionFinishedEvent> finishedEvents;

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
	 * @param handler animation event
	 */
    @Override
    public void addTransitionFinishedListener(TransitionFinishedEvent handler) {
        finishedEvents.add(handler);
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
        playForward();
    }

    @Override
    public void playForward() {
        int index = nextTransition.get();

        if(index < transitions.size()){
            play(index,MovingType.FORWARD,createForwardTransitionHandler());
        }
    }

    private void play(int index, MovingType type, AnimationEvent handler) {
        transitionControl.actualTransition = transitions.get(index);
        transitionControl.playActualTransition(type,handler);
    }

    private void playBack() {
        int index = nextTransition.get()-1;
        if(index>=0){
            play(index,MovingType.BACK,createBackTransitionHandler());
        }
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

    protected AnimationEvent createForwardTransitionHandler() {
        return new AnimationEvent() {

            @Override
            public void handle() {
                nextTransition();
                if (markedAsStepping) {
                    animationFinished(isNextTransition()? TransitionEndPositionType.NONE: TransitionEndPositionType.END);
                }else{
                    if (nextTransition.get() < transitions.size()) {
                        playForward();
                    } else {
                        animationFinished(TransitionEndPositionType.END);
                    }
                }
            }
        };
    }

    protected AnimationEvent createBackTransitionHandler() {
        return new AnimationEvent() {

            @Override
            public void handle() {
                backTransition();
                if (markedAsStepping) {
                    animationFinished(isPreviousTransition()? TransitionEndPositionType.NONE: TransitionEndPositionType.START);
                }else{
                    if (nextTransition.get() > 0) {
                        playBack();
                    } else {
                        animationFinished(TransitionEndPositionType.START);
                    }
                }
            }

        };
    }

    protected void animationFinished(TransitionEndPositionType type) {
        for (TransitionFinishedEvent ae : finishedEvents) {
            ae.handle(type);
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
       return getNextIndex()<transitions.size();
	}

    @Override
	public Boolean isPreviousTransition() {
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
