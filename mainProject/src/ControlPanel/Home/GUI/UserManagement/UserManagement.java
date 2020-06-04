package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Home.GUI.GUI;
import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static ControlPanel.Home.GUI.UserManagement.ChangePassword.changePasswordWindow;
import static ControlPanel.Home.GUI.UserManagement.CreateUser.createUserWindow;
import static ControlPanel.Home.GUI.UserManagement.EditUser.editUserWindow;

public class UserManagement extends GUI {

    static String selectedUser = "";

    public static void setSelectedUser(String value){
        selectedUser = value;
    }

    public static String getSelectedUser(){
        return selectedUser;
    }

    public UserManagement(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }
    public static void main(String[] args){
        userManagementGUI();
    }

    public static void userManagementGUI() {

        setWindow("userManagement");

        setEditUsersPermission(true);
        boolean admin = getEditUsersPermission();
        JFrame frame = new JFrame("Account Management");
        JPanel[] panel = new JPanel[3];
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width / 4, dim.height / 2));

        Menubar.menubar(frame);

        String[] userArray = {"12345", "32432", "23421", "43534", "32452", "43253", "99999", "21345"};

        JLabel header = new JLabel("Account Management");
        header.setFont(new Font("Serif", Font.BOLD, 35));
        header.setBorder(new EmptyBorder(10,10,0,10));
        panel[0].add(header);
        JLabel userLabel = new JLabel("User: " + getUserID());
        userLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JList list = new JList(userArray);
        JScrollPane userList = new JScrollPane(list);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(dim.width/5, dim.height/ 3));

        JButton backButton = new JButton("Back Button");

        JButton editUser = new JButton("Edit User");
        JButton deleteUser = new JButton("Delete User");
        JButton createUser = new JButton("Create User");
        JButton changePassword = new JButton("Change Password");

        if(admin) {
            frame.setLayout(new GridLayout(3, 1));
            panel[1].add(userList);
            panel[2].add(editUser);
            panel[2].add(deleteUser);
            panel[2].add(createUser);
        }

        if(!admin) {
            frame.setPreferredSize(new Dimension(dim.width / 4, dim.height / 4));
            frame.setLayout(new GridLayout(3, 1));
            panel[1].add(userLabel);
            panel[2].add(changePassword);
        }

        panel[0].add(backButton);

        backButton.addActionListener(e -> {
            if(getWindow() == "userManagement"){
                frame.dispose();
                GUI.displayGUI();
            }
        });

        //Line Below retrieves selected User from the User List and stores the value within a variable
        list.addListSelectionListener(e -> setSelectedUser((String) list.getSelectedValue()));

        //Line below is the action listener for the Edit User Button. Is a temp test to show it can retrieve the selected user
        editUser.addActionListener(e -> {
            editUserWindow(getSelectedUser());
            System.out.println(getSelectedUser());
        });

        //Line below is the action listener for the Delete User Button. Is a temp test to show it can retrieve the selected user
        deleteUser.addActionListener(e -> DeleteUserAlert.deleteUserAlert());

        createUser.addActionListener(e -> createUserWindow());

        //Following Code implements Changing the password
        changePassword.addActionListener(e -> {
            changePasswordWindow(getSelectedUser());
        });

        frame.getContentPane().add(panel[0]);
        frame.getContentPane().add(panel[1]);
        frame.getContentPane().add(panel[2]);
        if(!admin) {
            frame.getContentPane().add(panel[2]);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
