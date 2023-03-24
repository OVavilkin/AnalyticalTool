package net.associal.analyticaltool.queries.category;

public class Category {
    private boolean star;
    private Integer[] categories;

    public Category(boolean star) {
        this.star = star;
    }

    public Category(Integer... categories) {
        this.categories = categories;
    }

    public boolean isStar() {
        return star;
    }

    public Integer[] getCategories() {
        return categories;
    }

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
