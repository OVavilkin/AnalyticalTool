package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;

import java.time.LocalDate;

/**
 * C queries <br/>
 * <br/>
 * Examples: <br/>
 * C 1 1 P 01.01.2022 10 <br/>
 * C 2 2 P 02.02.2012 20 <br/>
 * C 3 3.1 P 03.03.2022 30 <br/>
 * <br/>
 * Format: <br/>
 * C/D service_id[.sub_service_id] question_id[.sub_question_id[.sub_question_id]] P/N dd.mm.yyyy minutes <br/>
 * values in square brackets are optional <br/>
 */
public class CQuery extends AbstractQuery {
    private int minutes;

    /**
     * Create a CQuery using below variables
     * @param num       the ordinal number of this query in the original list
     * @param service   the Service part of the query
     * @param question  the Question part of the query
     * @param first     if "P" then true, if "N" then false
     * @param date      when this call to help desk was logged
     * @param minutes   duration of the call in minutes
     */
    public CQuery(int num, Category service, Category question, boolean first, LocalDate date, int minutes) {
        super(num, service, question, first, date);
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
