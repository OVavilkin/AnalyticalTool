package net.associal.analyticaltool;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class AnalyticalToolTest
{
    @Test
    public void test7lines()
    {
        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb
                .append("7")
                .append(nl)
                .append("C 1.1 8.15.1 P 15.10.2012 83")
                .append(nl)
                .append("C 1 10.1 P 01.12.2012 65")
                .append(nl)
                .append("C 1.1 5.5.1 P 01.11.2012 117")
                .append(nl)
                .append("D 1.1 8 P 01.01.2012-01.12.2012")
                .append(nl)
                .append("C 3 10.2 N 02.10.2012 100")
                .append(nl)
                .append("D 1 * P 8.10.2012-20.11.2012")
                .append(nl)
                .append("D 3 10 P 01.12.2012");

        List<Integer> result = null;
        try {
            result = AnalyticalTool.analyze(sb.toString()).collect(Collectors.toList());
        } catch (IOException e) {
            assertTrue("Exception caught: " + e.getMessage(), false);
        }
        assertTrue( result.size() == 2 );
        assertTrue(Arrays.equals(result.toArray(new Integer[0]), new Integer[] {83, 100}));
    }

    @Test
    public void from15StringsIncludingN() {

        // we could use """ in java 15+

        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb
                .append("15")
                .append(nl)
                .append("C 1.1 8.15.1 P 15.10.2012 83")
                .append(nl)
                .append("C 1 10.1 P 01.12.2012 65")
                .append(nl)
                .append("C 1.1 5.5.1 P 01.11.2012 117")
                .append(nl)
                .append("D 1.1 8 P 01.01.2012-01.12.2012")
                .append(nl)
                .append("C 3 10.2 N 02.10.2012 100")
                .append(nl)
                .append("D 1 * P 8.10.2012-20.11.2012")
                .append(nl)
                .append("D 3 10 P 01.12.2012")
                .append(nl)
                .append("C 1 1 P 01.01.2022 10")
                .append(nl)
                .append("C 2 2 P 02.02.2012 20")
                .append(nl)
                .append("C 3 3.1 P 03.03.2022 30")
                .append(nl)
                .append("D 1 1 P 01.01.2022-02.01.2022")
                .append(nl)
                .append("D 2 2 P 01.02.2022-03.02.2022")
                .append(nl)
                .append("D 3 3.1 P 01.03.2022-04.03.2022")
                .append(nl)
                .append("C 3 3.1 N 05.03.2022 40")
                .append(nl)
                .append("D 3 3.1 N 01.03.2022-06.03.2022");

        List<Integer> result = null;
        try {
            result = AnalyticalTool.analyze(sb.toString()).collect(Collectors.toList());
        } catch (IOException e) {
            assertTrue("Exception caught: " + e.getMessage(), false);
        }

        assertTrue( result.size() == 5 );
        assertTrue(Arrays.equals(result.toArray(new Integer[0]), new Integer[] {83, 100, 10, 30, 40}));
    }
}
