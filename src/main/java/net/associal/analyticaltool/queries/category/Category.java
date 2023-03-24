package net.associal.analyticaltool.queries.category;

/**
 * Represents a Service <br/>
 * service_id[.variation_id] <br/>
 * And/or Question <br/>
 * question_type_id[.category_id.[sub-category_id]
 */
public class Category {
    private boolean star;
    private Integer[] categories;

    /**
     * Creates a category that is a star <br/>
     * D 1 * P 8.10.2012-20.11.2012 <br/>
     * the "Question" category is a star in above scenario <br/>
     * <br/>
     * @param star
     */
    public Category(boolean star) {
        this.star = star;
    }

    /**
     * Creates a category based on array of category_ids <br/>
     * or coma separated category_ids <br/>
     * <br/>
     * @param categories
     */
    public Category(Integer... categories) {
        this.categories = categories;
    }

    public boolean isStar() {
        return star;
    }

    public Integer[] getCategories() {
        return categories;
    }

    /**
     * Checks if subject category matches this category <br/>
     * 4.13 matches 4 <br/>
     * 4 matches * <br/>
     * 4.15.2 matches 4.15.2 <br/>
     * <br/>
     * @param category <br/>
     * @return boolean <br/>
     */
    public boolean matches(Category category) {
        // return true if matches all cases
        if(star)
            return true;

        Integer[] other = category.getCategories();

        for(int i = 0; i < categories.length; i++) {

            if(other.length == i) {
                // we reached last sub_category for the "other" object
                // like comparing 4.13 to 4
                return false;
            }

            if(categories[i] != other[i]) {
                // if this.(sub)category doesn't match the other obj category
                // we should complete the cycle;
                // continue to next category otherwise.
                return false;
            }
        }

        // all categories matched, we can return true
        return true;
    }
}
