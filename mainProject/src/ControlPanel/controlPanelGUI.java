package ControlPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;




public class controlPanelGUI extends controlPanelUser {

    public controlPanelGUI(String test, int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsers) {
        super(test, userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsers);
    }

    public static void displayGUI(){
        Map<String, Boolean> userPermissionFromServer = new HashMap<String, Boolean>();
        userPermissionFromServer.put("createBillboards", false);
        userPermissionFromServer.put("editAllBillboards", false);
        userPermissionFromServer.put("scheduleBillboards", false);
        userPermissionFromServer.put("editUsers", true);

        JFrame frame = new JFrame("Control Panel GUI");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Open");

        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new GridLayout(3,1));


        JLabel controlPanelHead = new JLabel();
        controlPanelHead.setFont(new Font("Serif", Font.BOLD, 48));
        controlPanelHead.setBorder(new EmptyBorder(0,10,0,10));
        JButton createBillboard = new JButton("Create New Billboard");
        JButton editBillboard = new JButton("Edit A Billboard");
        JButton scheduleBillboard = new JButton("Schedule A Billboard");
        JButton userManagement = new JButton("User Management");

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
        if(userPermissionFromServer.get("editUsers")) {
            panel3.add(userManagement);
        }
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        frame.pack();
        frame.setVisible(true);
    }
}
