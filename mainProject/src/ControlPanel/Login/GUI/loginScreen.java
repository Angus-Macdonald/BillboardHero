package ControlPanel.Login.GUI;
import ControlPanel.Home.GUI.GUI;

import ControlPanel.Utility.QuitAlert;
import ControlPanel.Utility.User;
import Server.Client;
import Server.logIO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.security.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static ControlPanel.Home.GUI.GUI.displayGUI;
import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.HashPassword.hashPassword;
import static ControlPanel.Utility.HashPassword.inputPassHashCheck;
import static ControlPanel.Utility.Menubar.menubar;
import static Server.Client.loginS;

public class loginScreen extends User {

    public loginScreen(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    /**
     * Function takes a String data type and converts to an int data type.
     * @param user Input a value that is a String data type that you wish to change to an int
     * @return Function will return int if the String can be converted to int, otherwise returns as 0
     */
    public static int parseUserID(String user){
        int parseUser;
        try{
            parseUser = Integer.parseInt(user);
        }
        catch (NumberFormatException e)
        {
            parseUser = 0;
        }
        return parseUser;
    }


    /**
     * Function that runs the controlPanelLogin function, used for development.
     * @param args No arguments are used.
     * @throws NoSuchAlgorithmException Throws exception if password hashing isn't usable
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        controlPanelLogin();
    }

    /**
     * This is the Control Panel Login GUI. It displays a GUI for the user to log in.
     * The function takes a userID and password, and makes a request to log in to the server once the log in button is chosen.
     * The password is "hashed" (function was created, but implementation was not).
     * If the user successfull logs in it will call the Control Panel Home GUI.
     * @throws NoSuchAlgorithmException Throws exception when password hashing isn't allowed, but was never implemented.
     */
    public static void controlPanelLogin() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);

        JFrame frame = new JFrame("Login Page");
        JPanel[] panel = new JPanel[4];
        panelInitialise(panel);

        frameManage(frame, 4 ,2);
        JMenuBar menuBar = new JMenuBar();
        menubar(frame);

        JLabel header = new JLabel("Log In");
        header.setFont(new Font("Helvetica", Font.BOLD, 48));
        header.setBorder(new EmptyBorder(10,10,10,10));
        panel[0].add(header);
        JLabel username = new JLabel("Username: ");
        username.setFont(new Font("Helvetica", Font.PLAIN, 20));
        panel[1].add(username);
        JTextField inputUsername = new JTextField(15);
        panel[1].add(inputUsername);
        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Helvetica", Font.PLAIN, 20));
        panel[2].add(password);
        JPasswordField inputPassword = new JPasswordField(15);
        panel[2].add(inputPassword);
        JButton loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(150,50));
        panel[3].add(loginButton);

        for (JPanel pan: panel){
            frame.getContentPane().add(pan);
        }
        loginButton.addActionListener(e -> {
            String inputUser = inputUsername.getText();
            char[] inputPass = inputPassword.getPassword();
            int userID = parseUserID(inputUser);
            String pass = new String(inputPass);


            int serverResponse = 0;

            try {
                serverResponse = loginS(userID, pass);
            } catch (IOException ex) {
                    ex.printStackTrace();
            }

            if(serverResponse != 0){
                setUserID(userID);
                setSessionToken(serverResponse);

                frame.dispose();
                try {
                    displayGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }


        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
