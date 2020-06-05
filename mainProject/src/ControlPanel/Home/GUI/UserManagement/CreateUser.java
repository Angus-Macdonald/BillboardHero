package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static ControlPanel.Login.GUI.loginScreen.parseUserID;
import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.FrameDispose.disposeFrames;
import static ControlPanel.Utility.HashPassword.hashPassword;
import static Server.Client.createUserS;
import static java.lang.Integer.parseInt;

public class CreateUser {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        createUserWindow();
    }

    public static void createUserWindow() throws NoSuchAlgorithmException {
        JFrame frame = new JFrame("Create User");
        frameManage(frame, 5, 1);
        Menubar.menubar(frame);
        JPanel[] panel = new JPanel[5];
        panelInitialise(panel);

        JLabel header = new JLabel("Create User");
        header.setFont(new Font("Serif", Font.BOLD, 35));
        panel[0].add(header);

        JLabel ID = new JLabel("New User ID: ");
        JTextField inputID = new JTextField(10);
        panel[1].add(ID);
        panel[1].add(inputID);

        JLabel PW = new JLabel("New User Password: ");
        JPasswordField inputPW = new JPasswordField(10);
        inputPW.setHorizontalAlignment(SwingConstants.CENTER);
        panel[2].add(PW);
        panel[2].add(inputPW);

        boolean[] permArray = new boolean[4];
        panel[3] = new JPanel(new GridLayout(2, 2));

        JCheckBox createBB = new JCheckBox("Create Billboard ");
        panel[3].add(createBB);
        createBB.addActionListener(e ->{
            if(permArray[0]){
                permArray[0] = false;
            }
            else if(!permArray[0]){
                permArray[0] = true;
            }
        });

        JCheckBox editBB = new JCheckBox("Edit Billboard");
        panel[3].add(editBB);

        editBB.addActionListener(e ->{
            if(permArray[1]){
                permArray[1] = false;
            }
            else if(!permArray[1]){
                permArray[1] = true;
            }
        });

        JCheckBox scheduleBB = new JCheckBox("Schedule Billboard");
        panel[3].add(scheduleBB);

        scheduleBB.addActionListener(e ->{
            if(permArray[2]){
                permArray[2] = false;
            }
            else if(!permArray[2]){
                permArray[2] = true;
            }
        });

        JCheckBox editUsers = new JCheckBox("Edit User");
        panel[3].add(editUsers);

        editUsers.addActionListener(e ->{
            if(permArray[3]){
                permArray[3] = false;
            }
            else if(!permArray[3]){
                permArray[3] = true;
            }
        });

        panel[3].setBorder(new EmptyBorder(0, 50,40,0));

        JButton submit = new JButton("Submit");
        panel[4].add(submit);

        submit.addActionListener(e ->{
            String inputUser = inputID.getText();
            char[] inputPass = inputPW.getPassword();

            int user = parseUserID(inputUser);

            String pass = new String(inputPass);
//            try {
//                password = new String(hashPassword(pass));
//            } catch (NoSuchAlgorithmException ex) {
//                ex.printStackTrace();
//            }
            try {
                createUserS(user, pass, false, permArray[0], permArray[1], permArray[2], permArray[3]);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            disposeFrames();
            try {
                UserManagement.userManagementGUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        for (JPanel j: panel
             ) {frame.getContentPane().add(j);

        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }
}
