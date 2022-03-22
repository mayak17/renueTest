package models;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

@Data
public class Airport implements Comparable<Airport> {
    @Nullable
    private Integer rowIndex;
    private String searchField;

    public Airport(Integer rowIndex, String field) {
        this.rowIndex = rowIndex;
        this.searchField = field;
    }

    public boolean startWithPrefix(String prefix) {
        return this.searchField.startsWith(prefix);
    }

    @Override
    public int compareTo(Airport o) {
        return this.searchField.compareTo(o.getSearchField());
    }
}
