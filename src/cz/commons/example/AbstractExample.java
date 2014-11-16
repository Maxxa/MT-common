package cz.commons.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Vojtěch Müller
 */
public abstract class AbstractExample  extends Application {

    protected Integer width = 600;
    protected Integer height = 400;

    @Override
    public void start(Stage stage) throws Exception {
        Pane canvas = new Pane();
        Scene scene = new Scene(canvas,getWidth(),getHeight());
        stage.setTitle(getTitle());
        initPane(canvas);
        stage.setScene(scene);
        stage.show();
    }

    protected abstract void initPane(Pane canvas);
    protected abstract String getTitle();

    protected Integer getWidth(){
        return width;
    }
    protected Integer getHeight(){
        return height;
    }

}