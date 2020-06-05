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

public class EditUser extends UserManagement {


    public EditUser(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        editUserWindow(getSelectedUser());
    }

    public static void editUserWindow(Integer user) throws IOException, ClassNotFoundException {
        ArrayList<Boolean> userPermissions = Client.ChkPermsS(user);

        JFrame frame = new JFrame("Edit User");
        FrameAndPanelUtility.frameManage(frame, 6, 1);
        Menubar.menubar(frame);
        JPanel[] panel = new JPanel[6];
        panelInitialise(panel);

        JLabel header = new JLabel("Edit User");
        panel[0].add(header);

        JLabel userID = new JLabel("User ID:  " + user.toString());
        panel[1].add(userID);
        frame.add(panel[1]);

        JButton changePW = new JButton("Change Password");
        panel[2].add(changePW);
        changePW.addActionListener(e -> changePasswordWindow(user.toString()));

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
        if (user.intValue() != getUserID()) {
            editUsers.setSelected(userPermissions.get(4));
            panel[4].add(editUsers);
        }
        panel[4].setBorder(new EmptyBorder(0, 50,50,0));

        createBB.addActionListener(e -> {
            if(!userPermissions.get(1)){
                userPermissions.set(1, true);
            }
            else if (userPermissions.get(1)){
                userPermissions.set(1, false);
            }
        });

        editBB.addActionListener(e -> {
            if(!userPermissions.get(2)){
                userPermissions.set(2, true);
            }
            else if (userPermissions.get(2)){
                userPermissions.set(2, false);
            }
        });

        scheduleBB.addActionListener(e -> {
            if(!userPermissions.get(3)){
                userPermissions.set(3, true);
            }
            else if (userPermissions.get(3)){
                userPermissions.set(3, false);
            }
        });

        editUsers.addActionListener(e -> {
            if (!userPermissions.get(4)) {
                userPermissions.set(4, true);
            }
            else if (userPermissions.get(4)) {
                userPermissions.set(4, false);
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e ->{
            try {
                Client.setPermissionS(user, false, userPermissions.get(1), userPermissions.get(2), userPermissions.get(3), userPermissions.get(4));
                frame.dispose();
            } catch (IOException ex) {
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
