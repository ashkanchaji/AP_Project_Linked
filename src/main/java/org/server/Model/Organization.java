package org.server.Model;

import java.sql.Date;

public class Organization extends  Model {
    private String name;

    private String rank;

    private Date enterYear;

    private Date exitYear;

    public Organization(String name, String rank, Date enterYear, Date exitYear) {
        this.name = name;
        this.rank = rank;
        this.enterYear = enterYear;
        this.exitYear = exitYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Date getEnterYear() {
        return enterYear;
    }

    public void setEnterYear(Date enterYear) {
        this.enterYear = enterYear;
    }

    public Date getExitYear() {
        return exitYear;
    }

    public void setExitYear(Date exitYear) {
        this.exitYear = exitYear;
    }
}
