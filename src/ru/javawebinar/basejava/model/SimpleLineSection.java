package ru.javawebinar.basejava.model;

import java.util.Objects;

public class SimpleLineSection<C> extends AbstractSection {
    private C item;

    public SimpleLineSection(C item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleLineSection<?> that = (SimpleLineSection<?>) o;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    @Override
    public String toString() {
        return item.toString();
    }

    public C getItem() {
        return item;
    }

    public void setItem(C item) {
        this.item = item;
    }
}
