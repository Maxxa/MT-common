package cz.commons.utils.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;

/**
 * Implementace logovani udalosti.
 * @author Martin Šára
 */
public class Log {

    private static Logger logger = null;
    
    private static FileHandler file;
    private static Formatter formatter;
    private static Handler console;
    
    private static boolean access = false;
    private static boolean accessChecked =false;
    
    /*
    OFF (highest value)
    SEVERE is a message level indicating a serious failure.
    WARNING is a message level indicating a potential problem.
    INFO is a message level for informational messages.
    CONFIG is a message level for static configuration messages.
    FINE is a message level providing tracing information.
    FINER indicates a fairly detailed tracing message.
    FINEST indicates a highly detailed tracing message
    ALL (lowest value)
    */    
    private static final Level DEFAULT_LOG_LEVEL = Level.OFF;
    private static final Level DEFAULT_FILE_LEVEL = Level.OFF;
    private static final Level DEFAULT_CONSOLE_LEVEL = Level.OFF;

    
    public static Logger get() {
        if (!checkAccess()) {
            return Logger.getGlobal();
        }
        
        if (logger == null) {
            logger = Logger.getGlobal();
            logger.setLevel(DEFAULT_LOG_LEVEL);
                        
            logger.setUseParentHandlers(false);
            
            formatter = new SimpleOneLineFormatter();
            
            try {
                file = new FileHandler("log.txt");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot add log file handler.");
            }
            file.setFormatter(formatter);
            file.setLevel(DEFAULT_FILE_LEVEL);
            logger.addHandler(file);
            
            console = new MyConsoleHandler();
            console.setLevel(DEFAULT_CONSOLE_LEVEL);  
            console.setFormatter(formatter);
            logger.addHandler(console);      
        }
        
        return logger;
    }
    
    /**
     * Kontrola prav pro nastavovani loggeru.
     * Napr. nastaveni handleru.
     * Zobrazeni aplikace (nepodepsane) v prohlizeci (pomoci Java Network Launching Protocol - JNLP) nema potrebna prava.
     * @return 
     */
    private static boolean checkAccess() {
        if (accessChecked) return access;
        boolean hasAccess = true;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            try {
                sm.checkPermission(new LoggingPermission("control", null));
            } catch (SecurityException e) {
                hasAccess = false;
            }
        }
        Log.access = hasAccess;
        Log.accessChecked = true;
        return hasAccess;
    }
    
}
