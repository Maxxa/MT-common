package cz.commons.animation;

import javafx.animation.Transition;

import java.util.ArrayList;

/**
 * @author Vojtěch Müller
 */
public interface IAnimationControl {

    void togglePlaying();

    void goBack();

    void goForth();

    void setRate(double rate);

    ArrayList<Transition> getTransitions();

    void addAnimationFinishedListener(AnimationEvent ae);

    void playForward();

    void clear();

    void removeFinishedHandlers();

    boolean isMarkedAsStepping();

    Boolean isNextTransition();

    Boolean isPreviousTransition();
}
