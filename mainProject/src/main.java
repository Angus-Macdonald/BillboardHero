
import ControlPanel.Login.controlPanelLogin;
import ControlPanel.UserManagement.userManagementGUI;
import Server.DBConnection;
import Server.logIO;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        controlPanelLogin.controlPanelLogin();






        logIO flu = new logIO();
        int tokenuser = flu.login(1,"root");
        System.out.println(tokenuser);
    }
}