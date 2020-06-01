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
        //createDBConnection.close();

    }
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, ParseException {
        //this creates the database if it does not exit otherwise it does nothing
        CreateDB init = new CreateDB();
        init.createDB();

        Users user = new Users();
        logIO log = new logIO();
        Schedule first = new Schedule();
        session session = new session();
        Permission permission = new Permission();

        ////create user//////
        //user.createUser(9,"passpass",true,true,true,true,true);

        ////login/////
        log.login(1,"pass");

        /////change permission//////
//        permission.setPermission(1,false,false,false,false,false);

        //////check if in session
//        boolean token = session.sessionCheck(1970027197,1);
//        System.out.println(token);

        ///////get current time and date///////
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date now = calendar.getTime();
//        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
//        //System.out.println("Current time: "+currentTimestamp);


        //////timestamp/////////
        //java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("1995-12-30 2:30:0.0");

        ////

        /////create bb////////
//        ServerBillboard bb = new ServerBillboard();
//        Object ojBB = "teef teef";
//        bb.createBB("teef",22,ojBB);


        //////schedule BB//////
        //first.scheduleBB("mincheck",22,timestamp,60,0,0,25);

        /////get current bill board and print//////
        //Object ans =first.currentBB(timestamp); System.out.println(ans);

        //// check what billboards have passed current time ////
        //first.editRepeats(timestamp);





    }
}
