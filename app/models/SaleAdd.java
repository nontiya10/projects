package models;

/**
 * Created by Admin on 3/5/2562.
 */
public class SaleAdd {

    private String id;
    private String Price,Amount;

    private String Total;
    private Sale sale;
    private Product product;

    public SaleAdd(String id, String price, String amount,String Total ,Sale sale, Product product) {
        this.id = id;
        Price = price;
        Amount = amount;
        this.Total = Total;
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
}
