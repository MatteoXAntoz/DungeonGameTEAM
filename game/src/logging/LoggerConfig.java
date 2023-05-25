package logging;

import ecs.components.Component;
import ecs.entities.Entity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import starter.Game;

public class LoggerConfig {
    private static Logger baseLogger;
    private static FileHandler customFileHandler;

    private static void createCustomFileHandler() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String directoryPath = "./logs/systemlogs/";
        String filepath = directoryPath + timestamp + ".log";
        File newLogFile = new File(filepath);
        try {
            Files.createDirectories(Paths.get(directoryPath));
            if (newLogFile.exists()) {
                baseLogger.info("Logfile already exists;");
            } else {
                newLogFile.createNewFile();
                baseLogger.info("Logfile '" + filepath + "' was created.");
            }
            customFileHandler = new FileHandler(filepath);
            customFileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException ioE) {
            baseLogger.warning(
                    "Creation of FileHandler in class 'LoggerConfig' failed: " + ioE.getMessage());
        }
    }

    /** Creates a new base logger that records all occurring logs to a file. */
    public static void initBaseLogger() {
        baseLogger = Logger.getLogger("");
        baseLogger.setLevel(Level.CONFIG);
        createCustomFileHandler();
        Arrays.stream(baseLogger.getHandlers()).forEach(handler -> handler.setLevel(Level.ALL));

        // Shutting off Game, Entity, and Component Logger
        Logger gameLogger = Logger.getLogger(Game.class.getName());
        gameLogger.setLevel(Level.OFF);
        baseLogger.config("The " + gameLogger.getName() + " Logger Level is set to OFF");

        Logger entityLogger = Logger.getLogger(Entity.class.getPackageName());
        entityLogger.setLevel(Level.OFF);
        baseLogger.config("The " + entityLogger.getName() + " Logger Level is set to OFF");

        Logger componentLogger = Logger.getLogger(Component.class.getPackageName());
        componentLogger.setLevel(Level.OFF);
        baseLogger.config("The " + componentLogger.getName() + " Logger Level is set to OFF");

        baseLogger.addHandler(customFileHandler);
    }
}
