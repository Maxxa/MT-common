package cz.commons.utils.dialogs;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Implementace dialogu se zobrazenim prubehu ulohy.
 * @author Martin Šára
 */
public class ProgressDialog extends Dialog.BaseDialogImpl {
    
    /**
     * Sirka dialogu.
     */
    public static final int WIDTH = (int) (200);
    /**
     * Vyska dialogu.
     */
    public static final int HEIGHT = (int) (30);

    public static final Color BACKGROUND_COLOR = Color.WHITESMOKE;
    
    /**
     * Ukazatel prubehu.
     */
    private final ProgressBar progressBar = new ProgressBar();
    /**
     * Celkovy pocet udalosti (= 100% na ukazateli prubehu).
     */
    private int totalEvents;
    
    
    
    public ProgressDialog() {
        super(StageStyle.UTILITY);
        init("Načítám...");
        setWidth(WIDTH);
        setHeight(HEIGHT);
        
        progressBar.setPrefWidth(WIDTH);
        progressBar.setPrefHeight(HEIGHT);
        
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        
        StackPane layout = new StackPane();
        layout.getChildren().add(progressBar);
        
        Scene scene = new Scene(layout, BACKGROUND_COLOR);
        setScene(scene);
    }

    /**
     * Nastaveni prubehu podle poctu zbyvajicich udalosti.
     * @param remainsEvents pocet zbyvajicich udalosti
     */
    public void setProgress(int remainsEvents) {
        progressBar.setProgress((totalEvents - remainsEvents) / (double) totalEvents);
    }

    /**
     * Nastaveni celkoveho poctu udalosti.
     * @param totalEvents 
     */
    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int getTotalEvents() {
        return totalEvents;
    }
            
}
