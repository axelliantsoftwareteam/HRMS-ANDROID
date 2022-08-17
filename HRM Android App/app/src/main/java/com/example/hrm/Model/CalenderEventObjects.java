package com.example.hrm.Model;

import java.util.Date;

/**
 * Created by akumar1 on 11/22/2017.
 */

public class CalenderEventObjects {
    private int id;
    private String title;
    private Date date;

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    private Date edate;

    public CalenderEventObjects() {
    }

    public CalenderEventObjects(String title, Date date,Date edate) {
        this.title = title;
       this.date=date;
        this.edate = edate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CalenderEventObjects{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", edate=" + edate +
                '}';
    }
}