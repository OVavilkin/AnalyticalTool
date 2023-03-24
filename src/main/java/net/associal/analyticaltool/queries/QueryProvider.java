package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;
import net.associal.analyticaltool.queries.category.CategoryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Represents a Factory providing Queries based on input <br/>
 * Variants are <br/>
 * from(String list) (for testing), from(Stream lines) - get multiple queries <br/>
 * fromLine(String line) - get single query <br/>
 */
public class QueryProvider {

    private static int counter;

    /**
     * Generate a stream of Queries based on input text <br/>
     * Used mainly for testing <br/>
     * <br/>
     * @param list "newline" separated list <br/>
     *             Example: <br/>
     * 3 <br/>
     * C 1.1 8.15.1 P 15.10.2012 83 <br/>
     * C 1 10.1 P 01.12.2012 65 <br/>
     * C 1.1 5.5.1 P 01.11.2012 117 <br/>
     *
     * @return Stream of Queries <br/>
     * @throws IOException if BufferedReader failed to read from stream generated in process <br/>
     */
    public static Stream<AbstractQuery> from(String list) throws IOException {

        // we would use list.lines() if we were on java 11+

        BufferedReader br = new BufferedReader(new StringReader(list));
        int total = Integer.valueOf(br.readLine());
        return br
                .lines()
                .filter(q -> q.length() > 1)
                .map(QueryProvider::fromLine)
                .limit(total);
    }

    /**
     * Generate Query stream based on input text <br/>
     *  <br/>
     * @param lines in C/D query format <br/>
     *              Example: <br/>
     * C 1.1 8.15.1 P 15.10.2012 83 <br/>
     * C 1 10.1 P 01.12.2012 65 <br/>
     * C 1.1 5.5.1 P 01.11.2012 117 <br/>
     *              Please note: first line is a Query, not total number of queries <br/>
     * <br/>
     * @return stream of Query elements <br/>
     */
    public static Stream<AbstractQuery> from(Stream<String> lines) {
        return lines
                .map(QueryProvider::fromLine);
    }

    /**
     * Create either D Query or C Query based on input <br/>
     * throws nothing since we want to proceed if some queries hold bad data <br/>
     * <br/>
     * @param line String representing the query <br/>
     *             Example: <br/>
     *             C 1.1 8.15.1 P 15.10.2012 83 <br/>
     * @return either D Query or C Query <br/>
     */
    public static AbstractQuery fromLine(String line) {
        String regex = "^([C|D]) (\\*|\\d{1,}(?:\\.\\d{1,})*) (\\*|\\d{1,}(?:\\.\\d{1,})*) ([P|N]) (.*)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        if(m.find()) {
            Category service = null;
            Category question = null;

            try {
                service = CategoryProvider.from(m.group(2));
                question = CategoryProvider.from(m.group(3));
            } catch (NumberFormatException nfe) {
                Logger.getLogger("net.associal.analyticaltool")
                        .warning("Service/Question format incorrect, query: " + line);
                return null;
            }
            boolean first = m.group(4).equals("P");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

            AbstractQuery a = null;

            try {

                if (m.group(1).equals("C")) {
                    //match "dd.MM.yyyy seconds"
                    m = Pattern.compile("(\\d*\\.\\d*\\.\\d*) (\\d*)")
                            .matcher(m.group(5));
                    if (m.find()) {
                        LocalDate ld = LocalDate.parse(m.group(1), formatter);
                        int seconds = Integer.valueOf(m.group(2));

                        a = new CQuery(++counter, service, question, first, ld, seconds);
                    }

                } else {
                    //match "dd.MM.yyyy-dd.MM.yyyy" or "d.M.yyyy"
                    m = Pattern.compile("(\\d*\\.\\d*\\.\\d*)-?(\\d*\\.\\d*\\.\\d*)?")
                            .matcher(m.group(5));

                    if (m.find()) {
                        LocalDate start = LocalDate.parse(m.group(1), formatter);
                        LocalDate end = start;
                        if (m.group(2) != null) {
                            end = LocalDate.parse(m.group(2), formatter);
                        }

                        a = new DQuery(++counter, service, question, first, start, end);
                    }
                }
            } catch (DateTimeParseException e) {
                Logger.getLogger("net.associal.analyticaltool")
                        .warning("Incorrect date(s) provided, query: " + line);
            } catch (NumberFormatException e) {
                Logger.getLogger("net.associal.analyticaltool")
                        .warning("Incorrect number of seconds provided, query: " + line);
            }

            return a;

        } else {
            Logger.getLogger("net.associal.analyticaltool")
                    .warning("Could not parse query string: " + line);
        }

        return null;
    }
}
