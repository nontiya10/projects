package models;

import com.avaje.ebean.Expr;
import org.joda.time.DateTime;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 27/10/2561.
 */
@Entity
@Table(name = "tbWork")
public class SaveWork  extends Model{
    @Id
    private String work_id;
    private String  Work_date;
    private String Work_month,Work_year;
    private String  Work_Timein,Work_Timeout;

    @ManyToOne
    private User user;



    public SaveWork() {
    }

    public SaveWork(String work_id, String work_date, String work_month,String work_year, String work_Timein, String work_Timeout, User user) {
        this.work_id = work_id;
        Work_date = work_date;
        Work_month = work_month;
        this.Work_year = work_year;
        Work_Timein = work_Timein;
        Work_Timeout = work_Timeout;
        this.user = user;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getWork_date() {
        return Work_date;
    }

    public void setWork_date(String work_date) {
        Work_date = work_date;
    }

    public String getWork_month() {
        return Work_month;
    }

    public void setWork_month(String work_month) {
        Work_month = work_month;
    }

    public String getWork_year() {
        return Work_year;
    }

    public void setWork_year(String work_year) {
        Work_year = work_year;
    }

    public String getWork_Timein() {
        return Work_Timein;
    }

    public void setWork_Timein(String work_Timein) {
        Work_Timein = work_Timein;
    }

    public String getWork_Timeout() {
        return Work_Timeout;
    }

    public void setWork_Timeout(String work_Timeout) {
        Work_Timeout = work_Timeout;
    }

    public models.User getUser() {
        return user;
    }

    public void setUser(models.User user) {
        this.user = user;
    }

    public  static Finder<String,SaveWork> finder = new Finder<String, SaveWork>(String.class,SaveWork.class);
    public static List<SaveWork> workList(){return finder.all();
    }

    public static void insert (SaveWork work){work.save();}
    public  static void workupdate (SaveWork work){work.update();
    }
    public  static  void workdelete (SaveWork work){work.delete();}

    public static List<SaveWork> userWork (String userid){
        return finder.where().eq("user.username", userid).findList();
    }
}
