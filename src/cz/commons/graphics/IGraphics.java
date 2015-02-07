package cz.commons.graphics;

import javafx.scene.text.Font;

/**
 * @author Vojtěch Müller
 */
public interface IGraphics {

    /**
     * Vychozi velikost fontu v systemu respektujici aktualni DPI.
     */
    public static final double DEFAULT_FONT_SIZE = Font.getDefault().getSize();
    /**
     * Konstanta udavajici zvetseni oproti standardnimu DPI.
     */
    public static final double PLATFORM_SCALE = DEFAULT_FONT_SIZE / 12.0;
}
