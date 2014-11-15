package cz.commons.graphics;

import javafx.scene.paint.Color;

/**
 * @author Vojtěch Müller
 */
public final class LineBuilder {

    public static LineElement getBlueLine(ConnectibleElement start,ConnectibleElement end){
        return getColoredLine(start,end,Color.BLUE);
    }

    public static LineElement getRedLine(ConnectibleElement start,ConnectibleElement end){
        return getColoredLine(start,end,Color.RED);
    }

    public static LineElement getLine(ConnectibleElement start, ConnectibleElement end){
        return new LineElement(start,end);
    }

    public static LineElement getColoredLine(ConnectibleElement start,ConnectibleElement end,Color color){
        LineElement line = getLine(start,end);
        line.setLineColor(color);
        return line;
    }



}
