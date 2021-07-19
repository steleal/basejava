package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListSection extends AbstractSection {
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must be not null");
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return items.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public List<String> getItems() {
        return items;
    }

}
