package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;

import java.time.LocalDate;

public class CQuery extends AbstractQuery {
    private int seconds;

    public CQuery(int num, Category service, Category question, boolean first, LocalDate date, int seconds) {
        super(num, service, question, first, date);
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
