package net.associal.analyticaltool;

import net.associal.analyticaltool.queries.category.Category;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class CategoryTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void starTo4()
    {
        Category category1 = new Category(true);
        Category category2 = new Category(4);
        assertTrue( category1.matches(category2) );
    }

    @Test
    public void starTo4dot13() {
        Category category1 = new Category(true);
        Category category2 = new Category(4, 13);
        assertTrue(category1.matches(category2));
    }

    @Test
    public void fourToFour() {
        Category category1 = new Category(4);
        Category category2 = new Category(4);
        assertTrue(category1.matches(category2));
    }

    @Test
    public void fourTo4dot13() {
        Category category1 = new Category(4);
        Category category2 = new Category(4, 13);
        assertTrue(category1.matches(category2));
    }

    @Test
    public void four13To4() {
        Category category1 = new Category(4, 13);
        Category category2 = new Category(4);
        assertFalse(category1.matches(category2));
    }

    @Test
    public void four13To4dot13() {
        Category category1 = new Category(4, 13);
        Category category2 = new Category(4, 13);
        assertTrue(category1.matches(category2));
    }
}
