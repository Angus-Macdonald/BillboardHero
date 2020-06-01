package Server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateDB {
    public static void createDB() throws SQLException, IOException {
        Connection createDBConnection = DBConnection.getInstance();
        Statement Statement = createDBConnection.createStatement();

        Statement.execute("CREATE TABLE IF NOT EXISTS Users (UserID int," +
                "Password varchar(255) NOT NULL," +
                "Admin bool NOT NULL," +
                "CreateBB bool NOT NULL," +
                "EditAllBB bool NOT NULL, " +
                "ScheduleBB bool NOT NULL, " +
                "EditUsers bool NOT NULL," +
                "PRIMARY KEY (UserID))");

        Statement.execute("CREATE TABLE IF NOT EXISTS Billboards (BBName varchar(255)," +
                "Creator int NOT NULL," +
                "Billboard longblob NOT NULL," +
                "PRIMARY KEY (BBName))");

        Statement.execute("CREATE TABLE IF NOT EXISTS Schedule (BBName varchar(255)," +
                "Creator int NOT NULL," +
                "STime TIMESTAMP NOT NULL," +
                "ETime TIMESTAMP NOT NULL," +
                "duration int NOT NULL," +
                "repeatDay int," +
                "repeatHour int," +
                "repeatMin int," +
                "createNum BIGINT NOT NULL AUTO_INCREMENT," +
                "PRIMARY KEY (createNum))");

        Statement.execute("CREATE TABLE IF NOT EXISTS Session (Token int," +
                "UserID int NOT NULL," +
                "PRIMARY KEY (Token))");

        Statement.close();

    }
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, ParseException, ClassNotFoundException {
        //this creates the database if it does not exit otherwise it does nothing
        CreateDB init = new CreateDB();
        init.createDB();

        Users user = new Users();
        logIO log = new logIO();
        Schedule schedule = new Schedule();
        session session = new session();
        Permission permission = new Permission();
        ServerBillboard billboard = new ServerBillboard();

        /////create user//////
        //user.createUser(10,"passpass",true,true,true,true,true);

        /////delete user//////
        //user.deleteUser(10);

        /////get list of users//////
        //ArrayList al = user.listUsers();
        //System.out.println(al);

        /////set password////
        //user.setPassword("newsetpassword",9);

        ////login/////
        //log.login(3,"passpass");

        ////logout//////
        //log.logout(3);

        ////check permissions/////
        //ArrayList all = permission.ChkPerms(1);
        //System.out.println(all);

        /////change permission//////
        //permission.setPermission(5,false,false,false,false,false);

        //////schedule billboard//////
        //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2020-12-12 3:00:0.0");
        //schedule.scheduleBB("2020",100,timestamp,60,0,0,25);

        ////view schedule//////
        //ArrayList sch = schedule.viewSchedule();
        //System.out.println(sch);

        ////remove from schedule/////
        //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2020-12-12 3:00:0.0");
        //schedule.rmvFromSch("2020",timestamp);

        /////current billboard///
        //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("1996-1-03 3:00:0.0");
        //Object ans =schedule.currentBB(timestamp); System.out.println(ans);

        //////check if in session///
        //boolean token = session.sessionCheck(172286850,1);
        //System.out.println(token);

        /////create Billboard////////
        //Object ojBB = "this is the stuff for 44";
        //billboard.createBB("the44",44,ojBB);

        ////delete Billboard /////////
        //billboard.deleteBB("the44");

        ////get billboard information/////
        //String bbinfo = billboard.getBBInfo("test");
        //System.out.println(bbinfo);

        ////list billboards//////
        //ArrayList lBB = billboard.ListBillboards();
        //System.out.println(lBB);

        ///////get current time and date///////
        //Calendar calendar = Calendar.getInstance();
        //java.util.Date now = calendar.getTime();
        //java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        //System.out.println("Current time: "+currentTimestamp);
    }
}
