package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.FrameAndPanelUtility;
import ControlPanel.Utility.Menubar;
import Server.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static ControlPanel.Home.GUI.UserManagement.ChangePassword.changePasswordWindow;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.FrameDispose.disposeFrames;

public class EditUser extends UserManagement {


    public EditUser(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        editUserWindow(getSelectedUser());
    }

    /**
     * Function creates a GUI window that allows a user to edit a users details. If the user they are editing is themselves, they do not have the ability to remove their own edit user permission.
     * @param user
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void editUserWindow(int user) throws IOException, ClassNotFoundException {
        ArrayList<Boolean> userPermissions = Client.ChkPermsS(user);
        boolean[] localPermissions = new boolean[4];
        localPermissions[0] = userPermissions.get(1);
        localPermissions[1] = userPermissions.get(2);
        localPermissions[2] = userPermissions.get(3);
        localPermissions[3] = userPermissions.get(4);


        JFrame frame = new JFrame("Edit User");
        FrameAndPanelUtility.frameManage(frame, 6, 1);
        Menubar.menubar(frame);
        JPanel[] panel = new JPanel[6];
        panelInitialise(panel);

        JLabel header = new JLabel("Edit User");
        panel[0].add(header);

        JLabel userID = new JLabel("User ID:  " + user);
        panel[1].add(userID);
        frame.add(panel[1]);

        JButton changePW = new JButton("Change Password");
        panel[2].add(changePW);
        changePW.addActionListener(e -> changePasswordWindow(user));

        JLabel permissions = new JLabel("User Permissions");
        panel[3].add(permissions);
        panel[3].setBorder(new EmptyBorder(50,0,0,0));

        panel[4] = new JPanel(new GridLayout(2, 2));
        JCheckBox createBB = new JCheckBox("Create Billboard ");
        createBB.setSelected(userPermissions.get(1));
        panel[4].add(createBB);

        JCheckBox editBB = new JCheckBox("Edit Billboard");
        editBB.setSelected(userPermissions.get(2));
        panel[4].add(editBB);

        JCheckBox scheduleBB = new JCheckBox("Schedule Billboard");
        scheduleBB.setSelected(userPermissions.get(3));
        panel[4].add(scheduleBB);
        JCheckBox editUsers = new JCheckBox("Edit User");
        if (user != getUserID()) {
            editUsers.setSelected(userPermissions.get(4));
            panel[4].add(editUsers);
        }
        panel[4].setBorder(new EmptyBorder(0, 50,50,0));

        createBB.addActionListener(e -> {
            if(!localPermissions[0]){
                localPermissions[0] = true;
            }
            else if (localPermissions[0]){
                localPermissions[0] = false;
            }
        });

        editBB.addActionListener(e -> {
            if(!localPermissions[1]){
                localPermissions[1] = true;
            }
            else if (localPermissions[1]){
                localPermissions[1] = false;
            }
        });

        scheduleBB.addActionListener(e -> {
            if(!localPermissions[2]){
               localPermissions[2] = true;
            }
            else if (localPermissions[2]){
                localPermissions[2] = false;
            }
        });

        editUsers.addActionListener(e -> {
            if (!localPermissions[3]) {
                localPermissions[3] = true;
            }
            else if (localPermissions[3]) {
                localPermissions[3] = false;
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e ->{
            try {

                Client.setPermissionS(user, false, localPermissions[0], localPermissions[1], localPermissions[2], localPermissions[3]);
                disposeFrames();
                userManagementGUI();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        panel[5].add(submitButton);

        for(JPanel pan: panel){
            frame.getContentPane().add(pan);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
