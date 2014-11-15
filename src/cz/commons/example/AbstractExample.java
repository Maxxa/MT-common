package cz.commons.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Vojtěch Müller
 */
public abstract class AbstractExample  extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane canvas = new Pane();
        Scene scene = new Scene(canvas,600,400);
        stage.setTitle(getTitle());
        initPane(canvas);
        stage.setScene(scene);
        stage.show();
    }

    protected abstract void initPane(Pane canvas);
    protected abstract String getTitle();
}