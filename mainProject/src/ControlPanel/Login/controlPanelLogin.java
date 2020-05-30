package ControlPanel.Login;
import ControlPanel.MainGUI.controlPanelGUI;

import ControlPanel.Utility.controlPanelExitAlert;
import ControlPanel.Utility.controlPanelUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.SQLException;

import static Server.logIO.login;

public class controlPanelLogin extends controlPanelUser {

    public controlPanelLogin(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void loginRequest(String user, byte[] pass){
        //Convert user and pass to data types needed for request
        //Make the request and receive the id and token
        //setUserID(id);
        //setSessionToken(token);
    }

    public void getPermissions(String user, String token){

        //This function will add data from server to an array, then place the data within the controlPanelUser object

        //boolean[] permissions = new boolean[4];

        //getCreateBBPermissionFromServer(user, token).add(permission[0]);
        //getEditBBPermissionFromServer(user, token).add(permission[1]);
        //getScheduleBBPermissionFromServer(user, token).add(permission[2]);
        //getEditUsersPermissionFromServer(user, token).add(permission[3]);

        //setCreateBBPermission(permission[0]);
        //setEditBBPermission(permission[1]);
        //setScheduleBBPermission(permission[2]);
        //setEditUsersPermission(permission[3]);
    }

    public static int parseUserID(String user){
        int foo;
        try{
            foo = Integer.parseInt(user);
        }
        catch (NumberFormatException e)
        {
            foo = 0;
        }

        return foo;
    }

    public static void controlPanelLogin() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);

        JFrame frame = new JFrame("Login Page");
        JPanel panel1 = new JPanel();
        JPanel panel2= new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        frame.setLayout(new GridLayout(4,2));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        frame.setLocation(dim.width/3, dim.height/3);


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);
        JLabel header = new JLabel("Log In");
        header.setFont(new Font("Helvetica", Font.BOLD, 48));
        header.setBorder(new EmptyBorder(10,10,10,10));
        panel1.add(header);
        JLabel username = new JLabel("Username: ");
        username.setFont(new Font("Helvetica", Font.PLAIN, 20));
        panel2.add(username);
        JTextField inputUsername = new JTextField(15);
        panel2.add(inputUsername);
        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Helvetica", Font.PLAIN, 20));
        panel3.add(password);
        JPasswordField inputPassword = new JPasswordField(15);
        panel3.add(inputPassword);
        JButton loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(150,50));
        panel4.add(loginButton);

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        frame.getContentPane().add(panel4);

        item.addActionListener(e -> controlPanelExitAlert.alterWindow());

        loginButton.addActionListener(e -> {
            String inputUser = inputUsername.getText();
            char[] inputPass = inputPassword.getPassword();

            StringBuilder pass = new StringBuilder();
            for (char i :inputPass) {
                pass.append(i);
            }
            byte[] hashedPassword = md.digest(pass.toString().getBytes(StandardCharsets.UTF_8));

            int userID = parseUserID(inputUser);
            String password1 = new String(hashedPassword);

            System.out.println(password1);

//            int serverResponse = 0;
//
//            try {
//                serverResponse = login(userID, password1);
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//
//            frame.dispose();
//            controlPanelGUI.displayGUI();


        });

        frame.pack();
        frame.setVisible(true);
    }
}

//Testing Global GIT Author Commit