package cz.commons.graphics;

import javafx.scene.paint.Color;

/**
 * @author Vojtěch Müller
 */
public final class LineBuilder {

    public static LineElement getBlueLine(ConnectibleElement start,ConnectibleElement end){
        LineElement line = getLine(start,end);
        line.setLineColor(Color.BLUE);
        return line;
    }

    public static LineElement getRedLine(ConnectibleElement start,ConnectibleElement end){
        LineElement line = getLine(start,end);
        line.setLineColor(Color.RED);
        return line;
    }

    public static LineElement getLine(ConnectibleElement start, ConnectibleElement end){
        return new LineElement(start,end);
    }



}
