package ControlPanel.Home.GUI;

import ControlPanel.Utility.QuitAlert;
import ControlPanel.Utility.controlPanelUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static ControlPanel.Utility.logoutAlert.logoutAlert;


public class controlPanelGUI extends controlPanelUser {

    public controlPanelGUI(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void main(String[] args){
        displayGUI();
    }

    public static void displayGUI(){
        Map<String, Boolean> userPermissionFromServer = new HashMap<String, Boolean>();
        userPermissionFromServer.put("createBillboards", true);
        userPermissionFromServer.put("editAllBillboards", true);
        userPermissionFromServer.put("scheduleBillboards", true);
        userPermissionFromServer.put("editUsers", true);

        JFrame frame = new JFrame("Control Panel GUI");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        frame.setLocation(dim.width/3, dim.height/3);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem logout = new JMenuItem("Log Out");
        logout.addActionListener(e -> {
            logoutAlert();

        });

        quit.addActionListener(e -> QuitAlert.alterWindow());

        menuBar.add(menu);
        menu.add(logout);
        menu.add(quit);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new GridLayout(3,1));

        JLabel controlPanelHead = new JLabel("Billboard Hero Control Panel");
        controlPanelHead.setFont(new Font("Serif", Font.BOLD, 35));
        controlPanelHead.setBorder(new EmptyBorder(10,10,0,10));
        JButton createBillboard = new JButton("Create New Billboard");
        JButton editBillboard = new JButton("Edit A Billboard");
        JButton scheduleBillboard = new JButton("Schedule A Billboard");
        JButton userManagement = new JButton("Account Management");

        panel1.add(controlPanelHead);

        if(userPermissionFromServer.get("createBillboards")) {
            panel2.add(createBillboard);
        }
        if(userPermissionFromServer.get("editAllBillboards")) {
            panel2.add(editBillboard);
        }
        if(userPermissionFromServer.get("scheduleBillboards")) {
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
