package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by Admin on 6/4/2562.
 */
@Entity (name = "tbTeamList")
public class TeamList extends  Model {
    @Id
    private String teamList_Id;
    private  String teamList_Posit;
    @ManyToOne
    @JoinColumn(name="username")
    private User user;

@ManyToOne
private Team team;

    public TeamList() {
    }

    public TeamList(String teamList_Id, String teamList_Posit, User user, Team team) {
        this.teamList_Id = teamList_Id;
        this.teamList_Posit = teamList_Posit;
        this.user = user;
        this.team = team;
    }

    public String getTeamList_Id() {
        return teamList_Id;
    }

    public void setTeamList_Id(String teamList_Id) {
        this.teamList_Id = teamList_Id;
    }

    public String getTeamList_Posit() {
        return teamList_Posit;
    }

    public void setTeamList_Posit(String teamList_Posit) {
        this.teamList_Posit = teamList_Posit;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public static Model.Finder<String, TeamList> getFinder() {
        return finder;
    }

    public static void setFinder(Model.Finder<String, TeamList> finder) {
        TeamList.finder = finder;
    }

    public  static Model.Finder<String,TeamList> finder = new Model.Finder<String, TeamList>(String.class,TeamList.class);
    public static List<TeamList> teamListList(){return finder.all();}
    public static void insert (TeamList teamList){teamList.save();
    }
    public static  void update (TeamList teamList){teamList.update(); }
    public  static  void delete (TeamList teamList){teamList.delete();}


}
