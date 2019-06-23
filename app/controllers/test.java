package controllers;
import models.*;
import org.joda.time.DateTime;
import play.api.templates.Html;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 25/1/2562.
 */
public class test extends Controller {
public static List<Center> centerList = new ArrayList<Center>();
    public static Result main(Html content) {
        return ok(main.render(content));
    }

    public static List<ScheduleList> scheduleListList = new ArrayList<ScheduleList>();
    public static List<ScheduleL> scheduleLList = new ArrayList<ScheduleL>();
    public static ScheduleList scheduleList1;
    public static ScheduleL scheduleL;
    public static Form<ScheduleList> scheduleListForm = Form.form(ScheduleList.class);
    public static List<User> userList = new ArrayList<User>();
    public static CusSchedule cusSche;

    public static User datauser;

    public static String fdate = "";
    public static String ldate = "";

    public static Form<CusSchedule> cusScheduleForm = Form.form(CusSchedule.class);

    public static Result formSchedule (){

        cusScheduleForm = Form.form(CusSchedule.class);
        userList = User.userList();
        List<CusSchedule> CusScheduleList  = (List<CusSchedule>) Cache.get("dataSchedule");
        return main(dataCusSche.render(cusScheduleForm, userList, CusScheduleList,fdate,ldate));
    }
    public static List<CusSchedule> listtest = new ArrayList<CusSchedule>();

    public static Result AddSchedule() {
        Form<CusSchedule> newdata  = cusScheduleForm.bindFromRequest();
        datauser = User.finder.byId(newdata.get().getUser().getUsername());
        cusSche = newdata.get();
        fdate = cusSche.getSch_Date();
        ldate = cusSche.getSch_Dates();
        cusSche.setUser(datauser);
        List<CusSchedule> CusScheduleList  = (List<CusSchedule>) Cache.get("dataSchedule");
        scheduleListList = ScheduleList.ScheduleList();
        if (CusScheduleList == null){
            listtest.add(new CusSchedule("SCH-000001", cusSche.getSch_Date(), cusSche.getSch_Dates(), cusSche.getSch_Timein(), cusSche.getSch_Timeout(), datauser));
            User admin = User.finder.byId(session("username"));
            Cache.set("dataSchedule", listtest);
            return  redirect("/formSchedule");
        }else{
            Random random = new Random();
            String Sch_id = Integer.toString(random.nextInt(100000) + 1);
            listtest.add(new CusSchedule(Sch_id, cusSche.getSch_Date(), cusSche.getSch_Dates(), cusSche.getSch_Timein(), cusSche.getSch_Timeout(), datauser));
            User admin = User.finder.byId(session("username"));
            Cache.set("dataSchedule",listtest);
            return main(dataCusSche.render(cusScheduleForm, userList, CusScheduleList, fdate, ldate));
        }

    }

    public static Result saveSchedule (){
        fdate ="";
        ldate = "";
        List<CusSchedule> CusScheduleList  = (List<CusSchedule>) Cache.get("dataSchedule");
        if (CusScheduleList == null){
            flash("nodata","ไม่สามารถกดบันทึกได้เนื่องจากไม่มีข้อมูล");
            return redirect("/formSchedule");
        }else{
            scheduleListList = ScheduleList.ScheduleList();
            int numlist = scheduleListList.size();
            int id, idNum;
            String lastNum;
            String sId;
            if (scheduleListList.size() == 0) {
                sId = "SCH-000001";
            } else {
                lastNum = scheduleListList.get(numlist - 1).getListSch_Id();
                lastNum = lastNum.substring(4);
                idNum = Integer.parseInt(lastNum);
                id = idNum + 1;
                if (id < 10) {
                    sId = "SCH-00000" + id;
                } else if (id < 100) {
                    sId = "SCH-0000" + id;
                } else if (id < 1000) {
                    sId = "SCH-000" + id;
                } else if (id < 10000) {
                    sId = "SCH-00" + id;
                } else if (id < 100000) {
                    sId = "SCH-0" + id;
                } else {
                    sId = "SCH-" + id;
                }
            }
            String date = CusScheduleList.get(0).getSch_Date();
            String dates = CusScheduleList.get(0).getSch_Dates();
            String in = CusScheduleList.get(0).getSch_Timein();
            String out = CusScheduleList.get(0).getSch_Timeout();
            User dataUSer = CusScheduleList.get(0).getUser();
            scheduleList1 = new ScheduleList(sId,date,dates,in,out,dataUSer);
            ScheduleList.insert(scheduleList1);
            for (int i = 0; i < CusScheduleList.size(); i++) {
                Random random = new Random();
                String Sch_id = Integer.toString(random.nextInt(100000) + 1);
                ScheduleList scheduleList = ScheduleList.finder.byId(sId);
                User username = CusScheduleList.get(i).getUser();
                scheduleL = new ScheduleL(Sch_id,scheduleList,username);
                ScheduleL.create(scheduleL);
            }
            Cache.remove("dataSchedule");
        }
        return redirect("/dataAll");
    }

    public static Result deleteCusSchedule (String id){
        List<CusSchedule> CusScheduleList  = (List<CusSchedule>) Cache.get("dataSchedule");
        for (int i = 0 ; i<CusScheduleList.size();i++){
            if (CusScheduleList.get(i).getListSch_Id().equals(id)){
                listtest.remove(i);
                break;
            }
        }
        return  redirect("/formSchedule");
    }

    //reportSchedule
    public static Result dataScheduleList() {
        scheduleListList = ScheduleList.ScheduleList();
        return main(showreportschedule.render(scheduleListList));
    }

    public static ScheduleList schedulelist;
    public static Result list(String Id) {
        centerList = Center.centerList();
        schedulelist = ScheduleList.finder.byId(Id);
        scheduleLList = ScheduleL.find.where().eq("list_sch_id", Id).findList();
        return ok(reportSchedule.render(centerList, schedulelist,scheduleLList ));
    }






}
