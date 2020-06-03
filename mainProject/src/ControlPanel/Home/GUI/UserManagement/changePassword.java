package ControlPanel.Home.GUI.UserManagement;

import javax.swing.*;
import java.awt.*;

public class changePassword {
    public static void changePasswordWindow(){
        JFrame passwordFrame = new JFrame("Change Password");
        passwordFrame.setLayout(new GridLayout(5, 1));
        passwordFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel headingPane = new JPanel();
        JPanel userIdPane = new JPanel();
        JPanel oldPasswordPane = new JPanel();
        JPanel newPasswordPane = new JPanel();
        JPanel submitPane = new JPanel();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        passwordFrame.setJMenuBar(menuBar);

        JLabel heading = new JLabel("Change Password");
        headingPane.add(heading);

        JLabel userID = new JLabel("Enter your User ID: ");
        JTextField userInput = new JTextField();
        userIdPane.add(userID);
        userIdPane.add(userID);

        JLabel oldPassword = new JLabel("Enter your Old Password: ");
        JTextField oldInput = new JTextField();
        oldPasswordPane.add(oldPassword);
        oldPasswordPane.add(oldInput);

        JLabel newPassword = new JLabel("Enter your New Password: ");
        JTextField newInput = new JTextField();
        newPasswordPane.add(newPassword);
        newPasswordPane.add(newInput);

        JButton submitButton = new JButton("Submit");
        submitPane.add(submitButton);

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
