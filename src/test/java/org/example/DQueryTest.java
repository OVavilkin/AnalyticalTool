package org.example;

import org.example.queries.CQuery;
import org.example.queries.DQuery;
import org.example.queries.category.Category;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class DQueryTest
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    @Test
    public void starService()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("02.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(true),
                new Category(4),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void starQuestion()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("02.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(true),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void happensBefore()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("02.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertFalse( D.matches(C));
    }

    @Test
    public void happensAfter()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("03.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("02.03.2003", formatter)
        );

        assertFalse( D.matches(C));
    }

    @Test
    public void happensFirstDay()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void happensLastDay()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("03.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void manyCSubServices()
    {
        CQuery C = new CQuery(
                1,
                new Category(4, 1, 2, 3, 4),
                new Category(4, 13),
                false,
                LocalDate.parse("02.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 13),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void manyCSubQuestions()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 1, 2, 3, 4),
                false,
                LocalDate.parse("02.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 1, 2),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertTrue( D.matches(C));
    }

    @Test
    public void mismatch3rdQuestionSubCategory()
    {
        CQuery C = new CQuery(
                1,
                new Category(4),
                new Category(4, 1, 2),
                false,
                LocalDate.parse("02.03.2003", formatter),
                1
        );

        DQuery D = new DQuery(
                2,
                new Category(4),
                new Category(4, 1, 3),
                false,
                LocalDate.parse("01.03.2003", formatter),
                LocalDate.parse("03.03.2003", formatter)
        );

        assertFalse( D.matches(C));
    }
}
