package Server;

import java.sql.*;
import java.lang.Math;
class loginException extends Exception{}
public class logIO {
    public static int login(int userID, String password) throws SQLException {
        Connection connection = DBConnection.getInstance();
        //create a token
        int token = 0;

        //check to see if the login and password are contained within the
        int found = 0;
        PreparedStatement statement = connection.prepareStatement("SElECT UserID FROM users WHERE UserID=? AND Password=?");
        statement.clearParameters();
        statement.setInt(1, userID);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String someName = rs.getString("UserID");
            found++;
        }
        statement.close();
        //check if it has been found and throws and exception if it hasnt
        try{
            if(found == 0) throw new loginException();
            double tokenf = Math.floor(Math.random() * 2147483646);
            token = (int)tokenf;
        }
        catch(loginException notFound){
            System.out.println("Error: UserID or password was incorrect");
        }
        //insert data into session table within the database
        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO session Values(?,?)");
        statement2.setInt(1, token);
        statement2.setInt(2, userID);
        statement2.executeUpdate();
        statement2.close();
        connection.close();
        return token;
    }
    public void logout(int userID) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statementLogout = connection.prepareStatement(" DELETE FROM Session WHERE UserID=?");
        statementLogout.clearParameters();
        statementLogout.setInt(1,userID);
        statementLogout.executeUpdate();
        statementLogout.close();
        connection.close();
    }
}
