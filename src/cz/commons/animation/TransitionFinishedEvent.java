package cz.commons.animation;

/**
 * @author Vojtěch Müller
 */
public interface TransitionFinishedEvent {

    void handle(TransitionEndPositionType type);

}
