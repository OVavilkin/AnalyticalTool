package org.example;

import org.example.queries.category.Category;
import org.example.queries.category.CategoryProvider;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class CategoryProviderTest
{

    @Test
    public void parseStar()
    {
        Category category = CategoryProvider.from("*");
        assertTrue( category.isStar() );
    }

    @Test
    public void parse1()
    {
        Category category = CategoryProvider.from("1");
        assertTrue( category.getCategories()[0] == 1 );
    }

    @Test
    public void parse1dot2()
    {
        Category category = CategoryProvider.from("1.2");
        assertTrue( category.getCategories()[0] == 1 );
        System.out.println(Arrays.toString(category.getCategories()));
        assertTrue( category.getCategories()[1] == 2 );
    }

    @Test
    public void parse1dot2dot3()
    {
        Category category = CategoryProvider.from("1.2.3");
        assertTrue( category.getCategories()[0] == 1 );
        assertTrue( category.getCategories()[1] == 2 );
        assertTrue( category.getCategories()[2] == 3 );
    }

    @Test
    public void parse13dot20dot30()
    {
        Category category = CategoryProvider.from("13.20.30");
        assertTrue( category.getCategories()[0] == 13 );
        assertTrue( category.getCategories()[1] == 20 );
        assertTrue( category.getCategories()[2] == 30 );
    }
}
