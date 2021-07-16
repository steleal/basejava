package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListSection<C> extends AbstractSection {
    private List<C> items;

    public ListSection(List<C> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection<?> that = (ListSection<?>) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    public List<C> getItems() {
        return items;
    }

    public void setItems(List<C> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return items.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
