package cz.commons.utils.logging;

import java.io.PrintWriter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Implementace handleru pro vypis na standardni vystup.
 * @author Martin Šára
 */
public class MyConsoleHandler extends StreamHandler {

    private final PrintWriter pw;

    public MyConsoleHandler() {
        pw = new PrintWriter(System.out);
    }

    @Override
    public synchronized void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        pw.print(getFormatter().format(record));
        pw.flush();
    }

    @Override
    public synchronized void flush() {
        pw.flush();
    }

    @Override
    public synchronized void close() throws SecurityException {
        pw.close();
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return record.getLevel().intValue() >= getLevel().intValue();
    }
            
}
