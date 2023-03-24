package net.associal.analyticaltool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Interface to work with files using the tool
 */
public interface AnalyticalToolForFiles {

    /**
     * Use the analyze tool with (optional)input and (optional)output files <br/>
     * <br/>
     * @param input     file address for input data <br/>
     *                  if file does not exist will print log (Usage information) <br/>
     *                  and quit <br/>
     *                  If not provided switches to System.in <br/>
     * @param output    file address to store output <br/>
     *                  If not provided switches to System.out <br/>
     * @throws IOException if something happened while running analyze tool <br/>
     */
    static void analyze(String input, String output) throws IOException {

        BufferedWriter out;
        if(output == null) {
            out = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            out = new BufferedWriter(new FileWriter(output));
        }

        BufferedReader in;
        if(input == null) {
            InputStreamReader inStream = new InputStreamReader(System.in);
            if(!inStream.ready()) {
                Logger.getLogger("net.associal.analyticaltool").severe(
                        "No input found!"
                                + "\nUsage:"
                                + "\njava -cp file.jar net.associal.anylyticaltool.App "
                                + "[inputFile [outputFile [errorFile]]]"
                                + "\nor"
                                + "\ncat inputFile | java -cp net.associal.analyticaltool.App [ > outputFile "
                        + "[ 2> errorFile ]]"
                );
                return;
            }
            in = new BufferedReader(inStream);
        } else {
            in = new BufferedReader(new FileReader(input));
        }

        AnalyticalToolForStreams.analyze(in, out);
        out.flush();
        out.close();
        in.close();
    }
}
