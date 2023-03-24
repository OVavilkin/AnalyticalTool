package net.associal.analyticaltool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class to use the Analytical Tool.
 */
public class App 
{
    /**
     * Entrance of the console program <br/>
     * <br/>
     * @param args [inputFile [, outputFile [, errorFile]]] <br/>
     *             if no params provided then tries to use input/output/err streams <br/>
     * @throws IOException mainly if error while writing to a stream <br/>
     */
    public static void main( String[] args ) throws IOException {
        String inputFile = null;
        String outputFile = null;
        String errorFile;

        switch(args.length) {
            default:
                break;
            case 3:
                errorFile = args[2];
                FileHandler logFile = new FileHandler(errorFile);
                logFile.setFormatter(new SimpleFormatter());
                Logger logger = Logger.getLogger("net.associal.analyticaltool");
                // TODO: change logging level based on input parameter #4

                //disable logging to console if errorFile is provided
                logger.setUseParentHandlers(false);
                logger.addHandler(logFile);
            case 2:
                outputFile = args[1];
            case 1:
                inputFile = args[0];
                if(!Files.exists(Paths.get(inputFile))) {
                    Logger.getLogger("net.associal.analyticaltool")
                            .severe("Please provide valid input file, you provided: " + inputFile);
                    // interrupt program
                    return;
                }
        }

        try {
            AnalyticalToolForFiles.analyze(inputFile, outputFile);
        } catch (RuntimeException e) {
            Logger.getLogger("net.associal.analyticaltool")
                    .severe(e.getMessage());

            throw e;
        }
    }
}
