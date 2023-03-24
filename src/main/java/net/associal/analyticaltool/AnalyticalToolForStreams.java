package net.associal.analyticaltool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Interface to work with streams using the tool
 */
public interface AnalyticalToolForStreams {

    /**
     * Run analyze tool, iterate through the output stream and print it to output <br/>
     * @param in    Input stream, holds text file including total of all queries as first line <br/>
     *              Example: <br/>
     *              3 <br/>
     *              C 1.1 8.15.1 P 15.10.2012 83 <br/>
     *              C 1 10.1 P 01.12.2012 65 <br/>
     *              C 1.1 5.5.1 P 01.11.2012 117 <br/>
     * @param out   Output stream, where print analyze tool results <br/>
     * @throws IOException if failed to write to the BufferedWriter during the stream then below <br/>
     * @throws RuntimeException shall be handled in main; <br/>
     *              we are not able to throw checked exceptions as per Consumer accept(T t) method <br/>
     */
    static void analyze(BufferedReader in, BufferedWriter out) throws IOException {

        AnalyticalTool.analyze(in).forEach(i -> {
            try {
                out.write(String.valueOf(i));
                out.write(System.getProperty("line.separator"));
            } catch (IOException e) {
                RuntimeException re = new RuntimeException("Error while writing to output stream.", e);
                Logger.getLogger("net.associal.analyticaltool")
                        .severe(re.getMessage() + " : " + e.getMessage());
                // this will go to main
                throw re;
            }
        });

        out.flush();

    }
}
