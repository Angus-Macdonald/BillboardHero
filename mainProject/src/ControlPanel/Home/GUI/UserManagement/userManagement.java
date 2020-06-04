package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Home.GUI.controlPanelGUI;
import ControlPanel.Utility.controlPanelExitAlert;
import Server.Users;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ControlPanel.Home.GUI.UserManagement.changePassword.changePasswordWindow;
import static ControlPanel.Home.GUI.UserManagement.editUser.editUserWindow;

public class userManagement extends controlPanelGUI {

    static String selectedUser = "";

    public static void setSelectedUser(String value){
        selectedUser = value;
    }

    public static String getSelectedUser(){
        return selectedUser;
    }

    public userManagement(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void changePassword(){
        //Code to change a users password
        //If EDIT user permission, can change any password
        //Else, can only change their own user password

        //Enter UserID - Check edit user permission:
        //                      if false, check own UserID, if inputID and userID == false, return error
        //                      if true, continue;

        //Enter Your Password - Check edit user permission:
        //                      if false: check inputPassword == databasePassword for userID, if false return error
        //                      if true: check inputPassword == getUserID.dbPassword, if false return error

        //Enter New Password:
        //                  Check new password != old password
        //                  Check new password == int data type
        //                  Check new password has been hashed, use inputPassHashCheck Function(inputPW, hashPW), return true if they aren't equal
    }


    public static void main(String[] args){
        userManagementGUI();
    }
    public static void userManagementGUI() {
        setEditUsersPermission(true);
        boolean admin = getEditUsersPermission();
        JFrame frame = new JFrame("Account Management");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if(admin) {
            frame.setPreferredSize(new Dimension(dim.width / 2, dim.height / 2));
        }
        else {
            frame.setPreferredSize(new Dimension(dim.width / 4, dim.height / 4));
        }
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);

        String[] userArray = {"User 1", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4"};

        JLabel header = new JLabel("Account Management");
        header.setFont(new Font("Serif", Font.BOLD, 35));
        header.setBorder(new EmptyBorder(10,10,0,10));
        panel1.add(header);
        JLabel userLabel = new JLabel("User: " + getUserID());


        JList list = new JList(userArray);
        JScrollPane userList = new JScrollPane(list);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        userList.setPreferredSize(new Dimension(dim.width/5, dim.height/5/2));

        JButton editUser = new JButton("Edit User");
        JButton deleteUser = new JButton("Delete User");
        JButton changePassword = new JButton("Change Password");

        if(admin) {
            frame.setLayout(new GridLayout(2, 3));
            panel2.add(userList);
            panel2.add(editUser);
            panel2.add(deleteUser);
        }

        if(!admin) {
            frame.setLayout(new GridLayout(3, 1));
            panel2.add(userLabel);
            panel3.add(changePassword);
        }

        item.addActionListener(e -> controlPanelExitAlert.alterWindow());

        //Line Below retrieves selected User from the User List and stores the value within a variable
        list.addListSelectionListener(e -> setSelectedUser((String) list.getSelectedValue()));

        //Line below is the action listener for the Edit User Button. Is a temp test to show it can retrieve the selected user
        editUser.addActionListener(e -> {
            editUserWindow(getSelectedUser());
            System.out.println(getSelectedUser());
        });

        //Line below is the action listener for the Delete User Button. Is a temp test to show it can retrieve the selected user
        deleteUser.addActionListener(e -> System.out.println("Delete User: "+ selectedUser));

        //Following Code implements Changing the password
        changePassword.addActionListener(e -> {
            changePasswordWindow(getSelectedUser());
        });

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        if(!admin) {
            frame.getContentPane().add(panel3);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
