package ControlPanel;

public class
controlPanelUser {
    String test;
    int userID;
    int sessionToken;
    boolean createBBPermission;
    boolean editBBPermission;
    boolean scheduleBBPermission;
    boolean editUsers;

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

    public void setTest(){
        test = "Test success";
    }

    public String getTest(){
        return test;
    }

}
