package ControlPanel.Home.GUI;

import ControlPanel.Utility.User;
import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class GUI extends User {

    public GUI(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    private static String window;

    public static void setWindow(String window) {
        GUI.window = window;
    }

    public static String getWindow() {
        return window;
    }

    public static void main(String[] args){
        displayGUI();
    }

    public static void displayGUI(){
        JFrame frame = new JFrame("Control Panel GUI");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        frame.setLocation(dim.width/3, dim.height/3);

        Menubar.menubar(frame);

        JLabel controlPanelHead = new JLabel("Billboard Hero Control Panel");
        controlPanelHead.setFont(new Font("Serif", Font.BOLD, 35));
        controlPanelHead.setBorder(new EmptyBorder(10,10,0,10));
        JButton createBillboard = new JButton("Create New Billboard");
        JButton editBillboard = new JButton("Edit A Billboard");
        JButton scheduleBillboard = new JButton("Schedule A Billboard");
        JButton userManagement = new JButton("Account Management");

        panel1.add(controlPanelHead);

        setUserID(10);

        if(getCreateBBPermission()) {
            panel3.add(createBillboard);
        }
        if(getEditBBPermission()) {
            panel3.add(editBillboard);
        }
        if(getScheduleBBPermission()) {
            panel3.add(scheduleBillboard);
        }

        panel3.add(userManagement);

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);

        userManagement.addActionListener(e -> {
            ControlPanel.Home.GUI.UserManagement.userManagement.userManagementGUI();
            frame.dispose();
        });

        frame.pack();
        frame.setVisible(true);
    }
}
