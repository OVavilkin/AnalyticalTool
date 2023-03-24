package net.associal.analyticaltool;

import net.associal.analyticaltool.queries.AbstractQuery;
import net.associal.analyticaltool.queries.CQuery;
import net.associal.analyticaltool.queries.DQuery;
import net.associal.analyticaltool.queries.QueryProvider;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class AnalyticalTool {

    public static Stream<Integer> analyze(String lines) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(lines));
        return analyze(br);
    }

    /**
     * A function called by console application <br/>
     * Looks for total number of queries and calls {@link #analyze(Stream, int)} <br/>
     *  <br/>
     * @param br input as BufferedReader <br/>
     * @return stream of average values <br/>
     * @throws IOException if first line can not be parsed <br/>
     */
    public static Stream<Integer> analyze(BufferedReader br) throws IOException {
        int total;
        try {
            total = Integer.valueOf(br.readLine());
        } catch (NumberFormatException e) {
            // this will go to main
            throw new IllegalArgumentException("Unable to parse first line.");
        }
        return analyze(br.lines(), total);

    }

    /**
     * Main function to be used by the tool suite <br/>
     *  <br/>
     * @param lines C and D queries, example: <br/>
     * C 1 1 P 01.01.2022 10 <br/>
     * C 2 2 P 02.02.2012 20 <br/>
     * C 3 3.1 P 03.03.2022 30 <br/>
     * D 1 1 P 01.01.2022-02.01.2022 <br/>
     * D 2 2 P 01.02.2022-03.02.2022 <br/>
     * D 3 3.1 P 01.03.2022-04.03.2022 <br/>
     * C 3 3.1 N 05.03.2022 40 <br/>
     * D 3 3.1 N 01.03.2022-06.03.2022 <br/>
     * @param total the total number of queries, "8" as per above example <br/>
     * @return stream of integers holding average number of minutes per each D query <br/>
     * <br/>
     * Checks if all queries were parsed but does not interrupt program if some have malformed data <br/>
     */
    public static Stream<Integer> analyze(Stream<String> lines, int total) {

        //skip 1st line and parse all others
        //build a map of C and D queries
        Map<Class, List<AbstractQuery>> aMap = QueryProvider.from(lines.limit(total))
                .filter(Objects::nonNull)
                .collect(groupingBy(q -> q.getClass()));

        // get C queries
        List<CQuery> cList = aMap.get(CQuery.class)
                .stream()
                .map(q -> (CQuery) q)
                .collect(Collectors.toList());

        // get D queries
        List<DQuery> dList = aMap.get(DQuery.class)
                .stream()
                .map(q -> (DQuery) q)
                .collect(Collectors.toList());

        if(cList.size() + dList.size() < total) {
            // not sure if this is critical
            // maybe we can (should?) proceed with successfull queries
            Logger.getLogger("net.associal.analyticaltool").warning(
                    "Incorrect number of queries: "
                    + (cList.size() + dList.size())
                    + " but expected " + total + " queries");
        }

        // iterate each D query;
        // take only preceding C queries
        // check which of them match D query
        // get average for C queries matched
        return dList.stream()
                .map(q -> cList.stream()
                        .filter(c -> c.getNum() < q.getNum())
                        .filter(c -> q.matches(c))
                        .mapToInt(CQuery::getMinutes)
                        .average()
                        .orElse(Double.NaN))
                .filter(d -> !Double.isNaN(d))
                .map(d -> d.intValue());
    }
}
