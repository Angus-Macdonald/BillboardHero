package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static ControlPanel.Home.GUI.UserManagement.changePassword.changePasswordWindow;

public class editUser  extends userManagement {


    public editUser(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    static boolean[] permArray = {getCreateBBPermission(), getEditBBPermission(), getScheduleBBPermission(), getEditUsersPermission()};

    public static void main(String[] args){
        editUserWindow(getSelectedUser());
    }

    public static void editUserWindow(String user){
        JFrame frame = new JFrame("Edit User");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/5, dim.height/2));
        frame.setLayout(new GridLayout(5, 1));

        Menubar.menubar(frame);

        JPanel panel1 = new JPanel();
        JLabel header = new JLabel("Edit User");
        panel1.add(header);
        JPanel panel2 = new JPanel();
        JLabel userID = new JLabel("User ID: ");
        JTextField userInput = new JTextField(getSelectedUser(), 10);
        userInput.setHorizontalAlignment(JTextField.CENTER);
        panel2.add(userID);
        panel2.add(userInput);
        frame.add(panel2);

        JButton changePW = new JButton("Change Password");
        JPanel panel3 = new JPanel();
        panel3.add(changePW);
        changePW.addActionListener(e -> changePasswordWindow(getSelectedUser()));

        JPanel panel4 = new JPanel();
        JLabel permissions = new JLabel("User Permissions");
        panel4.add(permissions);
        panel4.setBorder(new EmptyBorder(50,0,0,0));

        JPanel panel5 = new JPanel(new GridLayout(2, 2));

        JCheckBox createBB = new JCheckBox("Create Billboard ");
        createBB.setSelected(permArray[0]);
        panel5.add(createBB);

        JCheckBox editBB = new JCheckBox("Edit Billboard");
        editBB.setSelected(permArray[1]);
        panel5.add(editBB);

        JCheckBox scheduleBB = new JCheckBox("Schedule Billboard");
        scheduleBB.setSelected(permArray[2]);
        panel5.add(scheduleBB);

        JCheckBox editUsers = new JCheckBox("Edit User");
        editUsers.setSelected(permArray[3]);
        panel5.add(editUsers);
        panel5.setBorder(new EmptyBorder(0, 50,50,0));

        createBB.addActionListener(e -> {
            if(!getCreateBBPermission()){
                setCreateBBPermission(true);
            }
            else if (getCreateBBPermission()){
                setCreateBBPermission(false);
            }

            System.out.println(getCreateBBPermission());
        });

        editBB.addActionListener(e -> {
            if(!getEditBBPermission()){
                setEditBBPermission(true);
            }
            else if (getEditBBPermission()){
                setEditBBPermission(false);
            }
            System.out.println(getEditBBPermission());
        });

        scheduleBB.addActionListener(e -> {
            if(!getScheduleBBPermission()){
                setScheduleBBPermission(true);
            }
            else if (getScheduleBBPermission()){
                setScheduleBBPermission(false);
            }
            System.out.println(getScheduleBBPermission());
        });

        editUsers.addActionListener(e -> {
            if(!getEditUsersPermission()){
                setEditUsersPermission(true);
            }
            else if(getEditUsersPermission()){
                setEditUsersPermission(false);
            }
            System.out.println(getEditUsersPermission());
        });



        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        frame.getContentPane().add(panel4);
        frame.getContentPane().add(panel5);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
