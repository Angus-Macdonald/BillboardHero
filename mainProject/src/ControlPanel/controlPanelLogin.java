package ControlPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlPanelLogin extends controlPanelUser {

    public controlPanelLogin(String test, int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsers) {
        super(test, userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsers);
    }

    public static void submitUsernameAndPassword(String user){
        controlPanelUser.setTest(user);
    }

    public static void controlPanelLogin(){
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

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelExitAlert.alterWindow();
        }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUser = inputUsername.getText();
                //char[] inputPass = inputPassword.getPassword();
                submitUsernameAndPassword(inputUser);
                frame.dispose();
                controlPanelGUI.displayGUI();

            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
