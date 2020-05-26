package ControlPanel;

public class controlPanelUser {
    private static String test;
    private static int userID;
    private static int sessionToken;
    private static boolean createBBPermission;
    private boolean editBBPermission;
    private boolean scheduleBBPermission;
    private boolean editUsers;

    public controlPanelUser(String test,
                            int userID,
                            int sessionToken,
                            boolean createBBPermission,
                            boolean editBBPermission,
                            boolean scheduleBBPermission,
                            boolean editUsers)
    {
        this.test = test;
        this.userID = userID;
        this.sessionToken = sessionToken;
        this.createBBPermission = createBBPermission;
        this.editBBPermission = editBBPermission;
        this.scheduleBBPermission = scheduleBBPermission;
        this.editUsers = editUsers;
    }

    public static void setTest(String value){
        test = value;
    }

    public static String getTest(){
        return test;
    }

}
