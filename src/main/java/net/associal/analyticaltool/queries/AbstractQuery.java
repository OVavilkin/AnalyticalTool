package net.associal.analyticaltool.queries;

import net.associal.analyticaltool.queries.category.Category;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Abstract class, parent for CQuery and DQuery <br/>
 * Holds variables every query should have. <br/>
 */
public class AbstractQuery {
    private int num;
    private Category service;
    private Category question;
    private boolean first;
    private LocalDate date;

    /**
     * Create AbstractQuery. Used by super() in CQuery and DQuery <br/>
     * @param num <br/>
     * @param service <br/>
     * @param question <br/>
     * @param firs          if "P" then true, if "N" then false <br/>
     * @param date <br/>
     */
    public AbstractQuery(int num, Category service, Category question, boolean first, LocalDate date) {
        this.num = num;
        this.service = service;
        this.question = question;
        this.first = first;
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public Category getService() {
        return service;
    }

    public Category getQuestion() {
        return question;
    }

    public boolean isFirst() {
        return first;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Simple hack for debugging purposes <br/>
     * @return String, representing the AbstractQuery <br/>
     */
    @Override
    public String toString() {
        return "num=" + num +
                ", service=" + Arrays.toString(service.getCategories()) +
                ", question=" + Arrays.toString(question.getCategories()) +
                ", first=" + first +
                ", date=" + date +
                '}';
    }
}
