package org.example.queries;

import org.example.queries.category.Category;

import java.time.LocalDate;
import java.util.Arrays;

public class AbstractQuery {
    private int num;
    private Category service;
    private Category question;
    private boolean first;
    private LocalDate date;

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

    @Override
    public String toString() {
        return "AbstractQuery{" +
                "num=" + num +
                ", service=" + Arrays.toString(service.getCategories()) +
                ", question=" + Arrays.toString(question.getCategories()) +
                ", first=" + first +
                ", date=" + date +
                '}';
    }
}
