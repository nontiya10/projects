package models;

import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by Admin on 6/4/2562.
 */
public class KeepAdd extends Model {
    private String KL_Id;
    private String Amount;
    private Keep keep;
    private Product product;

    public KeepAdd(String KL_Id, String amount, Keep keep, Product product) {
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
}
