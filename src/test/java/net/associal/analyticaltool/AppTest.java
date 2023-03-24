package net.associal.analyticaltool;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;

public class AppTest 
{
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        App.main(new String[] {"ignore/input", "ignore/output", "ignore/error"});
        //App.main(new String[] {"ignore/input"});
        assertTrue( "Complete", true );
    }
}
