package models;

import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by Admin on 25/1/2562.
 */
public class CusSchedule extends Model {
    private String ListSch_Id;
    private String Sch_Date, Sch_Dates;
    private String Sch_Timein, Sch_Timeout;
    private User user;

    public CusSchedule() {
    }

    public CusSchedule(String listSch_Id, String sch_Date, String sch_Dates, String sch_Timein, String sch_Timeout, User user) {
        this.ListSch_Id = listSch_Id;
        this.Sch_Date = sch_Date;
        this.Sch_Dates = sch_Dates;
        this.Sch_Timein = sch_Timein;
        this.Sch_Timeout = sch_Timeout;
        this.user = user;
    }

    public String getListSch_Id() {
        return ListSch_Id;
    }

    public void setListSch_Id(String listSch_Id) {
        ListSch_Id = listSch_Id;
    }

    public String getSch_Date() {
        return Sch_Date;
    }

    public void setSch_Date(String sch_Date) {
        Sch_Date = sch_Date;
    }

    public String getSch_Dates() {
        return Sch_Dates;
    }

    public void setSch_Dates(String sch_Dates) {
        Sch_Dates = sch_Dates;
    }

    public String getSch_Timein() {
        return Sch_Timein;
    }

    public void setSch_Timein(String sch_Timein) {
        Sch_Timein = sch_Timein;
    }

    public String getSch_Timeout() {
        return Sch_Timeout;
    }

    public void setSch_Timeout(String sch_Timeout) {
        Sch_Timeout = sch_Timeout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}