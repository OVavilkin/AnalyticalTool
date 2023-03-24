package net.associal.analyticaltool.queries.category;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Factory for Categories <br/>
 * Takes a String value and parses it <br/>
 */
public class CategoryProvider {

    /**
     * Creates a Category based on String provided <br/>
     * @param s <br/>
     * Examples: "*", "1", "1.2" <br/>
     * @return Category <br/>
     */
    public static Category from(String s) {
        if(s.startsWith("*"))
            return new Category(true);

        List<Integer> categories = new ArrayList<>();
        StringBuilder sb = new StringBuilder(s);
        Pattern pattern = Pattern.compile("^(\\d*)((?:\\.\\d*)*?)$");
        Matcher m = pattern.matcher(sb);

        while(m.find()) {
            categories.add(Integer.valueOf(m.group(1)));
            if( sb.delete(m.start(1), m.end(1) + 1).length() > 0) {
                m = pattern.matcher(sb);
            }
        }

        return new Category(categories.toArray(new Integer[0]));
    }

}
