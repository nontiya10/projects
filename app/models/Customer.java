package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 23/10/2561.
 */
@Entity
@Table(name = "tbcustomer")
public class Customer extends Model{
    @Id
    private String cus_Id;
    private String Cus_Name,Cus_Address ,Cus_Tel ,Cus_Email;

    @OneToMany(mappedBy = "customer",cascade= CascadeType.ALL )
    private List<Book> bookList = new ArrayList<Book>();

    public Customer() {
    }

    public Customer(String cus_Id, String cus_Name, String cus_Address, String cus_Tel, String cus_Email) {
        this.cus_Id = cus_Id;
        Cus_Name = cus_Name;
        Cus_Address = cus_Address;
        Cus_Tel = cus_Tel;
        Cus_Email = cus_Email;

    }

    public String getCus_Id() {
        return cus_Id;
    }

    public void setCus_Id(String cus_Id) {
        this.cus_Id = cus_Id;
    }

    public String getCus_Name() {
        return Cus_Name;
    }

    public void setCus_Name(String cus_Name) {
        Cus_Name = cus_Name;
    }

    public String getCus_Address() {
        return Cus_Address;
    }

    public void setCus_Address(String cus_Address) {
        Cus_Address = cus_Address;
    }

    public String getCus_Tel() {
        return Cus_Tel;
    }

    public void setCus_Tel(String cus_Tel) {
        Cus_Tel = cus_Tel;
    }

    public String getCus_Email() {
        return Cus_Email;
    }

    public void setCus_Email(String cus_Email) {
        Cus_Email = cus_Email;
    }



    public  static Finder<String,Customer> finder = new Finder<String, Customer>(String.class,Customer.class);
    public static List<Customer> customerList(){
        return finder.all();
    }

    public static void insert (Customer customer){customer.save();}
    public  static void update (Customer customer){customer.update();
    }
    public  static  void delete (Customer customer){customer.delete();}


}
