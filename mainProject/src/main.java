
import ControlPanel.Login.controlPanelLogin;
import ControlPanel.UserManagement.userManagementGUI;
import Server.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {
        //creates the tables in the database if they do not exist (can be run everytime)
        CreateDB database = new CreateDB();
        database.createDB();

        //creates a user with all permissions
        //Users testuser = new Users();
        //testuser.createUser(4,"root",true,true,true,true,true);

        //test list return
        //ArrayList<Integer> arr = testuser.listUsers();
        //System.out.println(arr);

        //return billboard info test
        //ServerBillboard thisbb = new ServerBillboard();
        //String bbinfos = thisbb.getBBInfo("flupe");
        //System.out.println(bbinfos);

        //login test
        //logIO flu = new logIO();
        //int tokenuser = flu.login(1,"root");
        //System.out.println(tokenuser);

        //controlPanelLogin.controlPanelLogin();
    }
}