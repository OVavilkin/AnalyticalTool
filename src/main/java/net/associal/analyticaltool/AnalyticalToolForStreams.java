package net.associal.analyticaltool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;

public interface AnalyticalToolForStreams {

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
