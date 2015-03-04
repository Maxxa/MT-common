package cz.commons.animation;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Vojtěch Müller
 */
public final class AnimationsHelper {

    public static ObservableList<Animation> getTransitionChildren(Transition transition) {
        if(transition instanceof  ParallelTransition){
            return  ((ParallelTransition) transition).getChildren();
        }
        if(transition instanceof  SequentialTransition){
            return  ((SequentialTransition) transition).getChildren();
        }
        return FXCollections.observableArrayList();
    }


    /**
     * Sets all children of parallel transition to visible. It can be useful for
     * preventing image flickering. (blink effect) It also maintains that all
     * nodes will be visible while playing (if they were hidden for some reason)
     *
     * @param animations animations
     */
    public static void setNodesToVisible(ObservableList<Animation> animations) {
        for (Animation a : animations) {

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
            if (a instanceof FadeTransition) {
                FadeTransition ft = (FadeTransition) a;
                ft.getNode().setVisible(true);
            }
            if (a instanceof ParallelTransition) {
                ParallelTransition p = (ParallelTransition) a;
                setNodesToVisible(p.getChildren());
            }
            if (a instanceof SequentialTransition) {
                SequentialTransition s = (SequentialTransition) a;
                setNodesToVisible(s.getChildren());
            }
        }
    }

    /**
     * Control all animations witch childs is end handler instated of StepDirection
     * and set this handler direction
     * */
    public static void controlAndSetEndHandlerDirections(Transition actualTransition, MovingType movingType) {
        setDirection(actualTransition.getOnFinished(), movingType);
        setDirectionForAllChilds(getTransitionChildren(actualTransition), movingType);
    }

    public static void setDirectionForAllChilds(ObservableList<Animation> animations, MovingType direction) {
        for (Animation a : animations) {
            setDirection(a.getOnFinished(),direction);
            if(Transition.class.isInstance(a)){
                setDirectionForAllChilds(getTransitionChildren((Transition) a), direction);
            }
        }
    }

    public static void setDirection(EventHandler<ActionEvent> handler, MovingType direction){
        if(handler!=null && StepDirection.class.isInstance(handler)){
            ((StepDirection)handler).setDirection(direction);
        }
    }

}
