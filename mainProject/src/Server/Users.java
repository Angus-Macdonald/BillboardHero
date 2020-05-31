package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Users {
    public void createUser(int userID,
                           String password,
                           boolean admin,
                           boolean createBB,
                           boolean editAllBB,
                           boolean scheduleBB,
                           boolean editUsers) throws SQLException {
        Connection createUserStatementConnection = DBConnection.getInstance();
        PreparedStatement Statement = createUserStatementConnection.prepareStatement(" INSERT INTO mpdb.Users (" +
                "UserID," +
                "Password," +
                "Admin," +
                "CreateBB," +
                "EditAllBB," +
                "ScheduleBB," +
                "EditUsers) " +
                "Values(?,?,?,?,?,?,?)");
        Statement.clearParameters();
        Statement.setInt(1,userID);
        Statement.setString(2,password);
        Statement.setBoolean(3,admin);
        Statement.setBoolean(4,createBB);
        Statement.setBoolean(5,editAllBB);
        Statement.setBoolean(6,scheduleBB);
        Statement.setBoolean(7,editUsers);
        Statement.executeUpdate();

        Statement.close();
        createUserStatementConnection.close();
    };
    public void deleteUser(int userID) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(" DELETE FROM Users WHERE UserID=?");
        statement.clearParameters();
        statement.setInt(1,userID);
        statement.executeUpdate();
        statement.close();
        //connection.close();
    };
    public ArrayList<Integer> listUsers() throws SQLException {
        ArrayList<Integer> userslist = new ArrayList<Integer>();
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT UserID FROM users");
        statement.clearParameters();
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            int listUser = rs.getInt("UserID");
            userslist.add(listUser);
        }
        statement.close();
        return(userslist);
    };
    public void setPassword(String password, int userID ) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("update users set password=? where UserID=?");
        statement.clearParameters();
        statement.setString(1,password);
        statement.setInt(2,userID);
        statement.executeUpdate();
        statement.close();
    };
}
