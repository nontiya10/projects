package models;

import play.db.ebean.Model;

/**
 * Created by Admin on 4/4/2562.
 */
public class CusTeam extends Model{
    private String team_Id;
    private  String team_Posit;
    private Book book;
    private User user;

    public CusTeam(String team_Id, String team_Posit, Book book, User user) {
        this.team_Id = team_Id;
        this.team_Posit = team_Posit;
        this.book = book;
        this.user = user;
    }

    public String getTeam_Id() {
        return team_Id;
    }

    public void setTeam_Id(String team_Id) {
        this.team_Id = team_Id;
    }

    public String getTeam_Posit() {
        return team_Posit;
    }

    public void setTeam_Posit(String team_Posit) {
        this.team_Posit = team_Posit;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
