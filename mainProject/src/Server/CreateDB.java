package Server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

public class CreateDB {
    public static void createDB() throws SQLException, IOException {
        Connection createDBConnection = DBConnection.getInstance();
        Statement Statement = createDBConnection.createStatement();

        //Statement.execute("DROP TABLES Users");
        //Statement.execute("DROP TABLES Billboards");
        //Statement.execute("DROP TABLES Schedule");

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

        //Users dlu = new Users();
        //dlu.createUser(5,"falseboy",false,false,false,false,false);
        //Users dlu = new Users();
        //dlu.deleteUser(0);

        //logIO flu = new logIO();
        //int tokenuser = flu.login(1,"root");
        //System.out.println(tokenuser);

        //logIO flus = new logIO();
        //flus.logout(1);

//        boolean quan;
//        session flu = new session();
//        quan = flu.sessionCheck(1125581510,1);
//        System.out.println(quan);

//        boolean quan;
//        CheckPermission flu = new CheckPermission();
//        quan = flu.chkEditUsers(5);
//        System.out.println(quan);

//        Object myobj= "hello world";
//        ServerBillboard flu = new ServerBillboard();
//        flu.createBB("mm",2,myobj);


        //Users flu = new Users();
        //flu.setPassword("newpassed",3);



        Statement.close();
        //createDBConnection.close();

    }
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, ParseException {
        //this creates the database if it does not exit otherwise it does nothing
        CreateDB init = new CreateDB();
        init.createDB();


        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        System.out.println("Current time: "+currentTimestamp);


        //timestamp
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("1995-12-30 3:39:9.0");

        Schedule first = new Schedule();
        //first.viewSchedule();
        //first.rmvFromSch("this4",timestamp);

        //create bb
        ServerBillboard bb = new ServerBillboard();
        Object ojBB = "teef teef";
        bb.createBB("teef",22,ojBB);


        //////schedule BB//////
        //first.scheduleBB("teef",22,timestamp,30,0,1,0);

        /////get current bill board and print//////
        Object ans =first.currentBB(timestamp); System.out.println(ans);





    }
}
