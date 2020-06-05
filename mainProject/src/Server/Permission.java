package Server;

import java.sql.*;
import java.util.ArrayList;

public class Permission {
    /**
     *
     * @param userID the Id which is used to check the Admin permission associated with it
     * @return a boolean of the admin permission
     * @throws SQLException
     */
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
        return perm;
    }

    /**
     *
     * @param userID the Id which is used to check the CreateBB permission associated with it
     * @return a boolean of the CreateBB permission
     * @throws SQLException
     */
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
        return perm;
    }

    /**
     *
     * @param userID the Id which is used to check the EditAllBB permission associated with it
     * @return a boolean of the EditAllBB permission
     * @throws SQLException
     */
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
        return perm;
    }

    /**
     *
     * @param userID the Id which is used to check the ScheduleBB permission associated with it
     * @return the Id which is used to check the ScheduleBB permission associated with it
     * @throws SQLException
     */
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
        return perm;
    }

    /**
     *
     * @param userID the Id which is used to check the EditUsers permission associated with it
     * @return the Id which is used to check the EditUsers permission associated with it
     * @throws SQLException
     */
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

    /**
     *
     * @param userID the Id which is used to all permissions associated with it
     * @return a list of all of userID's permission in a  boolean arraylist
     * @throws SQLException
     */
    public ArrayList<Boolean> ChkPerms(int userID) throws SQLException {
        ArrayList<Boolean> permList = new ArrayList<Boolean>();
        permList.add(0,chkAdmin(userID));
        permList.add(1,chkCreateBB(userID));
        permList.add(2,chkEditAllBB(userID));
        permList.add(3,chkScheduleBB(userID));
        permList.add(4,chkEditUsers(userID));
        return permList;
    }

    /**
     *
     * @param userID the users ID send to mariaDB to set privileges associated with it
     * @param setAdmin a boolean for admin to be set where the user ID supplied is within the database
     * @param setCreateBB a boolean for CreateBB to be set where the user ID supplied is within the database
     * @param setEditAllBB a boolean for EditAllBB to be set where the user ID supplied is within the database
     * @param setScheduleBB a boolean for ScheduleBB to be set where the user ID supplied is within the database
     * @param editUsers a boolean for editUsers to be set where the user ID supplied is within the database
     * @throws SQLException
     */
    public void setPermission(int userID,
                              boolean setAdmin,
                              boolean setCreateBB,
                              boolean setEditAllBB,
                              boolean setScheduleBB,
                              boolean editUsers) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("UPDATE users " +
                "SET Admin=?, " +
                "CreateBB=?, " +
                "EditAllBB=?, " +
                "ScheduleBB=?, " +
                "EditUsers=? WHERE UserID=?");
        statement.clearParameters();

        statement.setBoolean(1,setAdmin);
        statement.setBoolean(2,setCreateBB);
        statement.setBoolean(3,setEditAllBB);
        statement.setBoolean(4,setScheduleBB);
        statement.setBoolean(5,editUsers);
        statement.setInt(6,userID);
        statement.executeUpdate();
        statement.close();


    }

}
