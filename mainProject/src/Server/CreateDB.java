package Server;

import java.io.IOException;
import java.sql.*;

public class CreateDB {
    public static void createDB() throws SQLException, IOException {
        Connection createDBConnection = DBConnection.getInstance();
        Statement Statement = createDBConnection.createStatement();

        //statement.execute("DROP TABLES Users");
        //statement.execute("DROP TABLES Billboards");
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
                "Date varchar(255) NOT NULL," +
                "STime int NOT NULL," +
                "ETime int NOT NULL," +
                "PRIMARY KEY (BBName))");

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
}
