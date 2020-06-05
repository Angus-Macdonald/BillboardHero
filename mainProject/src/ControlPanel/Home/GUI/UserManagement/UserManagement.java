package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Home.GUI.GUI;
import ControlPanel.Utility.Menubar;
import Server.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static ControlPanel.Home.GUI.UserManagement.ChangePassword.changePasswordWindow;
import static ControlPanel.Home.GUI.UserManagement.CreateUser.createUserWindow;
import static ControlPanel.Home.GUI.UserManagement.EditUser.editUserWindow;
import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.FrameDispose.disposeFrames;

public class UserManagement extends GUI {

    static Integer selectedUser = 0;

    public static void setSelectedUser(Integer value){
        selectedUser = value;
    }

    public static Integer getSelectedUser(){
        return selectedUser;
    }

    public UserManagement(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        userManagementGUI();
    }

    public static void userManagementGUI() throws IOException, ClassNotFoundException {

        setWindow("userManagement");

        JFrame frame = new JFrame("Account Management");
        JPanel[] panel = new JPanel[3];
        panelInitialise(panel);
        frameManage(frame);
        Menubar.menubar(frame);

        ArrayList<Integer> users = Client.listUsersS();
        Integer[] userArray = users.toArray(new Integer[0]);

        JLabel header = new JLabel("Account Management");
        header.setFont(new Font("Serif", Font.BOLD, 35));
        header.setBorder(new EmptyBorder(10,10,0,10));
        panel[0].add(header);
        JLabel userLabel = new JLabel("User: " + getUserID());
        userLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JList list = new JList(userArray);
        JScrollPane userList = new JScrollPane(list);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(frameManage(frame).width/5, frameManage(frame).height/ 3));

        JButton backButton = new JButton("Back Button");

        JButton editUser = new JButton("Edit User");
        JButton deleteUser = new JButton("Delete User");
        JButton createUser = new JButton("Create User");
        JButton changePassword = new JButton("Change Password");

        if(getEditUsersPermission()) {
            frame.setLayout(new GridLayout(3, 1));
            panel[1].add(userList);
            panel[2].add(editUser);
            panel[2].add(deleteUser);
            panel[2].add(createUser);
        }

        if(!getEditUsersPermission()) {
            frame.setPreferredSize(new Dimension(frameManage(frame).width / 4, frameManage(frame).height / 4));
            frame.setLayout(new GridLayout(3, 1));
            panel[1].add(userLabel);
            panel[2].add(changePassword);
        }

        panel[0].add(backButton);

        backButton.addActionListener(e -> {
            if(getWindow() == "userManagement"){
                disposeFrames();
                GUI.displayGUI();
            }
        });

        //Line Below retrieves selected User from the User List and stores the value within a variable
        list.addListSelectionListener(e -> setSelectedUser((int) list.getSelectedValue()));

        //Line below is the action listener for the Edit User Button. Is a temp test to show it can retrieve the selected user
        editUser.addActionListener(e -> {
            try {
                disposeFrames();
                editUserWindow(getSelectedUser());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        //Line below is the action listener for the Delete User Button. Is a temp test to show it can retrieve the selected user
        deleteUser.addActionListener(e -> {
            DeleteUserAlert.deleteUserAlert();
        });

        createUser.addActionListener(e -> {
            try {
                disposeFrames();
                createUserWindow();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        });

        //Following Code implements Changing the password
        changePassword.addActionListener(e -> {
            changePasswordWindow(getSelectedUser());
        });

        frame.getContentPane().add(panel[0]);
        frame.getContentPane().add(panel[1]);
        frame.getContentPane().add(panel[2]);
        if(!getEditUsersPermission()) {
            frame.getContentPane().add(panel[2]);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
