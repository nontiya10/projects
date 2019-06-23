package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table (name = "tbSale")
public class Sale extends Model{
    @Id
    private String Sale_Id;
    private String Sale_date,total;
    private String Sale_month,Sale_year;


    @ManyToOne
    private User user;

    public Sale() {
    }


    public Sale(String sale_Id, String sale_date, String total, String sale_month, String sale_year, User user) {
        Sale_Id = sale_Id;
        Sale_date = sale_date;
        this.total = total;
        Sale_month = sale_month;
        Sale_year = sale_year;
        this.user = user;
    }

    public String getSale_Id() {
        return Sale_Id;
    }

    public void setSale_Id(String sale_Id) {
        Sale_Id = sale_Id;
    }

    public String getSale_date() {
        return Sale_date;
    }

    public void setSale_date(String sale_date) {
        Sale_date = sale_date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSale_month() {
        return Sale_month;
    }

    public void setSale_month(String sale_month) {
        Sale_month = sale_month;
    }

    public String getSale_year() {
        return Sale_year;
    }

    public void setSale_year(String sale_year) {
        Sale_year = sale_year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public static Model.Finder<String, Sale> find =
            new Model.Finder<String, Sale>(String.class,Sale.class);

    public static List<Sale> list(){
        return find.all();
    }
    public static void create(Sale sale){
        sale.save();
    }

    public  static void update (Sale sale){sale.update();
    }
    public  static  void delete (Sale  sale){sale.delete();}

}
