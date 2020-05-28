package Server;

import java.sql.*;
public class CheckPermission {
    boolean chkAdmin(int userID)throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT Admin FROM users WHERE UserID=? ");
        statement.clearParameters();
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        boolean perm = false;
        while(rs.next()) {
            perm = rs.getBoolean("Admin");
        }
        statement.close();
        connection.close();
        return perm;
    }
    boolean chkCreateBB(int userID)throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT CreateBB FROM users WHERE UserID=? ");
        statement.clearParameters();
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        boolean perm = false;
        while(rs.next()) {
            perm = rs.getBoolean("CreateBB");
        }
        statement.close();
        connection.close();
        return perm;
    }
    boolean chkEditAllBB(int userID)throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT EditAllBB FROM users WHERE UserID=? ");
        statement.clearParameters();
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        boolean perm = false;
        while(rs.next()) {
            perm = rs.getBoolean("EditAllBB");
        }
        statement.close();
        connection.close();
        return perm;
    }
    boolean chkScheduleBB(int userID)throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT ScheduleBB FROM users WHERE UserID=? ");
        statement.clearParameters();
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        boolean perm = false;
        while(rs.next()) {
            perm = rs.getBoolean("ScheduleBB");
        }
        statement.close();
        connection.close();
        return perm;
    }
    boolean chkEditUsers(int userID)throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT EditUsers FROM users WHERE UserID=? ");
        statement.clearParameters();
        statement.setInt(1, userID);
        ResultSet rs = statement.executeQuery();
        boolean perm = false;
        while(rs.next()) {
            perm = rs.getBoolean("EditUsers");
        }
        statement.close();
        connection.close();
        return perm;
    }
}