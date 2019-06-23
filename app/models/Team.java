package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table (name = "tbTeam")
public class Team extends Model {
   @Id
    private  String id;

    @ManyToOne
    private Book book;

     private String date,time;
    private String status;

    @OneToMany(mappedBy = "team",cascade=CascadeType.ALL )
    private List<TeamList> teamLists = new ArrayList<TeamList>();


    public Team(String id, Book book, String date, String time,String status) {
        this.id = id;
        this.book = book;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  static Finder<String,Team> finder = new Finder<String, Team>(String.class,Team.class);

    public static  List<Team> teamList (){
        return  finder.all();
    }
    public static void insert (Team team){
        team.save();
    }
    public static void update (Team team){
        team.update();
    }

    public static void delete (Team team){
        team.delete();
    }
}
