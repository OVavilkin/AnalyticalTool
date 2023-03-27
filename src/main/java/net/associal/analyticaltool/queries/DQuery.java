package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * D Queries <br/><br/>
 *
 * Examples: <br/>
 * D 1 1 P 01.01.2022-02.01.2022 <br/>
 * D 2 2 P 01.02.2022-03.02.2022 <br/>
 * D 3 3.1 P 01.03.2022-04.03.2022 <br/>
 * <br/>
 * Format: <br/>
 * C/D service_id[.sub_service_id] question_id[.sub_question_id[.sub_question_id]] P/N dd.mm.yyyy dd.mm.yyyy <br/>
 * values in square brackets are optional <br/>
 * service_id[.sub_service_id] might be * (only whole value, sub_service_id can't be *) <br/>
 * question_id[.sub_question_id[.sub_question_id]] the same <br/>
 * first dd.mm.yyyy represents the starting day to monitor, later represents the last one. <br/>
 */
public class DQuery extends AbstractQuery {
    private LocalDate end;

    /**
     * Create a D Query <br/>
     *  <br/>
     * @param num       the ordinal number of this query in the original list <br/>
     * @param service   the Service part of the query <br/>
     *                  It may be more abstract then for the C Query (4 matches 4.13) <br/>
     *                  and * matches all <br/>
     * @param question  the Question part of the query <br/>
     *                  It can be more abstract then for the C Query (4.1 matches 4.1.13) <br/>
     *                  and * matches all <br/>
     * @param first     if "P" then true, if "N" then false <br/>
     *                  we should check only C queries that also "P" or "N" respectively <br/>
     * @param start     from what date to monitor the call records (C Queries) <br/>
     * @param end       till what date to monitor the call records (C Queries) <br/>
     */
    public DQuery(int num, Category service, Category question, boolean first, LocalDate start, LocalDate end) {
        super(num, service, question, first, start);
        this.end = end;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "DQuery{" +
                super.toString() +
                " end=" + end +
                '}';
    }

    /**
     * Perform check on 5 parameters <br/>
     *  <br/>
     * 1. The target C Query should happen before D Query (it's num is less then this num) <br/>
     * 2. Target query Service should match this service <br/>
     * 3. Target query Question should match this question <br/>
     * 4. Target query "First call"(P)/"Following call"(N) should match this query <br/>
     * 5. Target query date should be between start date and end date for this query <br/>
     * @param cQuery <br/>
     * @return boolean true if all matched <br/>
     */
    public boolean matches(CQuery cQuery) {
        Logger.getLogger("net.associal.analyticaltool")
                .log(Level.FINEST,
                        "Checking CQuery to match DQuery\n"
                        + this.toString() + "\n"
                        + cQuery.toString()
                );
        return (cQuery.getNum() < this.getNum())
                && this.getService().matches(cQuery.getService())
                && this.getQuestion().matches(cQuery.getQuestion())
                && (Boolean.compare(this.isFirst(), cQuery.isFirst()) == 0)
                && (cQuery.getDate().compareTo(this.getDate()) >= 0)
                && (cQuery.getDate().compareTo(end) <= 0);
    }
}
