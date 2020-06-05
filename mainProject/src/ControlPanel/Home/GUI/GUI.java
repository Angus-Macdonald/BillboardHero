package ControlPanel.Home.GUI;

import ControlPanel.Home.GUI.Billboard.billboardCreateGUI;
import ControlPanel.Home.GUI.Billboard.billboardEditGUI;
import ControlPanel.Home.GUI.Billboard.billboardScheduleGUI;
import ControlPanel.Home.GUI.UserManagement.UserManagement;
import ControlPanel.Utility.User;
import ControlPanel.Utility.Menubar;
import Server.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        displayGUI();
    }

    public static void displayGUI() throws IOException, ClassNotFoundException {
        ArrayList<Boolean> permission = new ArrayList<Boolean>();
        try {
            permission = Client.ChkPermsS(getUserID());
            setCreateBBPermission(permission.get(1));
            setEditBBPermission(permission.get(2));
            setScheduleBBPermission(permission.get(3));
            setEditUsersPermission(permission.get(4));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
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
        createBillboard.addActionListener(e ->
                new billboardCreateGUI());

        JButton editBillboard = new JButton("Edit A Billboard");
        editBillboard.addActionListener(e ->
                new billboardEditGUI());

        JButton scheduleBillboard = new JButton("Schedule A Billboard");
        scheduleBillboard.addActionListener(e ->
        {
            try {
                new billboardScheduleGUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

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

        String[] columns = {"Name", "Author"};

        ArrayList billboard = Client.ListBillboardsS();
        Object[][] data = new Object[billboard.size()][2];
        for (int i = 0; i < billboard.size(); i+=2) {
            data[i][i] = billboard.get(i);
            data[i][i+1] = billboard.get(i+1);
        }

        JTable billboardList = new JTable(data, columns);
        JScrollPane table = new JScrollPane(billboardList);
        table.setPreferredSize(new Dimension(dim.width/3, dim.height/3/3));
        table.setBorder(new EmptyBorder(0,20,30,20));


        panel[2].add(table);


        userManagement.addActionListener(e -> {
            try {
                UserManagement.userManagementGUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
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
