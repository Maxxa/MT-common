package cz.commons.animation;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Vojtěch Müller
 */
class TransitionControl {

    protected Transition actualTransition = null;
    protected double rate;

    TransitionControl() {
       this.rate = 1;
    }

    protected synchronized void playActualTransition(MovingType movingType, AnimationEvent animationControlHandler){
        if(!isPlayActualTransition()){
            AnimationsHelper.setNodesToVisible(AnimationsHelper.getTransitionChildren(actualTransition));
            AnimationsHelper.controlAndSetEndHandlerDirections(actualTransition, movingType);
            initHandlers(animationControlHandler);
            actualTransition.setRate(movingType.getRate() * rate);
            actualTransition.play();
        }
    }

    private synchronized void initHandlers(AnimationEvent animationControlHandler) {
        EventHandler<ActionEvent> currentHandler = actualTransition.getOnFinished();
        AnimationEndHandler handler;
        if(!AnimationEndHandler.class.isInstance(currentHandler)){
            handler = new AnimationEndHandler();
            handler.oldEventHandler = currentHandler;
            actualTransition.setOnFinished(handler);
        }else{
            handler = (AnimationEndHandler) currentHandler;
        }
        handler.animationEvent = animationControlHandler;

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
        if (isPlayActualTransition()) {
            MovingType type = actualTransition.getRate()<0?MovingType.BACK:MovingType.FORWARD;
            actualTransition.setRate(type.getRate() * rate);
        }
        this.rate = rate;
    }

    protected class AnimationEndHandler implements EventHandler<ActionEvent> {

        EventHandler oldEventHandler;
        private AnimationEvent animationEvent;

        @Override
        public void handle(ActionEvent actionEvent) {
            go(oldEventHandler, actionEvent);
            animationEvent.handle();
        }

        private void go(EventHandler<ActionEvent> event,ActionEvent actionEvent){
            if(!AnimationEndHandler.class.isInstance(event) && event!=null){
                event.handle(actionEvent);
            }
        }
    }
}
