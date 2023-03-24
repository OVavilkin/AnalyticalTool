package org.example;

import org.example.queries.AbstractQuery;
import org.example.queries.CQuery;
import org.example.queries.DQuery;
import org.example.queries.QueryProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class AnalyticalTool {
    public static Stream<Integer> analyze(String lines) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(lines));
        return analyze(br.lines());
    }

    public static Stream<Integer> analyze(Stream<String> lines) {

        //skip 1st line and parse all others
        //build a map of C and D queries
        Map<Class, List<AbstractQuery>> aMap = QueryProvider.from(lines.skip(1))
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
