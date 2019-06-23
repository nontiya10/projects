package models;

import com.avaje.ebean.Expr;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 16/10/2561.
 */
@Entity
@Table (name = "tbuser")
public class User extends Model {
    @Id
    private String username;
    private String Mem_name,Mem_add,Mem_tel,Mem_email,Mem_id;
    private String password,status,id;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL )
    private List<SaveWork> workList = new ArrayList<SaveWork>();

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL )
    private List<Book> bookList = new ArrayList<Book>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private  List<ScheduleList> scheduleListList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private  List<ScheduleL> scheduleL = new ArrayList<>();


    public User() {
    }


    public User(String username, String mem_name, String mem_add, String mem_tel, String mem_email, String mem_id, String password, String status, String id) {
        this.username = username;
        Mem_name = mem_name;
        Mem_add = mem_add;
        Mem_tel = mem_tel;
        Mem_email = mem_email;
        Mem_id = mem_id;
        this.password = password;
        this.status = status;
        this.id = id;
    }

    public String getMem_name() {
        return Mem_name;
    }

    public void setMem_name(String mem_name) {
        Mem_name = mem_name;
    }

    public String getMem_add() {
        return Mem_add;
    }

    public void setMem_add(String mem_add) {
        Mem_add = mem_add;
    }

    public String getMem_tel() {
        return Mem_tel;
    }

    public void setMem_tel(String mem_tel) {
        Mem_tel = mem_tel;
    }

    public String getMem_email() {
        return Mem_email;
    }

    public void setMem_email(String mem_email) {
        Mem_email = mem_email;
    }

    public String getMem_id() {
        return Mem_id;
    }

    public void setMem_id(String mem_id) {
        Mem_id = mem_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  static Finder<String,User> finder = new Finder<String, User>(String.class,User.class);
    public static List<User> userList(){
        return finder.all();
    }
    public static void insert (models.User user){
        user.save();
    }
    public  static void update (User user){
        user.update();
    }
    public  static  void delete (User user){
        user.delete();
    }
    public  static User userlogin (String username,String password){
        return finder.where().and(Expr.eq("username",username),Expr.eq("password",password)).findUnique();
    }

    public static User datauser (String id){
    return finder.where().eq("id",id).findUnique();
    }
}
