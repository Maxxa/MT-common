package cz.commons.animation;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;

/**
 * @author Vojtěch Müller
 */
class TransitionControl {

    protected Transition actualTransition = null;
    private EventHandler<ActionEvent> oldHandler = null;
    private EventHandler<ActionEvent> animationControlHandler=null;
    protected double rate;

    TransitionControl() {
       this.rate = 1;
    }

    private EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(oldHandler!=null){
                go(oldHandler,actionEvent);
                actualTransition.setOnFinished(oldHandler);
            }
            go(animationControlHandler,actionEvent);
            actualTransition.setOnFinished(null);
            oldHandler=animationControlHandler=null;
        }

        private void go(EventHandler<ActionEvent> event,ActionEvent actionEvent){
            if(!handler.equals(event)){
                event.handle(actionEvent);
            }

        }

    };

    protected void playActualTransition(MovingType movingType, EventHandler<ActionEvent> animationControlHandler){
        if(!isPlayActualTransition()){
            this.animationControlHandler = animationControlHandler;
            this.oldHandler = actualTransition.getOnFinished();
            AnimationsHelper.setNodesToVisible(AnimationsHelper.getTransitionChildren(actualTransition));
            actualTransition.setOnFinished(handler);
            actualTransition.setRate(movingType.getRate() * rate);
            actualTransition.play();
        }
    }

    public boolean isPlayActualTransition(){
        return isInitialize() && actualTransition.getStatus() == Animation.Status.RUNNING;
    }

    public boolean isInitialize(){
        return actualTransition!=null;
    }

    public void clear(){
        actualTransition=null;
    }

    public void togglePlaying() {
        if (isPlayActualTransition()) {
            actualTransition.pause();
        }else if(isInitialize()){
            actualTransition.play();
        }
    }

    public void setRate(double rate) {
        if (isInitialize()) {
            actualTransition.setRate(actualTransition.getRate() * rate);
        }
        this.rate = rate;
    }
}
