package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 23/10/2561.
 */
@Entity
@Table(name = "tbProduct")
public class Product extends Model {
    @Id
    private String Pro_Id;
    private String Pro_Name ,Pro_Amount,Pro_Unit;
    private String Pro_total;

    public Product() {
    }


    public Product(String pro_Id, String pro_Name, String pro_Amount, String pro_Unit,String pro_total) {
        Pro_Id = pro_Id;
        Pro_Name = pro_Name;
        Pro_Amount = pro_Amount;
        Pro_Unit = pro_Unit;
        this.Pro_total = pro_total;
    }

    public String getPro_Id() {
        return Pro_Id;
    }

    public void setPro_Id(String pro_Id) {
        Pro_Id = pro_Id;
    }

    public String getPro_Name() {
        return Pro_Name;
    }

    public void setPro_Name(String pro_Name) {
        Pro_Name = pro_Name;
    }

    public String getPro_Amount() {
        return Pro_Amount;
    }

    public void setPro_Amount(String pro_Amount) {
        Pro_Amount = pro_Amount;
    }
    public String getPro_Unit() {
        return Pro_Unit;
    }

    public void setPro_Unit(String pro_Unit) {
        Pro_Unit = pro_Unit;
    }

    public String getPro_total() {
        return Pro_total;
    }

    public void setPro_total(String pro_total) {
        Pro_total = pro_total;
    }

    public  static Finder<String,Product> finder = new Finder<String, Product>(String.class,Product.class);
    public static List<Product> productList(){return finder.all();
    }
    public static void insert (models.Product product){product.save();}
    public  static void update (Product product){product.update();
    }
    public  static  void delete (Product product){product.delete();}
}
