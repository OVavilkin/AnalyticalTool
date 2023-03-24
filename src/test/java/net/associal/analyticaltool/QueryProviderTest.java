package net.associal.analyticaltool;

import net.associal.analyticaltool.queries.AbstractQuery;
import net.associal.analyticaltool.queries.CQuery;
import net.associal.analyticaltool.queries.DQuery;
import net.associal.analyticaltool.queries.QueryProvider;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class QueryProviderTest
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    @Test
    public void queryC1()
    {
        AbstractQuery a = QueryProvider.fromLine("C 1.1 8.15.1 P 15.10.2012 83");
        assertTrue( a instanceof CQuery);
        CQuery c = (CQuery) a;
        assertTrue(Arrays.equals(c.getService().getCategories(), new Integer[] {1, 1}));
        assertTrue(Arrays.equals(c.getQuestion().getCategories(), new Integer[] {8, 15, 1}));
        assertTrue( c.isFirst() );
        assertTrue( c.getDate().compareTo(LocalDate.parse("15.10.2012", formatter)) == 0);
        assertTrue(c.getMinutes() == 83);

    }

    @Test
    public void wrongD1date() throws IOException {
        // TODO: catch error stream and check the log
        AbstractQuery a = QueryProvider.fromLine("D 1.1 8 P 99.99.2012-01.12.2012");
        assertTrue(a == null);
    }

    @Test
    public void wrongC1seconds() {
        AbstractQuery a = QueryProvider.fromLine("C 1.1 8.15.1 P 15.10.2012 rat");
        assertTrue(a == null);
    }

    @Test
    public void wrongC1question() {
        AbstractQuery a = QueryProvider.fromLine("C 1.1 8.a P 15.10.2012 rat");
        assertTrue(a == null);
    }

    @Test
    public void queryD1()
    {
        AbstractQuery a = QueryProvider.fromLine("D 1.1 8 P 01.01.2012-01.12.2012");
        assertTrue( a instanceof DQuery);
        DQuery d = (DQuery) a;
        assertTrue(Arrays.equals(d.getService().getCategories(), new Integer[] {1, 1}));
        assertTrue(Arrays.equals(d.getQuestion().getCategories(), new Integer[] {8}));
        assertTrue( d.isFirst() );
        assertTrue( d.getDate().compareTo(LocalDate.parse("01.01.2012", formatter)) == 0);
        assertTrue( d.getEnd().compareTo(LocalDate.parse("01.12.2012", formatter)) == 0);

    }

    @Test
    public void queryD2Star()
    {
        AbstractQuery a = QueryProvider.fromLine("D 1 * P 8.10.2012-20.11.2012");
        assertTrue( a instanceof DQuery );
        DQuery d = (DQuery) a;
        assertTrue(Arrays.equals(d.getService().getCategories(), new Integer[] {1}));
        assertTrue(d.getQuestion().isStar());
        assertTrue( d.isFirst() );
        assertTrue( d.getDate().compareTo(LocalDate.parse("8.10.2012", formatter)) == 0);
        assertTrue( d.getEnd().compareTo(LocalDate.parse("20.11.2012", formatter)) == 0);

    }

    @Test
    public void fromOneString() {

        // we could use """ in java 15+

        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb
                .append("1")
                .append(nl)
                .append("C 1.1 8.15.1 P 15.10.2012 83");

        Stream<AbstractQuery> aStream = null;

        try {
            aStream = QueryProvider.from(sb.toString());
        } catch (IOException e) {
            assertTrue("Exception thrown", false);
        }

        List<AbstractQuery> aList = aStream.collect(Collectors.toList());

        assertTrue( aList.size() == 1);
        assertTrue( aList.get(0) instanceof CQuery);
        CQuery c = (CQuery) aList.get(0);
        assertTrue(c.getMinutes() == 83);
    }

    @Test
    public void from2Strings() {

        // we could use """ in java 15+

        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");
        sb
                .append("2")
                .append(nl)
                .append("C 1.1 8.15.1 P 15.10.2012 83")
                .append(nl)
                .append("C 1 10.1 P 01.12.2012 65");

        Stream<AbstractQuery> aStream = null;

        try {
            aStream = QueryProvider.from(sb.toString());
        } catch (IOException e) {
            assertTrue("Exception thrown", false);
        }

        List<AbstractQuery> aList = aStream.collect(Collectors.toList());

        assertTrue( aList.size() == 2);
        assertTrue( aList.get(0) instanceof CQuery);
        CQuery c = (CQuery) aList.get(0);
        assertTrue(c.getMinutes() == 83);
    }

    @Test
    public void from7Strings() {

        // we could use """ in java 15+

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

        Stream<AbstractQuery> aStream = null;

        try {
            aStream = QueryProvider.from(sb.toString());
        } catch (IOException e) {
            assertTrue("Exception thrown", false);
        }

        List<AbstractQuery> aList = aStream.collect(Collectors.toList());
        assertTrue( aList.size() == 7);
        CQuery c = (CQuery) aList.get(0);
        assertTrue(c.getMinutes() == 83);

        DQuery d = (DQuery) aList.get(3);
        assertTrue( d.getEnd().compareTo(LocalDate.parse("01.12.2012", formatter)) == 0);

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
                .append("D 3 3.1 N 01.03.2022-06.03.2022")
        ;

        Stream<AbstractQuery> aStream = null;

        try {
            aStream = QueryProvider.from(sb.toString());
        } catch (IOException e) {
            assertTrue("Exception thrown", false);
        }


        List<AbstractQuery> aList = aStream.collect(Collectors.toList());
        assertTrue( aList.size() == 15);
    }
}
