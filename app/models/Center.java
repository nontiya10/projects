package models;

import com.avaje.ebean.Expr;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 23/10/2561.
 */
@Entity
@Table(name = "tbcenter")
public class Center extends Model {
    @Id
    private String Cen_Id;
    private String Cen_Name;
    private String Cen_Address,Cen_Tel,Cen_Email,Cen_Price;
    private String Cen_Logo;



    public Center(String cen_Id, String cen_Name, String cen_Address, String cen_Tel, String cen_Email,String cen_Price,String cen_Logo) {
       this.Cen_Id=cen_Id;
        this.Cen_Name = cen_Name;
        this.Cen_Address = cen_Address;
        this.Cen_Tel = cen_Tel;
        this.Cen_Email = cen_Email;
        this.Cen_Price = cen_Price;
        this.Cen_Logo=cen_Logo;
}

    public String getCen_Id() {
        return Cen_Id;
    }

    public void setCen_Id(String cen_Id) {
        Cen_Id = cen_Id;
    }

    public String getCen_Name() {
        return Cen_Name;
    }

    public void setCen_Name(String cen_Name) {
        Cen_Name = cen_Name;
    }

    public String getCen_Address() {
        return Cen_Address;
    }

    public void setCen_Address(String cen_Address) {
        Cen_Address = cen_Address;
    }

    public String getCen_Tel() {
        return Cen_Tel;
    }

    public void setCen_Tel(String cen_Tel) {
        Cen_Tel = cen_Tel;
    }

    public String getCen_Email() {
        return Cen_Email;
    }

    public void setCen_Email(String cen_Email) {
        Cen_Email = cen_Email;
    }

    public String getCen_Price() {
        return Cen_Price;
    }

    public void setCen_Price(String cen_Price) {
        Cen_Price = cen_Price;
    }



    public String getCen_Logo() {
        return Cen_Logo;
    }

    public void setCen_Logo(String cen_Logo) {
        Cen_Logo = cen_Logo;
    }

    public  static Finder<String,Center> finder = new Finder<String, Center>(String.class,Center.class);
    public static List<Center> centerList(){
        return finder.all();
    }
    public static void insert (models.Center center){center.save();}
    public  static void update (Center center){
        center.update();
    }
    public  static  void delete (Center center){center.delete();}

}

