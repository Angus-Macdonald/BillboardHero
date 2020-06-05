package ControlPanel.Home.GUI;

import ControlPanel.Home.GUI.UserManagement.UserManagement;
import ControlPanel.Utility.User;
import ControlPanel.Utility.Menubar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;


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
        panelInitialise(panel);
        frameManage(frame, 3, 1);
        Dimension dim = frameManage(frame);
        frame.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
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
        Object[][] data = {{"Test", "This", "Test"}};

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
