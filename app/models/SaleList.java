package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 19/12/2561.
 */
@Entity
@Table (name = "tbSaleList")
public class SaleList extends Model{
    @Id
    private String id;
    private String Price,Amount;
    private String Total;

    @ManyToOne
    private Sale sale;
    @ManyToOne
    private Product product;

    public SaleList() {
    }

    public SaleList(String id, String price, String amount, String total, Sale sale, Product product) {
        this.id = id;
        Price = price;
        Amount = amount;
        Total = total;
        this.sale = sale;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static Model.Finder<String, SaleList> find =
            new Model.Finder<String, SaleList>(String.class,SaleList.class);

    public static List<SaleList> list(){
        return find.all();
    }
    public static void create(SaleList saleList){
        saleList.save();
    }

    public  static void update (SaleList saleList){saleList.update();
    }
    public  static  void delete (SaleList  saleList){saleList.delete();}
}
