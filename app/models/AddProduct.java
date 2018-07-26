package models;

/**
 * Created by Admin on 25/7/2561.
 */
public class AddProduct {
    private String name,id,comment,sex;
    private Double price;

    public AddProduct() {
    }

    public AddProduct(String name, String id, String comment, String sex, Double price) {
        this.name = name;
        this.id = id;
        this.comment = comment;
        this.sex = sex;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
