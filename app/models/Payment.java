package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table (name = "tbPayment")
public class Payment  extends Model{
    @Id
    private String Id;
    private String Pay_Total;
    private String Pay_Date;
    private String Pay_Month,Pay_Year;

    @ManyToOne
    private User user;
    @OneToOne
    private Team Team;
    @ManyToOne
    private  Center center;

    public Payment() {
    }

    public Payment(String id, String pay_Total, String pay_Date,String pay_Month,String pay_Year, User user,Team team,Center center) {
        Id = id;
        Pay_Total = pay_Total;
        Pay_Date = pay_Date;
        this.Pay_Month =pay_Month;
        this.Pay_Year = pay_Year;
        this.user = user;
        Team = team;
        this.center = center;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPay_Total() {
        return Pay_Total;
    }

    public void setPay_Total(String pay_Total) {
        Pay_Total = pay_Total;
    }

    public String getPay_Date() {
        return Pay_Date;
    }

    public void setPay_Date(String pay_Date) {
        Pay_Date = pay_Date;
    }

    public String getPay_Month() {
        return Pay_Month;
    }

    public void setPay_Month(String pay_Month) {
        Pay_Month = pay_Month;
    }

    public String getPay_Year() {
        return Pay_Year;
    }

    public void setPay_Year(String pay_Year) {
        Pay_Year = pay_Year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public models.Team getTeam() {
        return Team;
    }

    public void setTeam(models.Team team) {
        Team = team;
    }

    public Center getCenter() {
        return center;
    }
    public void setCenter(Center center) {
        this.center = center;
    }
    public static Finder<String, Payment> find =
            new Finder<String, Payment>(String.class,Payment.class);

    public static List<Payment> list(){
        return find.all();
    }
    public static void create(Payment payment){
        payment.save();
    }

    public  static void update (Payment payment){payment.update();
    }
    public  static  void delete (Payment payment){payment.delete();}

}

