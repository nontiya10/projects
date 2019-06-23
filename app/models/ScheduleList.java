package models;

import com.avaje.ebean.Expr;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table(name = "tbScheduleList")
public class ScheduleList extends Model {
    @Id
    @Column(name = "ListSch_Id")
    private String ListSch_Id;
    private String Sch_Date,Sch_Dates;
    private String Sch_Timein,Sch_Timeout;

    @ManyToOne
    private User user;



    public ScheduleList() {
    }

    public ScheduleList(String listSch_Id, String sch_Date, String sch_Dates, String sch_Timein, String sch_Timeout, User user) {
        ListSch_Id = listSch_Id;
        Sch_Date = sch_Date;
        Sch_Dates = sch_Dates;
        Sch_Timein = sch_Timein;
        Sch_Timeout = sch_Timeout;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public static Finder<String, ScheduleList> getFinder() {
        return finder;
    }

    public static void setFinder(Finder<String, ScheduleList> finder) {
        ScheduleList.finder = finder;
    }

    public  static Model.Finder<String,ScheduleList> finder = new Model.Finder<String, ScheduleList>(String.class,ScheduleList.class);
    public static List<ScheduleList> ScheduleList(){return finder.all();}
    public static void insert (ScheduleList scheduleList){
        scheduleList.save();
    }

    public static List<ScheduleList> sreachDate (String m, String y){
        return finder.where().and(Expr.eq("Sch_M",m),Expr.eq("Sch_y",y)).findList();
    }

    public static List<ScheduleList> detailSch (String id){
        return  finder.where().eq("user.id", id).findList();
    }

    public static ScheduleList detailSch1 (String id){
        return  finder.where().eq("user.username",id).findUnique();
    }
}
