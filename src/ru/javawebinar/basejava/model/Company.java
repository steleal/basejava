package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Company {
    private String title;
    private String url;

    public Company(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(title, company.title) &&
                Objects.equals(url, company.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
