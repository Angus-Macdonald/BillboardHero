package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Home.GUI.controlPanelGUI;
import ControlPanel.Utility.controlPanelExitAlert;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userManagement extends controlPanelGUI {

    public userManagement(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void createUser(){
        //EDIT USER PERMISSION
        //Code to create user
    }

    public static void editUser(){
        //EDIT USER PERMISSION
        //Code to edit user
    }

    public static void deleteUser(){
        //EDIT USER PERMISSION
        //CANNOT DELETE OWN PROFILE
        //Code to delete user
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
        frame.setPreferredSize(new Dimension(dim.width/2, dim.height/2));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new GridLayout(2, 2));

        String[] userArray = {"User 1", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4"};

        JLabel header = new JLabel("Account Management");
        header.setFont(new Font("Serif", Font.BOLD, 35));
        header.setBorder(new EmptyBorder(10,10,0,10));
        panel1.add(header);

        JList list = new JList(userArray);
        JScrollPane userList = new JScrollPane(list);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        if(admin){
            panel3.add(userList, BorderLayout.LINE_START);
        }

        userList.setPreferredSize(new Dimension(dim.width/5, dim.height/5));

        JButton editUser = new JButton("Edit User");
        JButton deleteUser = new JButton("Delete User");
        JButton changePassword = new JButton("Change Password");

//        if(admin) {
//            panel4.add(editUser, BorderLayout.LINE_END);
//            panel4.add(deleteUser, BorderLayout.LINE_END);
//            panel4.add(changePassword, BorderLayout.LINE_END);
//        }

        item.addActionListener(e -> controlPanelExitAlert.alterWindow());

        final int[] listIndex = new int[1];
        list.addListSelectionListener(e -> listIndex[0] = e.getFirstIndex());

        deleteUser.addActionListener(e -> deleteUser());

        panel3.setBorder(new EmptyBorder(0,0,0,70));

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
//        frame.getContentPane().add(panel4);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
