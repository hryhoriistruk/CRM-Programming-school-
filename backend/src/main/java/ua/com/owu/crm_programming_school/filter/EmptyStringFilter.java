package ua.com.owu.crm_programming_school.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
@JsonFilter("emptyStringFilter")
public class EmptyStringFilter {
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            return str == null || str.isEmpty();
        }

        return false;
    }
}

