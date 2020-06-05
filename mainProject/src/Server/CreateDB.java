package Server;

import ControlPanel.Utility.billboard;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateDB {
    public static void createDB() throws SQLException, IOException, ParseException {
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


        CreateDB rootUser = new CreateDB();
        rootUser.createRoot(1);
        Statement.close();

    }

    /**
     *
     * @param userID the ID of the root user which is created upon the database initialisation
     * @throws SQLException
     */
    public void createRoot(int userID) throws SQLException {
        Connection connection = DBConnection.getInstance();
        //check if it exists
        int found = 0;
        PreparedStatement findstatement = connection.prepareStatement("SElECT UserID FROM users WHERE UserID=?");
        findstatement.clearParameters();
        findstatement.setInt(1, userID);
        ResultSet rs = findstatement.executeQuery();
        while(rs.next()) {
            String someName = rs.getString("UserID");
            found++;
        }
        if(found==1){ }
        else{
            Users user = new Users();
            user.createUser(1,"root",true,true,false,true,true);
        }
        findstatement.close();

    }
}
