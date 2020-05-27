
import ControlPanel.Login.controlPanelLogin;
import ControlPanel.UserManagement.userManagementGUI;
import Server.CreateDB;
import Server.DBConnection;
import Server.Users;
import Server.logIO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {
        //creates the tables in the database
        CreateDB database = new CreateDB();
        database.createDB();
        //creates a user with all permissions
        Users testuser = new Users();
        testuser.createUser(4,"root",true,true,true,true,true);

        controlPanelLogin.controlPanelLogin();





        //logIO flu = new logIO();
        //int tokenuser = flu.login(1,"root");
        //System.out.println(tokenuser);
    }
}