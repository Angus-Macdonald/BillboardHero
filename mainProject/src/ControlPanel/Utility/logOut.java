package ControlPanel.Utility;

import javax.swing.*;

public class logOut extends controlPanelUser{
    public logOut(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void clearControlPanelUser(){
        setUserID(0);
        setSessionToken(0);
        setCreateBBPermission(false);
        setEditBBPermission(false);
        setScheduleBBPermission(false);
        setEditUsersPermission(false);
    }


}

