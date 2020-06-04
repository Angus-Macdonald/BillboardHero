package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static ControlPanel.Home.GUI.UserManagement.ChangePassword.changePasswordWindow;

public class EditUser extends UserManagement {


    public EditUser(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
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
        frame.setLayout(new GridLayout(6, 1));

        Menubar.menubar(frame);

        JPanel[] panel = new JPanel[6];
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }

        JLabel header = new JLabel("Edit User");
        panel[0].add(header);

        JLabel userID = new JLabel("User ID: ");
        JTextField userInput = new JTextField(getSelectedUser(), 10);
        userInput.setHorizontalAlignment(JTextField.CENTER);
        panel[1].add(userID);
        panel[1].add(userInput);
        frame.add(panel[1]);

        JButton changePW = new JButton("Change Password");
        panel[2].add(changePW);
        changePW.addActionListener(e -> changePasswordWindow(getSelectedUser()));

        JLabel permissions = new JLabel("User Permissions");
        panel[3].add(permissions);
        panel[3].setBorder(new EmptyBorder(50,0,0,0));

        panel[4] = new JPanel(new GridLayout(2, 2));
        JCheckBox createBB = new JCheckBox("Create Billboard ");
        createBB.setSelected(permArray[0]);
        panel[4].add(createBB);

        JCheckBox editBB = new JCheckBox("Edit Billboard");
        editBB.setSelected(permArray[1]);
        panel[4].add(editBB);

        JCheckBox scheduleBB = new JCheckBox("Schedule Billboard");
        scheduleBB.setSelected(permArray[2]);
        panel[4].add(scheduleBB);

        JCheckBox editUsers = new JCheckBox("Edit User");
        editUsers.setSelected(permArray[3]);
        panel[4].add(editUsers);
        panel[4].setBorder(new EmptyBorder(0, 50,50,0));

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

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e ->{
            //Send req/data
            frame.dispose();
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
