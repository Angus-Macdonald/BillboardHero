
import ControlPanel.Login.GUI.loginScreen;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException {
        //this creates the database if it does not exit otherwise it does nothing

//        CreateDB init = new CreateDB();
//        init.createDB();
        loginScreen.controlPanelLogin();
//
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