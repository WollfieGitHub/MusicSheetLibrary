package fr.wollfie.sheetmusiclibrary.io.logging;

import fr.wollfie.sheetmusiclibrary.utils.Constants;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class Logger {
    
    public enum Level {
        SPAM,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL
    }
    
    private static PrintStream outputStream = System.out;
    private static Level currentLevel = Level.SPAM;

    public static Level getCurrentLevel() { return currentLevel; }

    public static void setCurrentLevel(Level currentLevel) {
        Logger.currentLevel = currentLevel;
    }
    public static void setOutputStream(PrintStream stream) {
        outputStream = stream;
    }

    /**
     * Prints a message with spam level
     * @param msg The message to print
     */
    public static void spam(Object msg) {
        print(msg, Level.SPAM);
    }
    
    /**
     * Prints a message with debug level
     * @param msg The message to print
     */
    public static void debug(Object msg) {
        print(msg, Level.DEBUG);
    }

    /**
     * Prints formatted message with debug level
     * @param msg The message to print
     * @param formatArgs The args for the format
     */
    public static void debugf(String msg, Object... formatArgs) {
        print(String.format(msg, formatArgs), Level.DEBUG);
    }


    /**
     * Prints a message with info level
     * @param msg The message to print
     */
    public static void info(Object msg) {
        print(msg, Level.INFO);
    }

    /**
     * Prints formatted message with info level
     * @param msg The message to print
     * @param formatArgs The args for the format
     */
    public static void infof(String msg, Object... formatArgs) {
        print(String.format(msg, formatArgs), Level.INFO);
    }

    /**
     * Prints an exception 
     * @param e The exception to print
     */
    public static void error(Exception e) {
        print(e, Level.ERROR);
    }

    /**
     * Prints an error message
     * @param msg The error message
     */
    public static void error(Object msg) {
        print(msg, Level.ERROR);
    }

    /**
     * Prints a fatal exception 
     * @param e The exception to print
     */
    public static void fatal(Exception e) {
        print(e, Level.FATAL);
    }
    
    /**
     * Prints a fatal message
     * @param msg The error message
     */
    public static void fatal(Object msg) {
        print(msg, Level.FATAL);
    }

    /**
     * Prints a warning message
     * @param msg The warning message to print
     */
    public static void warning(Object msg) {
        print(msg, Level.WARNING);
    }

    /**
     * Prints formatted message with warning level
     * @param msg The message to print
     * @param formatArgs The args for the format
     */
    public static void warningf(String msg, Object... formatArgs) {
        print(String.format(msg, formatArgs), Level.WARNING);
    }

    public static void printStackTrace(Level level) {
        print("Find stack trace below...", level, true);    
    }

    private static int maxLength = 0;

    private static void print(Object msg, Level level) {
        print(msg, level, false);
    }
    
    private static void print(Object msg, Level level, boolean fullStackTrace) {

        // Only log when needed
        if (level.ordinal() >= currentLevel.ordinal()) {
            msg += "\n";
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            
            // 0 = Thread
            int index = 1;
            while (index < elements.length && elements[index].getClassName().equals(Logger.class.getName())) {
                index++;
            }
            
            String stackTrace = "";
            if (fullStackTrace) {
                stackTrace = Arrays.stream(elements).map(e -> e + "\n").collect(Collectors.joining());
            }
            
            String ansiColor = switch (level) {
                case SPAM -> Constants.ANSI_GREY_BOLD;
                case INFO -> Constants.ANSI_BLUE_BOLD;
                case WARNING -> Constants.ANSI_YELLOW_BOLD;
                case ERROR -> Constants.ANSI_RED_BOLD;
                case FATAL -> Constants.ANSI_BRIGHT_RED_BOLD;
                case DEBUG -> Constants.ANSI_MAGENTA_BOLD;
            };
            
            String[] classPath = elements[index].getClassName().split("\\.");
            
            String className = classPath[classPath.length-1];
            
            String header = String.format("%s[%s%s%s:%s%s%s|%7s]>%s%s",
                    ansiColor, Constants.ANSI_RESET,
                    className,
                    ansiColor, Constants.ANSI_RESET,
                    elements[index].getLineNumber(),
                    ansiColor, level.toString().toUpperCase(Locale.ROOT), Constants.ANSI_RESET,
                    (fullStackTrace ? ("\n"+stackTrace) : "")
            );
            // Update the max length of the classPath
            maxLength = Math.max(header.length(), maxLength);
            
            // Send to output stream
            outputStream.printf("%" + maxLength + "s %s", header, msg);
        }
    }
}
