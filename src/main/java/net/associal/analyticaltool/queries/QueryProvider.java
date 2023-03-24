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

public class QueryProvider {

    private static int counter;

    public static Stream<AbstractQuery> from(String list) throws IOException {

        // we would use list.lines() if we were on java 11+

        BufferedReader br = new BufferedReader(new StringReader(list));
        int total = Integer.valueOf(br.readLine());
        return br
                .lines()
                .filter(q -> q.length() > 1)
                .map(QueryProvider::fromLine)
                .limit(total);
                //.collect(groupingBy(q -> q.getClass()));
    }

    /**
     *
     * @param lines in C/D query format
     * @return stream of AbstractQuery elements
     */
    public static Stream<AbstractQuery> from(Stream<String> lines) {
        return lines
                .map(QueryProvider::fromLine);
    }

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
