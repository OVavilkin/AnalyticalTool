package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;

import java.time.LocalDate;

public class DQuery extends AbstractQuery {
    private LocalDate end;

    public DQuery(int num, Category service, Category question, boolean first, LocalDate start, LocalDate end) {
        super(num, service, question, first, start);
        this.end = end;
    }

    public LocalDate getEnd() {
        return end;
    }

    public boolean matches(CQuery line) {
        return (line.getNum() < this.getNum())
                && this.getService().matches(line.getService())
                && this.getQuestion().matches(line.getQuestion())
                && (Boolean.compare(this.isFirst(), line.isFirst()) == 0)
                && (line.getDate().compareTo(this.getDate()) >= 0)
                && (line.getDate().compareTo(end) <= 0);

    }
}
