package cz.commons.utils.handlers;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * @author Vojtěch Müller
 */
public class LetterValidationHandler implements EventHandler<KeyEvent> {

        final Integer maxLength;
        final CharacterSize characterSize;

        public LetterValidationHandler(Integer maxLength, CharacterSize characterSize) {
            this.maxLength = maxLength;
            this.characterSize = characterSize;
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            TextField textField = (TextField) keyEvent.getSource();
            if (textField.getText().length() >= maxLength) {
                keyEvent.consume();
            }
            if(!keyEvent.getCharacter().matches(characterSize.regex)){
                keyEvent.consume();
            }
        }

        public enum CharacterSize{
            LOWER_CASE("[a-z]"),UPPER_CASE("[A-Z]"),ALL("[A-Za-z]");

            String regex;

            CharacterSize(String regex) {
                this.regex = regex;
            }
        }
}
