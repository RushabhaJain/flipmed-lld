package utils;

public class Logger {
    private static final boolean DEBUG = true;
    
    public static void info(String message) {
        if (DEBUG) {
            System.out.println("[INFO] " + message);
        }
    }
    
    public static void debug(String message) {
        if (DEBUG) {
            System.out.println("[DEBUG] " + message);
        }
    }
    
    public static void error(String message) {
        System.err.println("[ERROR] " + message);
    }
}
