package net.associal.analyticaltool;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public interface AnalyticalToolForFiles {

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
