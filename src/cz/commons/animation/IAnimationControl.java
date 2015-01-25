package cz.commons.animation;

import javafx.animation.ParallelTransition;

import java.util.ArrayList;

/**
 * @author Vojtěch Müller
 */
public interface IAnimationControl {

    void togglePlaying();

    void goBack();

    void goForth();

    void setRate(double rate);

    ArrayList<ParallelTransition> getTransitions();

    void addAnimationFinishedListener(AnimationEvent ae);

    void playForward();

    void clear();

    void removeFinishedHandlers();

    boolean isMarkedAsStepping();

    boolean isNextTransition();

    boolean isForwardTransition();
}
