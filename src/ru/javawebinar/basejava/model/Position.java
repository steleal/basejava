package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {
    private Company company;
    private YearMonth startDate;
    private YearMonth stopDate;
    private String title;
    private String description;

    public Position(Company company, YearMonth startDate, YearMonth stopDate, String title, String description) {
        this.company = company;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(company, position.company) &&
                Objects.equals(startDate, position.startDate) &&
                Objects.equals(stopDate, position.stopDate) &&
                Objects.equals(title, position.title) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, startDate, stopDate, title, description);
    }

    @Override
    public String toString() {
        return "company=" + company +
                ",\n\tstartDate=" + startDate + ", stopDate=" + stopDate +
                ",\n\ttitle='" + title + '\'' +
                ",\n\tdescription='" + description + '\'';
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public void setStartDate(YearMonth startDate) {
        this.startDate = startDate;
    }

    public YearMonth getStopDate() {
        return stopDate;
    }

    public void setStopDate(YearMonth stopDate) {
        this.stopDate = stopDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
