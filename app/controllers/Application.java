package controllers;

import models.AddProduct;
import models.Products;
import play.*;
import play.api.mvc.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import play.api.templates.Html;
import play.mvc.*;

import play.mvc.Controller;
import views.html.*;

public class Application extends Controller {
    public static Products Pro1;
    public static Products Pro2;
    public static Products Pro3;
    public static Products Pro4;
    public static Products Pro5;
    public static Products Pro6;



    public  static Result Main (Html Content){
        return ok(main.render(Content));
    }

    public static Result index() {
        return Main(Product.render());
    }
    public static Result Product1() {
        return Main(Product1.render());
    }
    public static Result  Product2 () {
        return Main(Product2.render());
    }
    public static  Result ShowAddProduct(){
        DynamicForm inputproduct = Form.form().bindFromRequest();
        String name,id,comment,sex;
        double price;
        name = inputproduct.get("name");
        id = inputproduct.get("id");
        comment = inputproduct.get("comment");
        sex = inputproduct.get("sex");
        price = Double.parseDouble(inputproduct.get("price"));
        AddProduct output = new AddProduct(name, id,comment, sex, price);
        return Main(ShowAddProduct.render(output));}


    public static Result Products(){
        Pro1 = new Products("P001","G-SHOCK PREMIUM รุ่น GA-1100-2BDR","CASIO","รายละเอียด...",8415.00,5);
        Pro2 = new Products("P002","G-SHOCK รุ่น DW-6900EW-7JF","CASIO","ราละเอียด...",3900.00,10);
        Pro3 = new Products("P003","G-SHOCK PREMIUM รุ่น GA-1100-9GDR","CASIO","รายละเอียด...",6930.00,5);
        Pro4 = new Products("P004","G-Shock รุ่น AW-591-2ADR","CASIO","รายละเอียด...",2460.00,4);
        Pro5 = new Products("P005","G-SHOCK รุ่น GA-700-4ADR","CASIO","รายละเอีย...",3740.00,8);
        Pro6 = new Products("P006","G-SHOCK รุ่น GST-S310D-1ADR","CASIO","รายละเอียด...",11500.00,4);
        return Main(views.html.Products.render(Pro1,Pro2,Pro3,Pro4,Pro5,Pro6));


    }
    public static Result MyFrom() {return Main(From.render());}

}
