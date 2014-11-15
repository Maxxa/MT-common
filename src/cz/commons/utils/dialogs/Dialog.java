package cz.commons.utils.dialogs;

import cz.commons.resources.CommonResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Implementace dialogu.
 * Od JDK 8 (JavaFX 8) lze pouzit knihovnu ControlFX (http://fxexperience.com/controlsfx/)
 * @author Martin Šára
 * @param <T> typ data "zavesenych" vlastnich na tlacitkach
 */
public class Dialog<T> {
    
    /**
     * Sirka dialogu.
     */
    public static final int WIDTH = (int) (400);
    /**
     * Vyska dialogu.
     */
    public static final int HEIGHT = (int) (150);
    /**
     * Vychozi barva pozadi.
     */
    public static final Color BACKGROUND_COLOR = Color.WHITESMOKE;
    
    
    
    /**
     * Navratove hodnoty dialogu.
     */
    public static enum Result {
        OK, CANCEL;
    }
    
    /**
     * Definice zobrazenych tlacitek.
     */
    public static enum Buttons {
        OK, OK_CANCEL, YES_NO, YES_NO_CANCEL
    }
    
    /**
     * Definice zobrazenych ikon.
     */
    public static enum Icon {
        INFORMATION("info48.png"), 
        WARNING("warning48.png"), 
        ERROR("error48.png"), 
        QUESTION("confirm48.png");
        
        private final String file;
        
        private Icon(String file) {
            this.file = file;
        }
        
        public Image getImage() {
            String url = CommonResources.getResource("icons/" + file).toString();
            return new Image(url);
        }
    }
    
    
    
    
    
    /**
     * Zakladni dialog.
     */
    private interface BaseDialog {
        
        /**
         * Zobrazi dialog a pozastavi aplikaci.
         * @return 
         */
        Result showDialog();
        
        /**
         * Zobrazi dialog.
         * @return 
         */
        Result showDialogNoWait();
        
        /**
         * Nastavi sirku dialogu.
         * @param width 
         */
        void setWidth(double width);
        
        /**
         * Nastavi vysku dialogu.
         * @param height 
         */
        void setHeight(double height);
        
        /**
         * Nastavi validator dialogu.
         * Overuje platnost zadanych vstupu.
         * @param validator 
         */
        void setDialogValidator(DialogValidator validator);
        
        /**
         * Vyzada zavreni dialogu.
         * Pokud je nastaven validator, overi platnost zadanych vstupu.
         * Pokud neni dialog uspesne zvalidovan, neni zavren.
         * @param result 
         */
        void requestClose(Result result);
                
    }
    
    
    
    /**
     * Dialog s volitelnymi hodnotami tlacitek.
     * @param <T> typ dat spojenych s jednotlivymi tlacitky
     */
    public interface CustomButtonsDialog<T> extends BaseDialog {        
        
        /**
         * Vrati hodnotu, ktera odpovida stisknutemu tlacitku.
         * @return 
         */
        T getResult();
        
    }
    
    
    
    /**
     * Dialog se vstupnim polem.
     */
    public interface InputDialog extends CustomButtonsDialog<String> { 
            
    }
    
    
    
    /**
     * Dialog s volitelnym obsahem.
     */
    public interface CustomContentDialog extends BaseDialog {
        
        /**
         * Zmeni volitelny obsah dialogu.
         * @param node 
         */
        void setCustomContent(Node node);
        
    }
    
    
    
    /**
     * Validator kontrolujici platnost zadanych vstupu.
     */
    public interface DialogValidator {
        
        /**
         * Provede validaci vstupu.
         * @return 
         */
        boolean isValid();
        
        /**
         * Volano, pokud byla validace neuspesna.
         */
        void wasInvalid();
        
    }
    
    /**
     * Implementace zakldaniho dialogu.
     */
    public static class BaseDialogImpl extends Stage implements BaseDialog {
        
        protected Result result;
        protected final VBox centerVBox = new VBox(5);
        protected DialogValidator validator;
        
        // dialog with standard buttons
        protected BaseDialogImpl(String title, String message, Icon icon, Buttons buttons) {
            super(StageStyle.UTILITY);
            init(title);
            doLayout(icon.getImage(), message, getButtons(buttons));
            addShortcuts(getScene());
        }
        
        protected BaseDialogImpl(StageStyle style) {
            super(style);
        }
        
        protected BaseDialogImpl() {
            super(StageStyle.UTILITY);
        }
                
        protected final void init(String title) {
            setTitle(title);
            setWidth(WIDTH);
            setHeight(HEIGHT);
            setResizable(false);
            initModality(Modality.APPLICATION_MODAL);
            
            setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (result == null) result = Result.CANCEL;
                }
            });
        }
        
        protected final void doLayout(Image icon, String text, HBox buttonsHBox) {
            BorderPane layout = new BorderPane();
            
            StackPane iconPane = new StackPane();
            ImageView image = new ImageView(icon);
            iconPane.getChildren().add(image);
            iconPane.setPrefWidth((48+40));
            iconPane.setMinWidth((48+40));
            layout.setLeft(iconPane);
            
            centerVBox.setAlignment(Pos.CENTER_LEFT);
            Label label = new Label(text);
            label.setWrapText(true);
            centerVBox.getChildren().add(label);
            BorderPane.setAlignment(centerVBox, Pos.CENTER);
            BorderPane.setMargin(centerVBox, new Insets(5, 20, 10, 0));
            layout.setCenter(centerVBox);
            
            layout.setBottom(buttonsHBox);
            
            Scene scene = new Scene(layout, BACKGROUND_COLOR);
            setScene(scene);
        }
        
        public final void addShortcuts(Scene scene) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case ENTER:
                            requestClose(Result.OK);
                            break;

                        case ESCAPE:
                            result = Result.CANCEL;
                            close();
                            break;
                    }
                }
            });
        }
        
        public final void addEscapeShortcut(Scene scene) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case ESCAPE:
                            result = Result.CANCEL;
                            close();
                            break;
                    }
                }
            });
        }
                
        private HBox getButtons(Buttons buttons) {
            HBox box = getButtonsHBox();
                    
            switch (buttons) {
                case OK:
                    box.getChildren().add(getOkButton());
                    break;
                case OK_CANCEL:
                    box.getChildren().add(getOkButton());
                    box.getChildren().add(getCancelButton());
                    break;
                case YES_NO:
                    box.getChildren().add(getYesButton());
                    box.getChildren().add(getNoButton());
                    break;
                case YES_NO_CANCEL:
                    box.getChildren().add(getYesButton());
                    box.getChildren().add(getNoButton());
                    box.getChildren().add(getCancelButton());
            }
            
            return box;
        }
        
        protected final HBox getButtonsHBox() {
            HBox box = new HBox(10);
            box.setAlignment(Pos.CENTER_RIGHT);
            box.setPadding(new Insets(10, 20, 10, 10));
            return box;
        }
        
        @Override
        public void requestClose(Result result) {
            if (validator == null) {
                if (result != null) this.result = result;
                close();
                return;
            }
            
            if (validator.isValid()) {
                if (result != null) this.result = result;
                close();
            } else {
                validator.wasInvalid();
            }
        }
        
        protected void requestClose() {
            requestClose(null);
        }
        
        protected final Button getOkButton() {            
            Button button = new Button("OK");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    result = Result.OK;
                    requestClose();
                }
            });
            return button;
        }
        
        protected final Button getCancelButton() {            
            Button button = new Button("Storno");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    result = Result.CANCEL;
                    close();
                }
            });
            return button;
        }
        
        protected final Button getYesButton() {            
            Button button = new Button("Ano");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    result = Result.OK;
                    requestClose();
                }
            });
            return button;
        }
        
        protected final Button getNoButton() {            
            Button button = new Button("Ne");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    result = Result.CANCEL;
                    close();
                }
            });
            return button;
        }
                
        @Override
        public Result showDialog() {
            showAndWait();
            return result;
        }

        @Override
        public Result showDialogNoWait() {
            show();
            return this.result;
        }
        
        @Override
        public void setDialogValidator(final DialogValidator validator) {
            this.validator = validator;
        }
    }  
    
    
    
    
    
    /**
     * Implementace dialogu s vlastnimi tlacitky.
     * @param <T> typ dat prirazenych tlacitkum
     */
    private static final class CustomButtonsDialogImpl<T> extends Dialog.BaseDialogImpl implements CustomButtonsDialog<T> {
        
        private T customResult;
        
        public CustomButtonsDialogImpl(String title, String message, Icon icon, String[] buttonLabels, T[] buttonValues, boolean addCancelButton) {
            super(StageStyle.UTILITY);
            init(title);
            doLayout(icon.getImage(), message, getCustomButtons(buttonLabels, buttonValues, addCancelButton));
            addEscapeShortcut(getScene());
        }
        
        private HBox getCustomButtons(String[] buttonLabels, T[] buttonValues, boolean addCancelButton) {
            if (buttonLabels == null || buttonValues == null) throw new NullPointerException("null argument");
            if (buttonLabels.length != buttonValues.length) throw new IllegalArgumentException("different length");
            
            HBox box = getButtonsHBox();
            
            for (int i = 0; i < buttonLabels.length; i++) {
                String label = buttonLabels[i];
                final T buttonResult = buttonValues[i];
                
                Button button = new Button(label);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        result = Result.OK;
                        customResult = buttonResult;
                        requestClose();
                    }
                });
                
                box.getChildren().add(button);
            }
            
            if (addCancelButton) {
                box.getChildren().add(getCancelButton());
            }
            
            return box;
        }
        
        @Override
        public T getResult() {
            return customResult;
        }
        
    }
    
    
    
    
    
    /**
     * Implementace vstupniho dialogu.
     */
    public static final class InputDialogImpl extends Dialog.BaseDialogImpl implements InputDialog {
        
        private TextField textField;
        private String textResult;
        
        public InputDialogImpl(String title, String message, String defaultValue) {
            super(title, message, Icon.QUESTION, Buttons.OK_CANCEL);
            addInputBox(defaultValue);
        }
        
        public InputDialogImpl(String title, String message, String defaultValue, Icon icon, Buttons buttons) {
            super(title, message, icon, buttons);
            addInputBox(defaultValue);
        }

        private void addInputBox(String defaultValue) {
            textField = new TextField(defaultValue);

            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case ENTER:
                            result = Result.OK;
                            requestClose();
                            break;
                        case ESCAPE:
                            result = Result.CANCEL;
                            close();
                            break;
                    }
                }
            });

            setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    textResult = textField.getText();
                }
            });

            centerVBox.getChildren().add(textField);
        }
        
        @Override
        public String getResult() {
            return textResult;
        }
        
    }
    
    
       
    
    /**
     * Implementace dialogu s vlastnim obsahem.
     */
    public static final class CustomContentDialogImpl extends BaseDialogImpl implements CustomContentDialog {
        
        public CustomContentDialogImpl(String title, Node node, Icon icon, Buttons buttons, int width, int height) {
            super(title, "", icon, buttons);
            setWidth(width);
            setHeight(height);
            setCustomContent(node);
        }
        
        @Override
        public void setCustomContent(Node node) {
            centerVBox.getChildren().clear();
            centerVBox.getChildren().add(node);
        }
                
    }
    
    
    
    
    /**
     * Zobrazi informacni dialog.
     * @param title
     * @param message
     * @return {@link Result}
     */
    public static Result showInformation(String title, String message) {
        return show(title, message, Icon.INFORMATION, Buttons.OK);
    }

    /**
     * Zobrazi vystrazny dialog.
     * @param title
     * @param message
     * @return {@link Result}
     */
    public static Result showWarning(String title, String message) {
        return show(title, message, Icon.WARNING, Buttons.OK);
    }

    /**
     * Zobrazi chybovy dialog.
     * @param title
     * @param message
     * @return {@link Result}
     */
    public static Result showError(String title, String message) {
        return show(title, message, Icon.ERROR, Buttons.OK);
    }

    /**
     * Zobrazi dialog s otazkou.
     * Obsahuje tlacitka ANO-NE.
     * @param title
     * @param message
     * @return {@link Result}
     */
    public static Result showQuestion(String title, String message) {
        return show(title, message, Icon.QUESTION, Buttons.OK_CANCEL);
    }
    
    /**
     * Zobrazi dialog.
     * @param title text zahlavi dialogu
     * @param message text dialogu
     * @param icon ikona dialogu
     * @param buttons tlacitka dialogu
     * @return {@link Result}
     */
    public static Result show(String title, String message, Icon icon, Buttons buttons) {
        return new BaseDialogImpl(title, message, icon, buttons).showDialog();
    }
    
    /**
     * Zobrazi dialog.
     * @param title
     * @param message
     * @param icon
     * @param buttons
     * @param width sirka dialogu, brana v potaz, pokud je vetsi jak 0
     * @param height vyska dialogu, brana v potaz, pokud je vetsi jak 0
     * @return {@link Result}
     */
    public static Result show(String title, String message, Icon icon, Buttons buttons, int width, int height) {
        BaseDialogImpl dlg = new BaseDialogImpl(title, message, icon, buttons);
        if (width > 0) dlg.setWidth(width);
        if (height > 0) dlg.setHeight(height);
        return dlg.showDialog();
    }
    
    
    
    
    
    /**
     * Zobrazi dialog s volitelnym obsahem.
     * @param title
     * @param icon
     * @param buttons
     * @param node
     * @return {@link Result}
     */
    public static Result show(String title, Icon icon, Buttons buttons, Node node) {
        return show(title, icon, buttons, node, WIDTH, WIDTH);
    }
    
    /**
     * Zobrazi dialog o danych rozmerech s volitelnym obsahem.
     * @param title
     * @param icon
     * @param buttons
     * @param node
     * @param width
     * @param height
     * @return {@link Result}
     */
    public static Result show(String title, Icon icon, Buttons buttons, Node node, int width, int height) {
        return createCustomContentDialog(title, icon, buttons, node, width, height).showDialog();
    }

    /**
     * Vrati dialog s volitenym obsahem o danych rozmerech.
     * Tento obsah lze pozdeji zmenit.
     * @param title
     * @param icon
     * @param buttons
     * @param node
     * @return
     */
    public static CustomContentDialog createCustomContentDialog(String title, Icon icon, Buttons buttons, Node node) {
        return createCustomContentDialog(title, icon, buttons, node, WIDTH, WIDTH);
    }

    /**
     * Vrati dialog s volitelnym obsahem.
     * @param title
     * @param icon
     * @param buttons
     * @param node
     * @param width
     * @param height
     * @return
     */
    public static CustomContentDialog createCustomContentDialog(String title, Icon icon, Buttons buttons, Node node, int width, int height) {
        return new CustomContentDialogImpl(title, node, icon, buttons, (int) (width), (int) (height));
    }

    
    
    
    
    /**
     * Vrati dialog s volitelnymi tlacitky.
     * Po kliknuti na tlacitko je vracena hodnota spojena s timto tlacitkem.
     * @param <T> typ dat pripojenych k jedlotlivym tlacitkum
     * @param title
     * @param message
     * @param icon
     * @param buttonLabels popisky tlacitek
     * @param buttonValues data spojene s tlacitky
     * @param addCancelButton
     * @return
     */
    public static <T> CustomButtonsDialog<T> createCustomButtonsDialog(String title, String message, Icon icon, String[] buttonLabels, T[] buttonValues, boolean addCancelButton) {
        return new CustomButtonsDialogImpl<>(title, message, icon, buttonLabels, buttonValues, addCancelButton);      
    }
    
    /**
     * Vrati dialog s volitelnymi tlacitky.
     * Po kliknuti na tlacitko je vracena hodnota spojena s timto tlacitkem.
     * Popisky tlacitek jsou vzaty z pripojenych dat.
     * @param <T> typ dat pripojenych k jedlotlivym tlacitkum
     * @param title
     * @param message
     * @param icon
     * @param buttons data spojene s tlacitky
     * @param addCancelButton
     * @return 
     */
    public static <T> CustomButtonsDialog<T> createCustomButtonsDialog(String title, String message, Icon icon, T[] buttons, boolean addCancelButton) {
        String[] labels = new String[buttons.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = buttons[i].toString();
        }
        return new CustomButtonsDialogImpl<>(title, message, icon, labels, buttons, addCancelButton);      
    }
    
    
    
    
    
    /**
     * Vrati dialog obsahujici vstupni pole.
     * @param title
     * @param message
     * @param defaultInput vychozi hodnota vstupniho pole
     * @return 
     */
    public static InputDialog createInputDialog(String title, String message, String defaultInput) {
        return new InputDialogImpl(title, message, defaultInput);
    }
    
    /**
     * Vrati dialog o danych rozmerech obsahujici vstupni pole.
     * @param title
     * @param message
     * @param defaultInput
     * @param width
     * @param height
     * @return 
     */
    public static InputDialog createInputDialog(String title, String message, String defaultInput, int width, int height) {
        return createInputDialog(title, message, defaultInput, Buttons.OK_CANCEL, Icon.QUESTION, width, height);
    }
    
    /**
     * Vrati dialog o danych rozmerech obsahujici vstupni pole a zvolena tlacitka.
     * @param title
     * @param message
     * @param defaultInput
     * @param buttons
     * @param width
     * @param height
     * @return 
     */
    public static InputDialog createInputDialog(String title, String message, String defaultInput, Buttons buttons, int width, int height) {
        return createInputDialog(title, message, defaultInput, buttons, Icon.QUESTION, width, height);
    }
    
    /**
     * Vrati dialog o danych rozmerech obsahujici vstupni pole, zvolena tlacitka a ikonu.
     * @param title
     * @param message
     * @param defaultInput
     * @param buttons
     * @param icon
     * @param width
     * @param height
     * @return 
     */
    public static InputDialog createInputDialog(String title, String message, String defaultInput, Buttons buttons, Icon icon, int width, int height) {
        InputDialogImpl dlg = new InputDialogImpl(title, message, defaultInput, icon, buttons);
        // pokud je vyska nebo sirka 0, je pouzita vychozi velikost
        if (width > 0) dlg.setWidth(width);
        if (height > 0) dlg.setHeight(height);
        return dlg;
    }
    
}
