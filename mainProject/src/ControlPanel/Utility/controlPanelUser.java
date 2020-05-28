package ControlPanel.Utility;

public class controlPanelUser {

    private static int userID;
    private static int sessionToken;
    private static boolean createBBPermission;
    private static boolean editBBPermission;
    private static boolean scheduleBBPermission;
    private static boolean editUsersPermission;

    public controlPanelUser(
                            int userID,
                            int sessionToken,
                            boolean createBBPermission,
                            boolean editBBPermission,
                            boolean scheduleBBPermission,
                            boolean editUsersPermission
                            )
    {
        this.userID = userID;
        this.sessionToken = sessionToken;
        this.createBBPermission = createBBPermission;
        this.editBBPermission = editBBPermission;
        this.scheduleBBPermission = scheduleBBPermission;
        this.editUsersPermission = editUsersPermission;
    }

    public static void setUserID(int value){userID = value;}
    public static int getUserID(){return userID; }

    public static void setSessionToken(int value){sessionToken = value;}
    public static int getSessionToken(){return sessionToken;}

    public static void setCreateBBPermission(boolean value){createBBPermission = value;}
    public static boolean getCreateBBPermission(){return createBBPermission;}

    public static void setEditBBPermission(boolean value){editBBPermission = value;}
    public static boolean getEditBBPermission(){return editBBPermission;}

    public static void setScheduleBBPermission(boolean value){scheduleBBPermission = value;}
    public static boolean getScheduleBBPermission(){return scheduleBBPermission;}

    public static void setEditUsersPermission(boolean value){editUsersPermission = value;}
    public static boolean getEditUsersPermission(){return editUsersPermission;}


}