package ControlPanel.Utility;

import Server.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static ControlPanel.Login.GUI.loginScreen.controlPanelLogin;
import static java.awt.Frame.getFrames;

public class Logout extends User {
    public Logout(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    /**
     * This function re-initialises the Parent class variables to default on log out.
     * @param frame
     */
    public static void logout(JFrame frame){
        setUserID(0);
        setSessionToken(0);
        setCreateBBPermission(false);
        setEditBBPermission(false);
        setScheduleBBPermission(false);
        setEditUsersPermission(false);
        frame.dispose();
    }

    /**
     * This function displays a GUI log out alert that the user must confirm or deny.
     */
    public static void logoutAlert() {

        JFrame frame = new JFrame("Alert");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel alert = new JLabel("Do you wish to log out? All unsaved progress will be lost.");
        JButton logoutButton = new JButton("Log Out");
        JButton cancelButton = new JButton("Cancel");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width / 5) * 2, (dim.height / 5) * 2);
        frame.setLayout(new GridLayout(2, 1));

        panel1.add(alert);
        panel2.add(logoutButton);
        panel2.add(cancelButton);

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);

        logoutButton.addActionListener(e -> {
            Frame[] frames = getFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
                try {
                    Client.logoutS(getUserID());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                logout(frame);
            }
            try {
                controlPanelLogin();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

        });

        cancelButton.addActionListener(e -> {
            frame.dispose();

        });

        frame.pack();
        frame.setVisible(true);
    }
}
