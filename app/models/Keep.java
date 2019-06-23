package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table(name = "tbKeep")
public class Keep extends Model {
    @Id
    private String Keep_Id;
    private String Keep_Date;

    @ManyToOne
    private User user;



    public Keep() {
    }

    public Keep(String keep_Id, String keep_Date ,User user) {
        Keep_Id = keep_Id;
        Keep_Date = keep_Date;
        this.user = user;
    }

    public String getKeep_Id() {
        return Keep_Id;
    }

    public void setKeep_Id(String keep_Id) {
        Keep_Id = keep_Id;
    }

    public String getKeep_Date() {
        return Keep_Date;
    }

    public void setKeep_Date(String keep_Date) {
        Keep_Date = keep_Date;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Model.Finder<String, Keep> find =
            new Model.Finder<String, Keep>(String.class,Keep.class);

    public static List<Keep> list(){
        return find.all();
    }
    public static void create(Keep keep){
        keep.save();
    }

    public  static void update (Keep keep){keep.update();
    }
    public  static  void delete (Keep keep){keep.delete();}
}
