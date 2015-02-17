package cz.commons.animation;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Vojtěch Müller
 */
public abstract class StepEventHandler implements EventHandler<ActionEvent>,StepDirection {

    private MovingType direction = MovingType.FORWARD;

    @Override
    public void handle(ActionEvent actionEvent) {
        if(MovingType.FORWARD.equals(direction)){
            handleForward(actionEvent);
        }else{
            handleBack(actionEvent);
        }
    }

    @Override
    public void setDirection(MovingType direction) {
        this.direction=direction;
    }

    protected abstract void handleForward(ActionEvent actionEvent);

    protected abstract void handleBack(ActionEvent actionEvent);
}
