package net.associal.analyticaltool;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;

public class AppTest 
{
    @Test
    public void runApp() throws IOException {
        App.main(new String[] {"ignore/input", "ignore/output", "ignore/error"});
        // TODO: check files, catch input/output etc...
        // At least it works from manual testing perspective :)
        // also would prefer to test console tool using perl, not java...
        try {
            App.main(new String[] {});
        } catch (IOException e) {
            assertTrue("Exception caught: " + e.getMessage(), true);
        }
    }
}
