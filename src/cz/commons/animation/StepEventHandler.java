package cz.commons.animation;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Vojtěch Müller
 */
public abstract class StepEventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Animation){
            Animation animation = (Animation) actionEvent.getSource();
            if(animation.getRate()<0){
                handleBack(actionEvent);
            }else{
                handleForward(actionEvent);
            }
        }
    }

    protected abstract void handleForward(ActionEvent actionEvent);

    protected abstract void handleBack(ActionEvent actionEvent);
}
