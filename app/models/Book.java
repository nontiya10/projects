package models;

import javassist.runtime.Desc;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 19/11/2561.
 */
@Entity
@Table(name = "tbbooks")
public class Book extends Model {
    @Id
    private String book_id;
    private String  book_date,book_dates,book_time;
    private String Book_Amount,Book_Status,status;

    @ManyToOne
    private User user;
    @ManyToOne
    private Customer customer;

    public Book() {

    }

    public Book(String book_id, String book_date, String book_dates, String book_time, String book_Amount, String book_Status, User user, Customer customer,String status) {
        this.book_id = book_id;
        this.book_date = book_date;
        this.book_dates = book_dates;
        this.book_time = book_time;
        Book_Amount = book_Amount;
        Book_Status = book_Status;
        this.user = user;
        this.customer = customer;
        this.status = status;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getBook_dates() {
        return book_dates;
    }

    public void setBook_dates(String book_dates) {
        this.book_dates = book_dates;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getBook_Amount() {
        return Book_Amount;
    }

    public void setBook_Amount(String book_Amount) {
        Book_Amount = book_Amount;
    }

    public String getBook_Status() {
        return Book_Status;
    }

    public void setBook_Status(String book_Status) {
        Book_Status = book_Status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Finder<String, Book> getFinder() {
        return finder;
    }

    public  static Finder<String,Book> finder = new Finder<String, Book>(String.class,Book.class);
    public static List<Book> bookList(){
        return finder.where().orderBy().desc("book_id").findList();
    }
    public static void insert (Book book){book.save();}
    public  static void update (Book book){book.update();}
    public  static  void delete (Book book){book.delete();}

    public static Book cusBook (String id){
        return finder.where().eq("customer.cus_Id",id).findUnique();
    }
    public static List<Book> UsersBook (){return  finder.where().eq("Book_Status","2").findList();}
}
