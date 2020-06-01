package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SessionException extends Exception{}
public class session {
    public boolean sessionCheck(int token,int userID) throws SQLException {
        int foundSession=0;
        boolean sessionExists = false;
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT Token FROM session WHERE token=? AND UserID=?");
        statement.clearParameters();
        statement.setInt(1, token);
        statement.setInt(2, userID);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String someName = rs.getString("Token");
            foundSession++;
        }
        statement.close();
        //check if it has been found and throws and exception if it hasnt
        try{
            if(foundSession == 0) throw new SessionException();
            sessionExists = true;
        }
        catch(SessionException notFound){
            System.out.println("Error: UserID or password was incorrect");
        };
        //connection.close();
        return sessionExists;
    };

}
