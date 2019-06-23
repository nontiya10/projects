package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 16/2/2562.
 */
@Entity
@Table(name = "tbScheduleL")
public class ScheduleL extends Model {

    @Id
    @Column(name = "Sch_id")
    private String Sch_id;

    @ManyToOne
    @JoinColumn(name="list_sch_id")
    private ScheduleList scheduleList;

    @ManyToOne
    @JoinColumn(name="username")
    private User user;


    public ScheduleL() {
    }

    public ScheduleL(String sch_id, ScheduleList scheduleList, User user) {
        Sch_id = sch_id;
        this.scheduleList = scheduleList;
        this.user = user;
    }

    public String getSch_id() {
        return Sch_id;
    }

    public void setSch_id(String sch_id) {
        Sch_id = sch_id;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Finder<String, ScheduleL> find =
            new Finder<String, ScheduleL>(String.class,ScheduleL.class);

    public static List<ScheduleL> list(){
        return find.all();
    }
    public static void create(ScheduleL scheduleL){
        scheduleL.save();
    }

    public  static void update (ScheduleL scheduleL){scheduleL.update();
    }
    public  static  void delete (ScheduleL scheduleL){scheduleL.delete();}


}
