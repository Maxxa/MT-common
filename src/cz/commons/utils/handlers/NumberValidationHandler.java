package cz.commons.utils.handlers;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * @author Vojtěch Müller
 */
public class NumberValidationHandler implements EventHandler<KeyEvent> {

    final Integer maxLength;

    public NumberValidationHandler(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        TextField txt_TextField = (TextField) keyEvent.getSource();
        if (txt_TextField.getText().length() >= maxLength) {
            keyEvent.consume();
        }
        if(keyEvent.getCharacter().matches("[0-9.]")){
            if(txt_TextField.getText().contains(".") && keyEvent.getCharacter().matches("[.]")){
                keyEvent.consume();
            }else if(txt_TextField.getText().length() == 0 && keyEvent.getCharacter().matches("[.]")){
                keyEvent.consume();
            }
        }else{
            keyEvent.consume();
        }
    }
}
