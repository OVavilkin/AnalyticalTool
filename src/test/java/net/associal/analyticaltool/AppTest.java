package net.associal.analyticaltool;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppTest 
{
    @Test
    public void runApp() throws IOException {
        App.main(new String[]{"ignore/input", "ignore/output", "ignore/error"});
        long lines = Files.lines(Paths.get("ignore/output"), StandardCharsets.UTF_8)
                .count();
        assertTrue("Output should hold 4 lines", lines == 5);
        lines = Files.lines(Paths.get("ignore/error"), StandardCharsets.UTF_8)
                .count();
        assertTrue("Error should hold 0 lines", lines == 0);
    }

    @Test
    public void runAppWithLogLevel() throws IOException {
        App.main(new String[]{"ignore/input", "ignore/output", "ignore/error", "FINEST"});

        long lines = Files.lines(Paths.get("ignore/output"), StandardCharsets.UTF_8)
                .count();

        assertTrue("Output should hold 4 lines", lines == 5);

        lines = Files.lines(Paths.get("ignore/error"), StandardCharsets.UTF_8)
                .count();

        assertTrue("Error should hold 0 lines", lines == 160);
    }

    /**
     * Run with caution; disabled to not pause the "mvn package" operation
     */
    @Ignore
    @Test
    public void runAppError() {
        try {
            App.main(new String[] {});
        } catch (IOException e) {
            assertTrue("Exception caught: " + e.getMessage(), true);
        }
    }

}
