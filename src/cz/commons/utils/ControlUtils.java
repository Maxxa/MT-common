package cz.commons.utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 * Utility pro graficke prvky.
 * @author Martin Šára
 */
public class ControlUtils {       
    
    /**
     * Vytvori vertikalni textove pole.
     * @param text
     * @return 
     */
    public static Label getVerticalLabel(String text) {
        Label label = new Label(StringUtils.getVerticalText(text));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMinSize(30, text.length() * 18);
        label.setPrefSize(30, text.length() * 18);
                
        return label;
    }
    
    /**
     * Vytvori tlacitko s vertikalnim textem.
     * @param text
     * @return 
     */
    public static Button getVerticalButton(String text) {
        Button button  = new Button(StringUtils.getVerticalText(text));
        button.setAlignment(Pos.CENTER);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setMinSize(30, text.length() * 18);
        button.setPrefSize(30, Double.MAX_VALUE);
        
        return button;
    }
    
    /**
     * Vytvori panel - do leve casti vlozi popis obsahu, do prave casti obsah.
     * @param label
     * @param content
     * @return 
     */
    public static BorderPane getLabeledBorderPane(Node label, Node content) {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(label);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(stackPane);
        borderPane.setCenter(content);
        borderPane.setStyle("-fx-focus-color: transparent");
        
        return borderPane;
    } 

}
