package org.example.queries.category;

import org.example.queries.category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryProvider {

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

        return new Category(categories.toArray(new Integer[categories.size()]));
    }

}
