package cz.commons.utils.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Implementace jednoradkoveho formatovace.
 * @author Martin Šára
 */
public class SimpleOneLineFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        /*return String.format("%1$s: %2$s\n", 
               record.getLevel(), record.getMessage());*/
        return String.format("%1$s: %2$s [%3$s %4$s]\n", 
                record.getLevel(), record.getMessage(),
                record.getSourceClassName(), record.getSourceMethodName());
    }
    
}
