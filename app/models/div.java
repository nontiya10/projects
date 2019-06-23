package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table(name = "tbDiv")
public class div extends Model{
    @Id
    private String Div_Id;
    private String Div_Date, Div_Datediv;

    @ManyToOne
    private Sale sale;

    @ManyToOne
    private User user;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    private  ScheduleList scheduleList;

    @ManyToOne
    private  SaveWork saveWork;


    public div() {
    }

    public div(String div_Id, String div_Date, String div_Datediv, Sale sale, User user, Payment payment, ScheduleList scheduleList, SaveWork saveWork) {
        Div_Id = div_Id;
        Div_Date = div_Date;
        Div_Datediv = div_Datediv;
        this.sale = sale;
        this.user = user;
        this.payment = payment;
        this.scheduleList = scheduleList;
        this.saveWork = saveWork;
    }

    public String getDiv_Id() {
        return Div_Id;
    }

    public void setDiv_Id(String div_Id) {
        Div_Id = div_Id;
    }

    public String getDiv_Date() {
        return Div_Date;
    }

    public void setDiv_Date(String div_Date) {
        Div_Date = div_Date;
    }

    public String getDiv_Datediv() {
        return Div_Datediv;
    }

    public void setDiv_Datediv(String div_Datediv) {
        Div_Datediv = div_Datediv;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ScheduleList scheduleList) {
        this.scheduleList = scheduleList;
    }

    public SaveWork getSaveWork() {
        return saveWork;
    }

    public void setSaveWork(SaveWork saveWork) {
        this.saveWork = saveWork;
    }

    public static Finder<String, div> getFinder() {
        return finder;
    }

    public static void setFinder(Finder<String, div> finder) {
        div.finder = finder;
    }

    public  static Finder<String,div> finder = new Finder<String, div>(String.class,div.class);
    public static List<div> divList(){return finder.all();
    }
    public static void insert (models.div divs){divs.save();}
    public  static void update (div divs){divs.update();
    }
    public  static  void delete (div divs){divs.delete();}
}