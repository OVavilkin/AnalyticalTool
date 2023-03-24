package net.associal.analyticaltool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App 
{
    public static void main( String[] args ) throws IOException {
        String inputFile = null;
        String outputFile = null;
        String errorFile = null;

        switch(args.length) {
            default:
                /*
                InputStreamReader inStream = new InputStreamReader(System.in);
                System.out.println("Stream is ready: " + inStream.ready());
                if(!inStream.ready()) {
                    Logger.getLogger("net.associal.analyticaltool").
                            severe("No input found!");
                    System.out.println("Usage:");
                    System.out.println("java -cp file.jar net.associal.anylyticaltool.App "
                            + "[inputFile [outputFile [errorFile]]]");
                    System.out.println("\nor");
                    System.out.println("cat inputFile | java -cp net.associal.analyticaltool.App");
                }
                inStream.close();
                return;

                 */
                System.out.println("No input...");
                break;
            case 3:
                errorFile = args[2];
                FileHandler logFile = new FileHandler(errorFile);
                logFile.setFormatter(new SimpleFormatter());
                Logger logger = Logger.getLogger("net.associal.analyticaltool");

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
                    inputFile = null;
                }
            case 0:
                break;
        }

        try {
            AnalyticalToolForFiles.analyze(inputFile, outputFile);
        } catch (RuntimeException e) {
            Logger.getLogger("net.associal.analyticaltool")
                    .severe(e.getMessage());

            //we are not able to handle runtime exceptions, interrupt program.
            throw e;
        }
    }
}
