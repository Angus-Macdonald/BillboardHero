package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.Menubar;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

import static ControlPanel.Utility.HashPassword.hashPassword;
import static ControlPanel.Utility.HashPassword.inputPassHashCheck;

public class changePassword extends userManagement {
    public changePassword(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void changePasswordWindow(String user){
        JFrame passwordFrame = new JFrame("Change Password");
        passwordFrame.setLayout(new GridLayout(5, 1));
        passwordFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        passwordFrame.setPreferredSize(new Dimension(dim.width / 5, dim.height / 2));

        JPanel headingPane = new JPanel();
        JPanel userIdPane = new JPanel();
        JPanel oldPasswordPane = new JPanel();
        JPanel newPasswordPane = new JPanel();
        JPanel submitPane = new JPanel();

        Menubar.menubar(passwordFrame);

        JLabel heading = new JLabel("Change Password");
        heading.setFont(new Font("Serif", Font.BOLD, 35));
        headingPane.add(heading);

        JLabel userID = new JLabel("Enter User ID: ");

        JTextField userInput = new JTextField(getSelectedUser(),10);
        userIdPane.add(userID);
        userIdPane.add(userInput);

        JLabel oldPassword = new JLabel("Enter Old Password: ");
        JPasswordField oldInput = new JPasswordField(10);
        oldPasswordPane.add(oldPassword);
        oldPasswordPane.add(oldInput);

        JLabel newPassword = new JLabel("Enter New Password: ");
        JPasswordField newInput = new JPasswordField(10);
        newPasswordPane.add(newPassword);
        newPasswordPane.add(newInput);

        JButton submitButton = new JButton("Submit");
        submitPane.add(submitButton);

        //Code Below demonstrates the submit button using the input data
        submitButton.addActionListener(e -> {
            char[] inputOldPassword = oldInput.getPassword();
            char[] inputNewPassword = newInput.getPassword();

            String OldPassword = null;
            String NewPassword = null;

            for(char i:inputOldPassword){OldPassword += i;}
            for(char i:inputNewPassword){NewPassword += i;}

            String hashOldPassword = null;
            String hashNewPassword = null;

            try {
                hashOldPassword = new String(hashPassword(OldPassword));
                hashNewPassword = new String(hashPassword(NewPassword));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

            if(inputPassHashCheck(OldPassword, hashOldPassword) && inputPassHashCheck(NewPassword, hashNewPassword)){
                //If both passwords are hashed, do something
                System.out.println(hashOldPassword);
                System.out.println(hashNewPassword);
            }


            passwordFrame.dispose();

        });

        passwordFrame.getContentPane().add(headingPane);
        passwordFrame.getContentPane().add(userIdPane);
        passwordFrame.getContentPane().add(oldPasswordPane);
        passwordFrame.getContentPane().add(newPasswordPane);
        passwordFrame.getContentPane().add(submitPane);

        passwordFrame.pack();
        passwordFrame.setLocationRelativeTo(null);
        passwordFrame.setVisible(true);

    }
}
