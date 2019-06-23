package controllers;
import com.avaje.ebean.Expr;
import models.*;
import org.joda.time.DateTime;
import play.Play;
import play.api.templates.Html;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.math.Ordering;
import views.html.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Application extends Controller {

    public static List<User> userList = new ArrayList<User>();

    public static Result login() {
        if (session("username") == null) {
            return ok(login.render());
        } else {
            return index();
        }
    }

    public static Result Logout() {
        session().clear();
        return redirect("/");
    }

    public static Result registerform() {
        return ok(Register.render());
    }

    public static Result Adduser() {
        DynamicForm newuser = Form.form().bindFromRequest();
        Random random = new Random();
        String sId = Integer.toString(random.nextInt(100000) + 1);
        String username = newuser.get("username");
        String Mem_name = newuser.get("Mem_name");
        String Mem_id = newuser.get("Mem_id");
        String Mem_tel = newuser.get("Mem_tel");
        String Mem_email = newuser.get("Mem_email");
        String Mem_add = newuser.get("Mem_add");
        String password = newuser.get("password");
        String repeat = newuser.get("repeat");
        String status = "user";
        if (!password.equals(repeat)) {
            flash("error", "คุณป้อนรหัสผ่านไม่ตรงกัน กรุณากรอกรหัสผ่านใหม่");
            return ok(Register.render());
        } else {
            User chk;
            chk = user.finder.byId(username);
            if (chk != null) {
                flash("error", "User นี้มีผู้ใช้งานเเล้ว");
                return ok(Register.render());
            } else {
                models.User user1 = new User(username, Mem_name, Mem_add, Mem_tel, Mem_email, Mem_id, password, status, sId);
                models.User.insert(user1);
                flash("success", "คุณป้อนข้อมูลสำเร็จ");
                return login();
            }

        }

    }

    public static Result index() {
        if (session("username") == null) {
            return login();
        }
        return main(index.render());
    }

    public static Result main(Html content) {
        return ok(main.render(content));
    }

    public static Result userlogin() {
        DynamicForm userlogin = Form.form().bindFromRequest();
        String username = userlogin.get("username");
        String password = userlogin.get("password");
        User userdata = user.userlogin(username, password);
        if (userdata != null) {
            session("Mem_name", userdata.getMem_name());
            session("Mem_id", userdata.getMem_id());
            session("Mem_tel", userdata.getMem_tel());
            session("Mem_email", userdata.getMem_email());
            session("Mem_add", userdata.getMem_add());
            session("username", userdata.getUsername());
            session("status", userdata.getStatus());
            return index();
        } else {
            flash("error", "กรุณาตรวจสอบ Username Password .กรุณาตรวจสอบใหม่ด้วยค่ะ!");
            return login();
        }

    }

    //Center
    public static List<Center> centerList = new ArrayList<Center>();
    public static Form<Center> centerForm = Form.form(Center.class);
    public static Center center;

    public static Result centerList() {
        centerList = models.Center.centerList();
        return main(showCenter.render(centerList));
    }


    public static Result formAddCenter() {
        centerForm = Form.form(models.Center.class);
        return main(inputCenter.render(centerForm));
    }

    //pathLogo
    public static String logo = Play.application().configuration().getString("logo");

    public static Result insertCenter() {
        Form<Center> newCenter = centerForm.bindFromRequest();
        if (newCenter.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด้วยค่ะ!");
            return main(inputCenter.render(newCenter));
        } else {
            center = newCenter.get();
            centerList = Center.centerList();
            int numlist = centerList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (centerList.size() == 0) {
                sId = "CEN-000001";
            } else {
                lastNum = centerList.get(numlist - 1).getCen_Id();
                lastNum = lastNum.substring(4);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "CEN-00000" + id;
                } else if (id < 100) {
                    sId = "CEN-0000" + id;
                } else if (id < 1000) {
                    sId = "CEN-000" + id;
                } else if (id < 10000) {
                    sId = "CEN-00" + id;
                } else if (id < 100000) {
                    sId = "CEN-0" + id;
                } else {
                    sId = "CEN-" + id;
                }
            }
            center.setCen_Id(sId);
//Logo
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("Cen_Logo");
            String fileName, contentType;
            contentType = picture.getContentType();
            File file = picture.getFile();
            fileName = picture.getFilename();
            if (contentType.startsWith("images")) {
                return Application.main(inputCenter.render(newCenter));
            }
            fileName = center.getCen_Id() + fileName.substring(fileName.lastIndexOf("."));
            file.renameTo(new File(logo, fileName));
            center.setCen_Logo(fileName);

            models.Center.insert(center);
            return redirect("/center");
        }
    }

    public static String upId;

    public static Result editCenter(String id) {
        center = models.Center.finder.byId(id);
        upId = id;
        if (center == null) {
            flash("EditError", "อย่ามาเเสดงแก้URL!");
            return centerList();
        } else {
            session("Cen_Id", center.getCen_Id());
            centerForm = Form.form(models.Center.class).fill(center);
            return main(editCenter.render(centerForm));
        }
    }

    public static Result updateCenter() {

        Form<Center> dataUpdate = centerForm.bindFromRequest();
        if (dataUpdate.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด้วยค่ะ!");
            return main(editCenter.render(dataUpdate));
        } else {
//            String Cen_Name = dataUpdate.get().getCen_Name();
//            String Cen_Add = dataUpdate.get().getCen_Address();
//            String Cen_Tel = dataUpdate.get().getCen_Tel();
//            String Cen_Email = dataUpdate.get().getCen_Email();
//            String Cen_Price = dataUpdate.get().getCen_Price();
//            String Cen_Logo =dataUpdate.get().getCen_Logo();
//            center updateCenter = new Center(session("Cen_Id"),Cen_Name, Cen_Add, Cen_Tel, Cen_Email,Cen_Price,Cen_Logo);
            center = dataUpdate.get();
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart picture = body.getFile("Cen_Logo");
            if (picture != null) {
                String fileName = picture.getFilename();
                String extension = fileName.substring(fileName.indexOf("."));
                String realName = center.getCen_Id() + extension;
                File file = picture.getFile();
                File temp = new File("public/images/center/" + realName);
                if (temp.exists()) {
                    temp.delete();
                }
                file.renameTo(new File("public/images/center/" + realName));
                center.setCen_Logo(realName);
            }
            center.setCen_Id(upId);
            center.setCen_Logo(center.getCen_Logo());
            models.Center.update(center);
            return centerList();
        }
    }

    public static Result deleteCenter(String id) {
        center = models.Center.finder.byId(id);
        if (center != null) {
            models.Center.delete(center);
        }
        return centerList();
    }

    //user

    public static Form<User> userForm = Form.form(User.class);
    public static User user;
    public static String Uid = "";

    public static Result userList() {
        userList = models.User.userList();
        return main(showUser.render(userList));
    }

    public static Result formAdduser() {
        userForm = Form.form(User.class);
        return main(inputUser.render(userForm));
    }

    public static Result insertuser() {
        Form<User> newuser = userForm.bindFromRequest();
        if (newuser.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(inputUser.render(newuser));
        } else {
            user = newuser.get();


            Random random = new Random();
            String sId = Integer.toString(random.nextInt(100000) + 1);


            user.setId(sId);
            models.User.insert(user);

            return redirect("/user");
        }
    }

    public static Result edituser(String id) {

        Uid = id;

        user = models.User.finder.byId(id);
        if (user == null) {
            flash("EditError", "อย่ามาเเสดงแก้URL!");
            return userList();
        } else {
            session("username", user.getUsername());
            userForm = Form.form(models.User.class).fill(user);
            return main(editUser.render(userForm));
        }
    }

    public static Result updateuser() {
        Form<User> dataUpdate = userForm.bindFromRequest();
        if (dataUpdate.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(editUser.render(dataUpdate));
        } else {
            user = dataUpdate.get();
            user.setUsername(Uid);
            User.update(user);

            return userList();
        }
    }

    public static Result deleteuser(String id) {
        user = models.User.finder.byId(id);
        ScheduleList test = ScheduleList.detailSch1(id);
        if (user != null) {
            if (session("username").equals(id)) {
                flash("error", "กำลังใช้งานอยู่ไม่สามารถลบได้");
                return userList();
            } else if (test != null) {
                flash("error", "มีการทำธุรกรรมเเล้ว");
                return userList();
            } else {
                models.User.delete(user);
            }
        }
        return userList();
    }


    //Customer
    public static List<Customer> customerList = new ArrayList<Customer>();
    public static Form<Customer> customerForm = Form.form(Customer.class);
    public static Customer customer;

    public static Result customerList() {
        customerList = models.Customer.customerList();
        return main(showCustomer.render(customerList));
    }

    public static Result formAddCustomer() {
        customerForm = Form.form(models.Customer.class);
        return main(inputCustomer.render(customerForm));
    }

    public static Result insertCustomer() {
        Form<Customer> newCustomer = customerForm.bindFromRequest();
        if (newCustomer.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(inputCustomer.render(newCustomer));
        } else {
            customer = newCustomer.get();
            customerList = Customer.customerList();
            int numlist = customerList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (customerList.size() == 0) {
                sId = "CUS-000001";
            } else {
                lastNum = customerList.get(numlist - 1).getCus_Id();
                lastNum = lastNum.substring(4);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "CUS-00000" + id;
                } else if (id < 100) {
                    sId = "CUS-0000" + id;
                } else if (id < 1000) {
                    sId = "CUS-000" + id;
                } else if (id < 10000) {
                    sId = "CUS-00" + id;
                } else if (id < 100000) {
                    sId = "CUS-0" + id;
                } else {
                    sId = "CUS-" + id;
                }
            }
            customer.setCus_Id(sId);
            Customer.insert(customer);
            return redirect("/customer");
        }
    }


    public static Result editCustomer(String id) {
        customer = models.Customer.finder.byId(id);
        if (customer == null) {
            flash("EditError", "อย่ามาเเสดงแก้URL!");
            return customerList();
        } else {
            session("Cus_Id", customer.getCus_Id());
            customerForm = Form.form(models.Customer.class).fill(customer);
            return main(editCustomer.render(customerForm));
        }
    }

    public static Result updateCustomer() {
        Form<Customer> dataUpdate = customerForm.bindFromRequest();
        if (dataUpdate.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(editCustomer.render(dataUpdate));
        } else {

            String Cus_Name = dataUpdate.get().getCus_Name();
            String Cus_Address = dataUpdate.get().getCus_Address();
            String Cus_Tel = dataUpdate.get().getCus_Tel();
            String Cus_Email = dataUpdate.get().getCus_Email();
            models.Customer updateCustomer = new Customer(session("Cus_Id"), Cus_Name, Cus_Address, Cus_Tel, Cus_Email);
            models.Customer.update(updateCustomer);
            return customerList();
        }
    }

    public static Result deleteCustomer(String id) {

        book = Book.cusBook(id);
        if (book != null) {
            flash("error", "ไม่สามารถลบได้ เนื่องจากรายชื่อนี้ได้ทำธุรกรรมเเล้ว");
        } else {
            customer = models.Customer.finder.byId(id);
            if (customer != null) {
                models.Customer.delete(customer);
            }

        }
        return customerList();
    }


    //Product
    public static List<models.Product> productList = new ArrayList<models.Product>();
    public static Form<models.Product> productForm = Form.form(models.Product.class);
    public static models.Product product;

    public static Result productList() {
        productList = models.Product.productList();
        return main(showProduct.render(productList));
    }

    public static Result formAddProduct() {
        productForm = Form.form(models.Product.class);
        return main(inputProduct.render(productForm));
    }

    public static Result insertProduct() {
        Form<models.Product> newProduct = productForm.bindFromRequest();
        if (newProduct.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(inputProduct.render(newProduct));
        } else {
            product = newProduct.get();
            productList = Product.productList();
            int numlist = productList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (productList.size() == 0) {
                sId = "PD-000001";
            } else {
                lastNum = productList.get(numlist - 1).getPro_Id();
                lastNum = lastNum.substring(3);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "PD-00000" + id;
                } else if (id < 100) {
                    sId = "PD-0000" + id;
                } else if (id < 1000) {
                    sId = "PD-000" + id;
                } else if (id < 10000) {
                    sId = "PD-00" + id;
                } else if (id < 100000) {
                    sId = "PD-0" + id;
                } else {
                    sId = "PD-" + id;
                }
            }
            product.setPro_Id(sId);
            product.setPro_Amount("0");
            Product.insert(product);

            return redirect("/product");
        }
    }

    public static Result editProduct(String id) {
        product = models.Product.finder.byId(id);
        if (product == null) {
            flash("EditError", "อย่ามาเเสดงแก้URL!");
            return productList();
        } else {
            session("pro_Id", product.getPro_Id());
            productForm = Form.form(models.Product.class).fill(product);
            return main(editProduct.render(productForm));
        }
    }

    public static Result updateProduct() {
        Form<models.Product> dataUpdate = productForm.bindFromRequest();
        if (dataUpdate.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(editProduct.render(dataUpdate));
        } else {

            String Pro_Name = dataUpdate.get().getPro_Name();
            String Pro_Amount = dataUpdate.get().getPro_Amount();
            String Pro_Unit = dataUpdate.get().getPro_Unit();
            String Pro_total = dataUpdate.get().getPro_total();
            models.Product updateProduct = new models.Product(session("pro_Id"), Pro_Name, Pro_Amount, Pro_Unit, Pro_total);
            models.Product.update(updateProduct);
            return productList();
        }
    }

    public static Result deleteProduct(String id) {
        product = models.Product.finder.byId(id);
        if (product != null) {
            models.Product.delete(product);
        }
        return productList();
    }


    //work
    public static List<SaveWork> workList = new ArrayList<SaveWork>();
    public static Form<SaveWork> workForm = Form.form(SaveWork.class);
    public static SaveWork work;

    public static Result workList() {
        List<SaveWork> workList = SaveWork.userWork(session().get("username"));
        return main(showSaveWork.render(workList));
    }


    public static Result timeInWork() {
        user = models.User.finder.byId(session("username"));
        SaveWork work = new SaveWork();
        workList = SaveWork.workList();
        int numlist = workList.size();
        int id, idNum;
        String lastNum;
        String sId;
        if (workList.size() == 0) {
            sId = "SW-000001";
        } else {
            lastNum = workList.get(numlist - 1).getWork_id();
            lastNum = lastNum.substring(3);
            idNum = Integer.parseInt(lastNum);
            id = idNum + 1;
            if (id < 10) {
                sId = "SW-00000" + id;
            } else if (id < 100) {
                sId = "SW-0000" + id;
            } else if (id < 1000) {
                sId = "SW-000" + id;
            } else if (id < 10000) {
                sId = "SW-00" + id;
            } else if (id < 100000) {
                sId = "SW-0" + id;
            } else {
                sId = "SW-" + id;
            }
        }
        Date date = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatmonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatyear = new SimpleDateFormat("yyyy");
        String dataDate = formatdate.format(date);
        String dataTime = formattime.format(date);
        String datamonth = formatmonth.format(date);
        String datayear = formatyear.format(date);
        work.setWork_id(sId);
        work.setWork_date(dataDate);
        work.setUser(user);
        work.setWork_month(datamonth);
        work.setWork_year(datayear);
        work.setWork_Timein(dataTime);
        work.setWork_Timeout(null);
        user = User.finder.byId(session("username"));
        scheduleListList = ScheduleList.finder.where().eq("user.id", user.getId()).findList();
        if (scheduleListList.size() == 0) {
            flash("ErrorTimeIn", "ไม่สามารถลงเวลาเข้างานได้เนื่องจากไม่มีรายชื่อในตารางจัดเวร");
        } else {
            SaveWork.insert(work);
        }

        session("timein", new Date().toString());
        List<SaveWork> workList = SaveWork.userWork(session().get("username"));
        return main(showSaveWork.render(workList));
    }

    public static Result timeOutWirk(String id) {
        SaveWork work1 = SaveWork.finder.byId(id);
        Date dates = new Date();
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatmonth =new SimpleDateFormat("MM");
        SimpleDateFormat formatyear = new SimpleDateFormat("yyyy");
        String outformat = formattime.format(dates);
        String datamonth = formatmonth.format(dates);
        String datayear = formatyear.format(dates);
        String work_id;
        String date;
        String work_month;
        String work_year;
        models.User user;
        String in, out;
        if (work1 != null) {
            work_id = work1.getWork_id();
            work_id = work1.getWork_id();
            date = work1.getWork_date();
            work_month = datamonth;
            work_year = datayear;
            user = work1.getUser();
            in = work1.getWork_date();
            out = outformat;
            session().remove("timein");
            SaveWork work = new SaveWork(work_id,date,work_month,work_year, in, out,user);
            if (!date.equals(in)) {
                flash("ErrorTime", "ไม่สามารถลงเวลาออกได้");
            } else {
                SaveWork.workupdate(work);
            }

        }
        List<SaveWork> workList = SaveWork.userWork(session().get("username"));
        return main(showSaveWork.render(workList));
    }


    //Book

    public static List<Book> bookList = new ArrayList<Book>();
    public static Form<Book> bookForm = Form.form(Book.class);
    public static Book book;
    public static Book databook;
    public static String cusID;

    public static Result bookList() {
        bookList = Book.bookList();
        return main(ShowBook.render(bookList));
    }

    public static Result formAddBook() {
        customerList = Customer.customerList();
        bookForm = Form.form(Book.class);
        return main(inputBook.render(bookForm, customerList));
    }


    public static Result insertBook() {
        customerList = Customer.customerList();
        user = User.finder.byId(session("username"));
        Form<models.Book> newbook = bookForm.bindFromRequest();
        if (newbook.hasErrors()) {
            flash("ข้อมูลไม่สมบูรณ์กรุณาตรวจสอบใหม่ด่วยค่ะ!");
            return main(inputBook.render(newbook, customerList));
        } else {
            book = newbook.get();
            bookList = Book.finder.all();
            int numlist = bookList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (bookList.size() == 0) {
                sId = "BK-000001";
            } else {
                lastNum = bookList.get(numlist - 1).getBook_id();
                lastNum = lastNum.substring(3);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "BK-00000" + id;
                } else if (id < 100) {
                    sId = "BK-0000" + id;
                } else if (id < 1000) {
                    sId = "BK-000" + id;
                } else if (id < 10000) {
                    sId = "BK-00" + id;
                } else if (id < 100000) {
                    sId = "BK-0" + id;
                } else {
                    sId = "BK-" + id;
                }
            }
            Date fulldate = new Date();
            SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
            String shortdate = formatdate.format(fulldate);
            fulldate.after(fulldate);
            user = User.finder.byId(session("username"));
            book.setBook_id(sId);
            book.setBook_date(shortdate);
            bookList = Book.bookList();
            int timeChk=0;
            for (int i = 0; i < bookList.size(); i++) {
                timeChk = timeChk+Integer.parseInt(bookList.get(i).getBook_time());
                String Time = bookList.get(i).getBook_time();
                String dataDate = bookList.get(i).getBook_dates();
                String date1 = book.getBook_date();
                LocalDate now = LocalDate.now();
                Date datenow = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if (!date1.equals(datenow)) {
                    flash("nodata", "ไม่สามารถเลือกวันที่ย้อนหลังได้กรุณาเลือกใหม่");
                    return formAddBook();
                }
                if (dataDate.equals(newbook.get().getBook_dates()) && Time.equals(newbook.get().getBook_time())) {
                    flash("error", "วันทีนี้มีการจองเเล้ว");
                    return formAddBook();
                } else {
                    if (timeChk>=1 && newbook.get().getBook_time().equals("3")){
                        flash("error", "วันทีนี้มีการจองเเล้ว");
                        return formAddBook();
                    }
                }
            }
            book.setUser(user);
            models.Book.insert(book);
            return redirect("/listBook");
        }
    }

    public static Result editBook(String id) {
        customerList = Customer.customerList();
        book = models.Book.finder.byId(id);
        String book_id = book.getBook_id();
        String date = book.getBook_date();
        String datas = book.getBook_dates();
        String time = book.getBook_time();
        String amount = book.getBook_Amount();
        String status = book.getBook_Status();
        user = book.getUser();
        customer = book.getCustomer();
        Book editbook = new Book(book_id, date, datas, time, amount, "2", user, customer, "0");
        Book.update(editbook);
        return redirect("/listBook");
    }

    public static Result deleteBook(String id) {
        book = models.Book.finder.byId(id);
        if (book != null) {
            models.Book.delete(book);
        }
        return bookList();
    }

    //จัดเวร
    public static List<ScheduleList> scheduleListList = new ArrayList<ScheduleList>();
    public static List<ScheduleL> scheduleLs = new ArrayList<ScheduleL>();
    public static ScheduleList scheduleList1;
    public static Form<ScheduleList> scheduleListForm = Form.form(ScheduleList.class);


    public static Result dataAll() {
        scheduleListList = ScheduleList.ScheduleList();
        return main(showCusSchedule.render(scheduleListList));
    }

    public static Result searchDate() {
        userList = User.userList();
        scheduleListForm = Form.form(ScheduleList.class);
        DynamicForm dataDate = Form.form().bindFromRequest();
        String m = dataDate.get("mounth");
        String y = dataDate.get("year");
        scheduleListList = ScheduleList.sreachDate(m, y);
        if (scheduleListList != null) {
            return main(showCusSchedule.render(scheduleListList));
        } else {
            flash("error", "ไม่มีข้อมูลของเดือนนี้");
            return main(showCusSchedule.render(scheduleListList));
        }

    }

    public static Result detailSch(String id) {
        scheduleLs = ScheduleL.list();
        scheduleLs = ScheduleL.find.where().ieq("list_sch_id", id).findList();
        return main(showDateSchedule.render(scheduleLs));
    }

    //team
    public static Form<Team> teamForm = Form.form(Team.class);

    public static Result formInsertTeam() {
        String status = "1";
        bookList = Book.finder.where().eq("status", "0").findList();
        userList = User.userList();
        book = null;
        return main(showTeam.render(bookList, userList, book, cusTeamList, status));
    }

    public static Result loadDate() {
        String status = "1";
        DynamicForm dataBooks = Form.form().bindFromRequest();
        book = Book.finder.byId(dataBooks.get("book"));
        bookList = Book.finder.where().eq("status", "0").findList();
        userList = User.userList();
        return main(showTeam.render(bookList, userList, book, cusTeamList, status));
    }

    public static List<CusTeam> cusTeamList = new ArrayList<CusTeam>();
    public static List<Team> teamList = new ArrayList<Team>();
    public static TeamList teamLists;
    public static Team team;
    public static List<TeamList> teamListList = new ArrayList<TeamList>();

    public static Result Add() {
        String status = "";
        bookList = Book.UsersBook();
        userList = User.userList();
        DynamicForm newdata = Form.form().bindFromRequest();
        user = User.finder.byId(newdata.get("username"));
        book = Book.finder.byId(newdata.get("book"));
        String position = newdata.get("position");
        List<CusTeam> CusScheduleListCh = (List<CusTeam>) Cache.get("dataCusTeam");
        teamListList = TeamList.teamListList();
        if (CusScheduleListCh == null) {
            cusTeamList.add(new CusTeam("", position, book, user));
            User admin = User.finder.byId(session("username"));
            Cache.set("dataCusTeam", cusTeamList);
            return main(showTeam.render(bookList, userList, book, cusTeamList, status));
        } else {
            Random random = new Random();
            String sId = Integer.toString(random.nextInt(100000) + 1);
            cusTeamList.add(new CusTeam(sId, position, book, user));
            Cache.set("dataCusTeam", cusTeamList);
            return main(showTeam.render(bookList, userList, book, cusTeamList, status));
        }

    }

    public static Result deleteCusTeam(String id) {
        List<CusTeam> CusTeamList = (List<CusTeam>) Cache.get("dataCusTeam");
        for (int i = 0; i < CusTeamList.size(); i++) {
            if (CusTeamList.get(i).getTeam_Id().equals(id)) {
                CusTeamList.remove(i);
                break;
            }
        }
        return redirect("/formInsertTeam");
    }

    public static Result saveCusTeam() {
        List<CusTeam> CusTeamList = (List<CusTeam>) Cache.get("dataCusTeam");
        Form<models.Team> newTeam = teamForm.bindFromRequest();
        if (CusTeamList == null) {
            flash("nodata", "ไม่สามารถกดบันทึกได้เนื่องจากไม่มีข้อมูล");
            return redirect("/formInsertTeam");
        } else {
            team = newTeam.get();
            teamList = Team.teamList();
            int numlist = teamList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (teamList.size() == 0) {
                sId = "CT-000001";
            } else {
                lastNum = teamList.get(numlist - 1).getId();
                lastNum = lastNum.substring(3);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "CT-00000" + id;
                } else if (id < 100) {
                    sId = "CT-0000" + id;
                } else if (id < 1000) {
                    sId = "CT-000" + id;
                } else if (id < 10000) {
                    sId = "CT-00" + id;
                } else if (id < 100000) {
                    sId = "CT-0" + id;
                } else {
                    sId = "CT-" + id;
                }
            }
            book = CusTeamList.get(0).getBook();
            String date = CusTeamList.get(0).getBook().getBook_dates();
            String time = CusTeamList.get(0).getBook().getBook_time();
            team = new Team(sId, book, date, time, "0");
            Team.insert(team);
            book.setStatus("1");
            Book.update(book);
            for (int i = 0; i < CusTeamList.size(); i++) {
                Random random = new Random();
                String sid = Integer.toString(random.nextInt(100000) + 1);
                String position = CusTeamList.get(i).getTeam_Posit();
                Book dataBook = CusTeamList.get(i).getBook();
                User dataUSer = CusTeamList.get(i).getUser();
                Team dateTeam = Team.finder.byId(sId);
                teamLists = new TeamList(sid, position, dataUSer, dateTeam);
                TeamList.insert(teamLists);
            }
            cusTeamList.clear();
            Cache.remove("dataCusTeam");
        }
        cusTeamList.clear();
        Cache.remove("dataCusTeam");
        return redirect("/dataTeam");
    }

    public static Result dataTeam() {
        teamList = Team.teamList();
        return main(showCusTeam.render(teamList));
    }

    public static Result detailTeam(String id) {
        teamListList = TeamList.teamListList();
        teamListList = TeamList.finder.where().eq("team.id", id).findList();
        return main(showteamList.render(teamListList));
    }


    //payment
    public static List<Payment> paymentList = new ArrayList<Payment>();
    public static Form<Payment> paymentForm = Form.form(Payment.class);
    public static Payment paymant;


    public static Result payList() {
        paymentList = Payment.list();
        return main(showdatapayment.render(paymentList));
    }

    public static Result formInsertpay() {
        teamList = Team.finder.where().eq("status", "0").findList();
        team = null;
        int total = 0;
        return main(showPay.render(teamList, team, total, ""));
    }


    public static Result loadDatepayment() {
        DynamicForm datapayment = Form.form().bindFromRequest();
        String price = Center.centerList().get(0).getCen_Price();
        team = Team.finder.byId(datapayment.get("team"));
        teamList = Team.finder.where().eq("status", "0").findList();
        int total = Integer.parseInt(price) * Integer.parseInt(team.getBook().getBook_Amount());
        return main(showPay.render(teamList, team, total, price));
    }


    public static Result savePayment() {
        DynamicForm dataPayment = Form.form().bindFromRequest();

        paymentList = Payment.list();
        int numlist = paymentList.size();
        int id, idNum;
        String lastNum;
        String sId;
        if (paymentList.size() == 0) {
            sId = "PM-000001";
        } else {
            lastNum = paymentList.get(numlist - 1).getId();
            lastNum = lastNum.substring(3);
            idNum = Integer.parseInt(lastNum);
            id = idNum + 1;
            if (id < 10) {
                sId = "PM-00000" + id;
            } else if (id < 100) {
                sId = "PM-0000" + id;
            } else if (id < 1000) {
                sId = "PM-000" + id;
            } else if (id < 10000) {
                sId = "PM-00" + id;
            } else if (id < 100000) {
                sId = "PM-0" + id;
            } else {
                sId = "PM-" + id;
            }
        }
        String total = dataPayment.get("total");
        Date datas = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatmonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatyear = new SimpleDateFormat("yyyy");
        String shortdate = formatdate.format(datas);
        String shortmonth = formatmonth.format(datas);
        String shortyear = formatyear.format(datas);
        User userPayment = User.finder.byId(session().get("username"));
        Team dataTeam = Team.finder.byId(dataPayment.get("team"));
        Center dataCenter = Center.centerList().get(0);
        paymant = new Payment(sId, total, shortdate,shortmonth,shortyear,userPayment, dataTeam, dataCenter);
        Payment.create(paymant);
        String idTeam = dataTeam.getId();
        Book bookid = dataTeam.getBook();
        String date = dataTeam.getDate();
        String time = dataTeam.getTime();
        String status = "1";
        Team updateTeam = new Team(idTeam, bookid, date, time, status);
        Team.update(updateTeam);

        return redirect("/formInsertpay");

    }


    //keep
    public static List<KeepAdd> keepAddList = new ArrayList<KeepAdd>();
    public static List<Keep> keepList = new ArrayList<Keep>();
    public static Keep keep;
    public static List<KeepList> keepListList = new ArrayList<KeepList>();

    public static Result AddkeepProduct() {
        productList = Product.productList();
        keepList = Keep.list();
        DynamicForm newdata = Form.form().bindFromRequest();
        product = Product.finder.byId(newdata.get("product"));
        String Amount = newdata.get("amount");
        List<KeepAdd> ProKeepList = new ArrayList<KeepAdd>();
        keepListList = KeepList.list();
        boolean found = false;
        if (Cache.get("datakeepAdd") != null) {
            ProKeepList.addAll((List<KeepAdd>) Cache.get("datakeepAdd"));
            for (int i = 0; i < ProKeepList.size(); i++) {
                if (ProKeepList.get(i).getProduct().getPro_Id().equals(product.getPro_Id())) {
                    int amountKeep = Integer.parseInt(ProKeepList.get(i).getAmount()) + Integer.parseInt(Amount);
                    ProKeepList.get(i).setAmount(Integer.toString(amountKeep));
                    found = true;
                    break;
                }
            }
        }

        if (found == false) {
            if (ProKeepList == null) {
                keepAddList.add(new KeepAdd("", Amount, keep, product));
                Cache.set("datakeepAdd", keepAddList);
                return redirect("/formInsertKeep");
            } else {
                Random random = new Random();
                String sId = Integer.toString(random.nextInt(100000) + 1);
                keepAddList.add(new KeepAdd(sId, Amount, keep, product));
                Cache.set("datakeepAdd", keepAddList);

            }
        }
        return redirect("/formInsertKeep");
    }

    public static Form<Keep> keepForm = Form.form(Keep.class);

    public static Result formInsertKeep() {
        productList = Product.productList();
        userList = User.userList();
        Product dataProducr = null;
        return main(inputdatakeep.render(productList, keepAddList, dataProducr));
    }

    public static Result loadkeep() {
        DynamicForm dataProduct = Form.form().bindFromRequest();
        product = Product.finder.byId(dataProduct.get("product"));
        productList = Product.productList();
        userList = User.userList();
        return main(inputdatakeep.render(productList, keepAddList, product));
    }

    public static Result deleteKeepAdd(String id) {
        for (int i = 0; i < keepAddList.size(); i++) {
            if (keepAddList.get(i).getKL_Id().equals(id)) {
                keepAddList.remove(i);
                break;
            }
        }

        return redirect("/formInsertKeep");
    }


    public static Result saveKeep() {
        List<KeepAdd> KeepAddList = (List<KeepAdd>) Cache.get("datakeepAdd");
        if (KeepAddList == null) {
            flash("nodata", "ไม่สามารถกดบันทึกได้เนื่องจากไม่มีข้อมูล");
            return redirect("/formInsertKeep");
        } else {
            keepList = Keep.list();
            int numlist = keepList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (keepList.size() == 0) {
                sId = "PK-000001";
            } else {
                lastNum = keepList.get(numlist - 1).getKeep_Id();
                lastNum = lastNum.substring(3);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "PK-00000" + id;
                } else if (id < 100) {
                    sId = "PK-0000" + id;
                } else if (id < 1000) {
                    sId = "PK-000" + id;
                } else if (id < 10000) {
                    sId = "PK-00" + id;
                } else if (id < 100000) {
                    sId = "PK-0" + id;
                } else {
                    sId = "PK-" + id;
                }
            }
            Date datas = new Date();
            SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
            String shortdate = formatdate.format(datas);
            User UserKeep = User.finder.byId(session().get("username"));
            keep = new Keep(sId, shortdate, UserKeep);
            Keep.create(keep);
            for (int i = 0; i < KeepAddList.size(); i++) {
                Random random = new Random();
                String idList = Integer.toString(random.nextInt(100000) + 1);
                Product dataProduct = Product.finder.byId(KeepAddList.get(i).getProduct().getPro_Id());
                int proAmount;
                String amount1 = KeepAddList.get(i).getAmount();
                if (dataProduct.getPro_Amount() == null) {
                    proAmount = Integer.parseInt(amount1);
                } else {
                    proAmount = Integer.parseInt(dataProduct.getPro_Amount()) + Integer.parseInt(amount1);
                }
                String idproduct = dataProduct.getPro_Id();
                String proName = dataProduct.getPro_Name();
                String unit = dataProduct.getPro_Unit();
                String total = dataProduct.getPro_total();
                String amountOK = Integer.toString(proAmount);
                KeepList newKeepList = new KeepList(idList, amount1, keep, dataProduct);
                Product updateProduct = new Product(idproduct, proName, amountOK, unit, total);
                KeepList.create(newKeepList);
                Product.update(updateProduct);
            }
            keepAddList.clear();
            Cache.remove("datakeepAdd");
        }
        keepAddList.clear();
        Cache.remove("datakeepAdd");
        return redirect("/dataKeepList");
    }


    public static Result dataKeepList() {
        keepList = Keep.list();
        return main(datakeep.render(keepList));
    }

    public static Result detailkeep(String id) {
        keepListList = KeepList.list();
        keepListList = KeepList.find.where().ieq("keep.Keep_Id", id).findList();
        return main(showDataKeepList.render(keepListList));


    }


    //sale
    public static List<SaleAdd> saleAddList = new ArrayList<SaleAdd>();
    public static List<Sale> saleList = new ArrayList<Sale>();
    public static Sale sale;
    public static List<SaleList> saleListList = new ArrayList<SaleList>();

    public static Result formInsertSale() {
        productList = Product.productList();
        userList = User.userList();
        Product dataProduct = null;
        return main(inputdataSale.render(productList, saleAddList, dataProduct));
    }

    public static Result loadDateSale() {
        DynamicForm dataProduct = Form.form().bindFromRequest();
        product = Product.finder.byId(dataProduct.get("product"));
        productList = Product.productList();
        userList = User.userList();
        return main(inputdataSale.render(productList, saleAddList, product));
    }

    public static Result AddSaleProduct() {
        productList = Product.productList();
        saleList = Sale.list();
        DynamicForm newdata = Form.form().bindFromRequest();
        product = Product.finder.byId(newdata.get("product"));
        String Price = newdata.get("Price");
        String Amount = newdata.get("amount");
        String Total = Integer.toString(Integer.parseInt(Price) * Integer.parseInt(Amount));
        if (Integer.parseInt(Amount) > Integer.parseInt(product.getPro_Amount())) {
            flash("noAmount", "จำนวนสินค้าไม่เพียงพอ");
            return formInsertSale();
        }
        List<SaleAdd> ProSaleList = new ArrayList<SaleAdd>();
        saleListList = SaleList.list();
        boolean found = false;
        if (Cache.get("dataSaleAdd") != null) {
            ProSaleList.addAll((List<SaleAdd>) Cache.get("dataSaleAdd"));
            for (int i = 0; i < ProSaleList.size(); i++) {
                if (ProSaleList.get(i).getProduct().getPro_Id().equals(product.getPro_Id())) {
                    int amountsale = Integer.parseInt(ProSaleList.get(i).getAmount()) + Integer.parseInt(Amount);
                    int priceSale = Integer.parseInt(ProSaleList.get(i).getTotal()) + Integer.parseInt(Total);
                    if (Integer.parseInt(ProSaleList.get(i).getAmount() + Integer.parseInt(Amount)) > Integer.parseInt(product.getPro_Amount())) {
                        flash("noAmount", "จำนวนสินค้าไม่เพียงพอ");
                        return formInsertSale();
                    } else {
                        ProSaleList.get(i).setAmount(Integer.toString(amountsale));
                        ProSaleList.get(i).setTotal(Integer.toString(priceSale));
                    }
                    found = true;
                    break;
                }
            }
        }
        if (found == false) {
            if (ProSaleList == null) {
                saleAddList.add(new SaleAdd("", Price, Amount, Total, sale, product));
                Cache.set("dataSaleAdd", saleAddList);
                return redirect("/formSale");
            } else {
                Random random = new Random();
                String sId = Integer.toString(random.nextInt(100000) + 1);
                saleAddList.add(new SaleAdd(sId, Price, Amount ,Total, sale, product));
                Cache.set("dataSaleAdd", saleAddList);
            }

        }
        return redirect("/formSale");
    }


    public static Result deleteSaleAdd(String id) {
        List<SaleAdd> SaleAddList = (List<SaleAdd>) Cache.get("dataSaleAdd");
        for (int i = 0; i < SaleAddList.size(); i++) {
            if (SaleAddList.get(i).getId().equals(id)) {
                SaleAddList.remove(i);
                break;
            }
        }
        return redirect("/formSale");
    }

    public static Result savesale() {
        DynamicForm newdata = Form.form().bindFromRequest();
        List<SaleAdd> SaleAddList = (List<SaleAdd>) Cache.get("dataSaleAdd");
        if (SaleAddList == null) {
            flash("nodata", "ไม่สามารถกดบันทึกได้เนื่องจากไม่มีข้อมูล");
            return redirect("/formSale");
        } else {
            saleList = Sale.list();
            int numlist = saleList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (saleList.size() == 0) {
                sId = "PS-000001";
            } else {
                lastNum = saleList.get(numlist - 1).getSale_Id();
                lastNum = lastNum.substring(3);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "PS-00000" + id;
                } else if (id < 100) {
                    sId = "PS-0000" + id;
                } else if (id < 1000) {
                    sId = "PS-000" + id;
                } else if (id < 10000) {
                    sId = "PS-00" + id;
                } else if (id < 100000) {
                    sId = "PS-0" + id;
                } else {
                    sId = "PS-" + id;
                }
            }
            Date datas = new Date();
            SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatmonth = new SimpleDateFormat("MM");
            SimpleDateFormat formatyear = new SimpleDateFormat("yyyy");
            String date = formatdate.format(datas);
            String month = formatmonth.format(datas);
            String year = formatyear.format(datas);
            int total = 0;
            for (int i = 0; i < SaleAddList.size(); i++) {
                total = total + Integer.parseInt(SaleAddList.get(i).getTotal());
            }
            User UserSale = User.finder.byId(session().get("username"));
            sale = new Sale(sId, date, Integer.toString(total),month,year, UserSale);
            Sale.create(sale);
            for (int i = 0; i < SaleAddList.size(); i++) {
                Random random = new Random();
                String idList = Integer.toString(random.nextInt(100000) + 1);
                Product dataProduct = Product.finder.byId(SaleAddList.get(i).getProduct().getPro_Id());
                int proPrice;
                String price = SaleAddList.get(i).getPrice();
                proPrice = Integer.parseInt(price);
                int proAmount;
                int Total;
                String amount1 = SaleAddList.get(i).getAmount();
                proAmount = Integer.parseInt(dataProduct.getPro_Amount()) - Integer.parseInt(amount1);
                Total = Integer.parseInt(SaleAddList.get(i).getPrice()) * Integer.parseInt(SaleAddList.get(i).getAmount());
                String idproduct = dataProduct.getPro_Id();
                String proName = dataProduct.getPro_Name();
                String unit = dataProduct.getPro_Unit();
                String amountOK = Integer.toString(proAmount);
                String Ptotal = dataProduct.getPro_total();
                SaleList newSaleList = new SaleList(idList, price, amount1, Integer.toString(Total), sale, dataProduct);
                Product updateProduct = new Product(idproduct, proName, amountOK, unit, Ptotal);
                SaleList.create(newSaleList);
                Product.update(updateProduct);
            }
            saleAddList.clear();
            Cache.remove("dataSaleAdd");
        }
        saleAddList.clear();
        Cache.remove("dataSaleAdd");
        return redirect("/dataSaleList");
    }

    public static Result dataSaleList() {
        saleList = Sale.list();
        return main(dataSale.render(saleList));
    }

    public static Result detailsale(String id) {
        saleListList = SaleList.list();
        saleListList = SaleList.find.where().ieq("sale.Sale_Id", id).findList();
        sale = Sale.find.byId(id);
        String total = sale.getTotal();
        return main(ShowdataSaleList.render(saleListList, total));


    }

    //reportproduct
    public static Result reportProduct() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        centerList = Center.centerList();
        productList = Product.productList();
        return ok(reportProduct.render(centerList, productList));
    }


    //reportSaveWork
    public static Result reportSaveWork() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        workList = SaveWork.finder.where().eq("Work_Timein", shortdate).findList();
        centerList = Center.centerList();
        workList = SaveWork.workList();
        return ok(reportSaveWork.render(centerList, workList));
    }

    //reportbook
    public static Result reportbook() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        centerList = Center.centerList();
        bookList = Book.bookList();
        return ok(reportbook.render(centerList, bookList));
    }

    //reportteam
    public static Result reportteam() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        centerList = Center.centerList();
        teamList = Team.finder.all();
        return main(showReportTeam.render(teamList));
    }

    public static Result list(String Id) {
        centerList = Center.centerList();
        team = Team.finder.byId(Id);
        teamListList = TeamList.finder.where().eq("team_id", Id).findList();
        return ok(reportTeam.render(centerList, team, teamListList));
    }

    //reportkeep
    public static Result reportkeep() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        centerList = Center.centerList();
        keepList = Keep.find.all();
        return main(showReportKeep.render(keepList));
    }

    public static Result keepp(String Id) {
        centerList = Center.centerList();
        keep = Keep.find.byId(Id);
        keepListList = KeepList.find.where().eq("keep_keep_id", Id).findList();
        return ok(reportKeep.render(centerList, keep, keepListList));
    }


    //reportsale
    public static Result reportsale() {
        Date fulldate = new Date();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd-MM-yyyy");
        String shortdate = formatdate.format(fulldate);
        centerList = Center.centerList();
        saleList = Sale.find.all();
        return main(showReportSale.render(saleList));
    }

    public static Result resale(String Id) {
        centerList = Center.centerList();
        sale = Sale.find.byId(Id);
        saleListList = SaleList.find.where().eq("sale_sale_id", Id).findList();
        return ok(reportSale.render(centerList, sale, saleListList));
    }

    //dividend

    public static Result diviall() {
        saleList = Sale.list();
        int totalPay=0;
        int totalSale=0;
        int total=0,expenses=0,net=0;

        return main(inputdiv.render(saleList,totalPay,totalSale,total,expenses,net));
    }

    public static Result loadDataSale (){
        DynamicForm data = Form.form().bindFromRequest();
        sale = Sale.find.byId(data.get("month"));
        saleList = Sale.find.where().where().and(Expr.eq("Sale_month",data.get("month")),Expr.eq("Sale_year",data.get("year"))).findList();
        paymentList = Payment.find.where().where().and(Expr.eq("Pay_Month", data.get("month")),Expr.eq("Pay_Year",data.get("year"))).findList();
        int totalPay=0;
        int totalSale=0;
        for(int i=0;i<paymentList.size();i++){
            totalPay = totalPay+Integer.parseInt(paymentList.get(i).getPay_Total());
        }
        for(int i=0;i<saleList.size();i++){
            totalSale = totalSale+Integer.parseInt(saleList.get(i).getTotal());
        }
        int total = totalSale+totalPay;
        int expenses = total*30/100;
        int net = total-expenses;
        return main(inputdiv.render(saleList,totalPay,totalSale,total,expenses,net));

    }
}








