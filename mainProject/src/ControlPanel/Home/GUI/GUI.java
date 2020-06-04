package ControlPanel.Home.GUI;

import ControlPanel.Home.GUI.UserManagement.UserManagement;
import ControlPanel.Utility.User;
import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


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

        JPanel[] panel = new JPanel[3];

        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        frame.setLocation(dim.width/3, dim.height/3);
        frame.setLayout(new GridLayout(3, 1));

        Menubar.menubar(frame);

        JLabel controlPanelHead = new JLabel("Billboard Hero Control Panel");
        controlPanelHead.setFont(new Font("Serif", Font.BOLD, 35));
        controlPanelHead.setBorder(new EmptyBorder(10,10,0,10));
        JButton createBillboard = new JButton("Create New Billboard");
        JButton editBillboard = new JButton("Edit A Billboard");
        JButton scheduleBillboard = new JButton("Schedule A Billboard");
        JButton userManagement = new JButton("Account Management");

        panel[0].add(controlPanelHead);

        if(getCreateBBPermission()) {
            panel[1].add(createBillboard);
        }
        if(getEditBBPermission()) {
            panel[1].add(editBillboard);
        }
        if(getScheduleBBPermission()) {
            panel[1].add(scheduleBillboard);
        }

        panel[1].add(userManagement);

        String[] columns = {"Name", "Date", "Author"};

        //Fill Data with Billboard Data
        Object[][] data = {{}};

        JTable billboardList = new JTable(data, columns);
        JScrollPane table = new JScrollPane(billboardList);
        table.setPreferredSize(new Dimension(dim.width/3, dim.height/3/3));
        table.setBorder(new EmptyBorder(0,20,30,20));


        panel[2].add(table);


        userManagement.addActionListener(e -> {
            UserManagement.userManagementGUI();
            frame.dispose();
        });
        for(JPanel pan: panel){
            frame.getContentPane().add(pan);
        }

        frame.pack();
        frame.setVisible(true);
    }
}
