
import ControlPanel.Login.controlPanelLogin;
import ControlPanel.UserManagement.userManagementGUI;
import Server.*;
import ControlPanel.Utility.billboard;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, ParseException {
        //this creates the database if it does not exit otherwise it does nothing
        CreateDB init = new CreateDB();
        init.createDB();

        ServerBillboard star = new ServerBillboard();
        Object newa = "this";
        star.createBB("star",22,newa);

        long timeS = new Date().getTime();




        Schedule first = new Schedule();
        first.scheduleBB("dis",22,timeS,3,60,0,3,0);
        String bupe = "asdassdsad";


//        billboard billboard1 = new billboard("billboard1");
//        billboard1.addMsg("testing first msg");
//        billboard1.addInfo("some random info");
//        billboard1.addInfo("a second msg");
//
//        billboard billboard2 = new billboard("billboard2");
//        billboard2.addMsg("billboard2 msg");
//        billboard2.addInfo("info for bill2");
    }
}