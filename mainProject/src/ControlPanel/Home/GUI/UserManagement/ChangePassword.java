package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.FrameAndPanelUtility;
import ControlPanel.Utility.Menubar;
import Server.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static ControlPanel.Login.GUI.loginScreen.parseUserID;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.FrameDispose.disposeFrames;
import static ControlPanel.Utility.HashPassword.hashPassword;
import static ControlPanel.Utility.HashPassword.inputPassHashCheck;

public class ChangePassword extends UserManagement {
    public ChangePassword(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    /**
     * Function creates a GUI for the user to change
     * @param user
     */
    public static void changePasswordWindow(int user){
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

        JLabel newPassword = new JLabel("Enter New Password: ");
        JPasswordField newInput = new JPasswordField(10);
        panel[3].add(newPassword);
        panel[3].add(newInput);

        JButton submitButton = new JButton("Submit");
        panel[4].add(submitButton);

        //Code Below demonstrates the submit button using the input data
        submitButton.addActionListener(e -> {
                    String inputUser = userInput.getText();
                    char[] inputNewPassword = newInput.getPassword();
                    int username = parseUserID(inputUser);
                    String password = new String(inputNewPassword);

//            try {
//                hashNewPassword = new String(hashPassword(NewPassword));
//            } catch (NoSuchAlgorithmException ex) {
//                ex.printStackTrace();
//            }


            try {
                Client.setPasswordS(password, username);
                disposeFrames();
                userManagementGUI();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });




        for(JPanel p: panel) {
            frame.getContentPane().add(p);
        }
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
