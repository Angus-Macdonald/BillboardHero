
import ControlPanel.Login.controlPanelLogin;
import ControlPanel.UserManagement.userManagementGUI;
import Server.*;
import ControlPanel.Utility.billboard;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {
        billboard billboard1 = new billboard("billboard1");
        billboard1.addMsg("testing first msg");
        billboard1.addInfo("some random info");
        billboard1.addInfo("a second msg");

        billboard billboard2 = new billboard("billboard2");
        billboard2.addMsg("billboard2 msg");
        billboard2.addInfo("info for bill2");
    }
}