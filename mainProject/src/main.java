import Server.Server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import java.text.ParseException;

class main{
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, IOException, ParseException, ClassNotFoundException {
        ControlPanel.Login.GUI.loginScreen.controlPanelLogin();
        Server.server();
    }
}