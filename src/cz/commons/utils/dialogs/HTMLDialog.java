package cz.commons.utils.dialogs;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BlendMode;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Zobrazeni dialogu s obsahem formatovanym pomoci HTML.
 * @author Martin Šára
 */
public class HTMLDialog {

    public static void show(String title, final String htmlContent, Dialog.Icon icon, Dialog.Buttons buttons, int width, int height) {
        final Dialog.CustomContentDialog dlg = Dialog.createCustomContentDialog(title, icon, buttons, new ProgressIndicator(), width, height);
        dlg.showDialogNoWait();
        
        final WebView view = new WebView();
        view.setDisable(true);
        view.setBlendMode(BlendMode.DARKEN);
        view.setFontSmoothingType(FontSmoothingType.LCD);
        view.setPrefSize(width, height);

        WebEngine engine = view.getEngine();
        engine.loadContent(htmlContent);
        dlg.setCustomContent(view);
    }
    
}
