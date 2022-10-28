package fr.wollfie.sheetmusiclibrary.io.logging;

import java.io.OutputStream;
import java.io.PrintStream;

public class Logger {

    private enum Level {
        SPAM,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL

    }
    
    private static PrintStream outputStream = System.out;
    private static Level currentLevel = Level.INFO;

    public static void setCurrentLevel(Level currentLevel) {
        Logger.currentLevel = currentLevel;
    }
    public static void setOutputStream(PrintStream stream) {
        outputStream = stream;
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
        print(String.format(msg, formatArgs) + "\n", Level.INFO);
    }

    private static void print(Object msg, Level level) {
        if (level.ordinal() >= currentLevel.ordinal()) {
            outputStream.print(msg);
        }
    }
}
