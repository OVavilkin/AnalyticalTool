package net.associal.analyticaltool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.*;

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
        String logLevel = null;

        //TODO: change input for the program so it is more unix style
        // example: -i inputFile
        //          -o outputFile
        //          -e errorFile
        //          -l logLevel

        switch(args.length) {
            default:
                break;
            case 4:
                logLevel = args[3];
            case 3:
                errorFile = args[2];
                FileHandler logFile = new FileHandler(errorFile);
                logFile.setFormatter(new SimpleFormatter());
                Logger logger = Logger.getLogger("net.associal.analyticaltool");

                // logLevel provided earlier, we should set it now or print Usage information.
                if(logLevel != null) {
                    try {
                        logger.setLevel(Level.parse(logLevel));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Usage: log level one of below");
                        System.out.println("SEVERE (highest value)\n" +
                                "WARNING\n" +
                                "INFO\n" +
                                "CONFIG\n" +
                                "FINE\n" +
                                "FINER\n" +
                                "FINEST (lowest value)");
                    }
                }

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
