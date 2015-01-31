package cz.commons.utils;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * @author Vojtěch Müller
 */
public class FadesTransitionBuilder {

    public static FadeTransition getTransition(Node element,Duration duration,int fromValue,int toValue) {
        return getTransition(element,duration,fromValue,toValue,1);
    }

    public static FadeTransition getTransition(Node element,Duration duration,int fromValue,int toValue,int cycleCount) {
       return FadeTransitionBuilder.create()
                .node(element)
                .duration(duration)
                .fromValue(fromValue)
                .toValue(toValue)
                .cycleCount(cycleCount)
                .build();
    }
}
