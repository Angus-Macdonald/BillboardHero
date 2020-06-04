package ControlPanel.Utility;

import javax.swing.*;

public class Logout extends User {
    public Logout(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void logout(JFrame frame){
        setUserID(0);
        setSessionToken(0);
        setCreateBBPermission(false);
        setEditBBPermission(false);
        setScheduleBBPermission(false);
        setEditUsersPermission(false);
        frame.dispose();
    }
}
