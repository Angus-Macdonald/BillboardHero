package Server;

import java.sql.*;
import java.util.ArrayList;

public class Permission {
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
        //connection.close();
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
        //connection.close();
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
        //connection.close();
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
        //connection.close();
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
        //connection.close();
        return perm;
    }
    public ArrayList<Boolean> ChkPerms(int userID) throws SQLException {
        ArrayList<Boolean> permList = new ArrayList<Boolean>();
        permList.add(0,chkAdmin(userID));
        permList.add(1,chkCreateBB(userID));
        permList.add(2,chkEditAllBB(userID));
        permList.add(3,chkScheduleBB(userID));
        permList.add(4,chkEditUsers(userID));
        return permList;
    }
    public void setPermission(int userID,
                              boolean setAdmin,
                              boolean setCreateBB,
                              boolean setEditAllBB,
                              boolean setScheduleBB,
                              boolean editUsers) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statementUpdateBB = connection.prepareStatement("UPDATE users " +
                "SET Admin=?, " +
                "CreateBB=?, " +
                "EditAllBB=?, " +
                "ScheduleBB=?, " +
                "EditUsers=? WHERE UserID=?");
        statementUpdateBB.clearParameters();

        statementUpdateBB.setBoolean(1,setAdmin);
        statementUpdateBB.setBoolean(2,setCreateBB);
        statementUpdateBB.setBoolean(3,setEditAllBB);
        statementUpdateBB.setBoolean(4,setScheduleBB);
        statementUpdateBB.setBoolean(5,editUsers);
        statementUpdateBB.setInt(6,userID);
        statementUpdateBB.executeUpdate();


    }

}
