package cz.commons.layoutManager;

import javafx.scene.layout.Pane;

/**
 * @author Vojtěch Müller
 */
public interface ITreeLayoutManager {

    Pane getCanvas();

    DepthManager getDepthManager();

    void clear();

}
