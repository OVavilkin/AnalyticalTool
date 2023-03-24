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
                        .mapToInt(CQuery::getSeconds)
                        .average()
                        .orElse(Double.NaN))
                .filter(d -> !Double.isNaN(d))
                .map(d -> d.intValue());
    }
}
