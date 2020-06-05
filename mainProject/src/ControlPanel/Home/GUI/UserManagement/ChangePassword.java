package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.FrameAndPanelUtility;
import ControlPanel.Utility.Menubar;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.HashPassword.hashPassword;
import static ControlPanel.Utility.HashPassword.inputPassHashCheck;

public class ChangePassword extends UserManagement {
    public ChangePassword(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void changePasswordWindow(String user){
        JFrame frame = new JFrame("Change Password");
        FrameAndPanelUtility.frameManage(frame, 5, 1);
        
        JPanel[] panel = new JPanel[5];
        panelInitialise(panel);
        Menubar.menubar(frame);

        JLabel heading = new JLabel("Change Password");
        heading.setFont(new Font("Serif", Font.BOLD, 35));
        panel[0].add(heading);

        JLabel userID = new JLabel("Enter User ID: ");

        JTextField userInput = new JTextField(getSelectedUser().toString(),10);
        panel[1].add(userID);
        panel[1].add(userInput);

        JLabel oldPassword = new JLabel("Enter Old Password: ");
        JPasswordField oldInput = new JPasswordField(10);
        panel[2].add(oldPassword);
        panel[2].add(oldInput);

        JLabel newPassword = new JLabel("Enter New Password: ");
        JPasswordField newInput = new JPasswordField(10);
        panel[3].add(newPassword);
        panel[3].add(newInput);

        JButton submitButton = new JButton("Submit");
        panel[4].add(submitButton);

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


            frame.dispose();

        });

        frame.getContentPane().add(panel[0]);
        frame.getContentPane().add(panel[1]);
        frame.getContentPane().add(panel[2]);
        frame.getContentPane().add(panel[3]);
        frame.getContentPane().add(panel[4]);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
