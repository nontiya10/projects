package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table (name = "tbKeepList")
public class KeepList extends Model {
    @Id
    private String KL_Id;
    private String Amount;

    @ManyToOne
    private Keep keep;

    @ManyToOne
    private Product product;

    public KeepList() {
    }

    public KeepList(String KL_Id,String amount, Keep keep,Product product) {
        this.KL_Id = KL_Id;

        Amount = amount;
        this.keep = keep;
        this.product = product;
    }

    public String getKL_Id() {
        return KL_Id;
    }

    public void setKL_Id(String KL_Id) {
        this.KL_Id = KL_Id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Keep getKeep() {
        return keep;
    }

    public void setKeep(Keep keep) {
        this.keep = keep;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static Model.Finder<String, KeepList> find =
            new Model.Finder<String, KeepList>(String.class,KeepList.class);

    public static List<KeepList> list(){
        return find.all();
    }
    public static void create(KeepList keepList){
        keepList.save();
    }

    public  static void update (KeepList keepList){keepList.update();
    }
    public  static  void delete (KeepList keepList){keepList.delete();}
}
