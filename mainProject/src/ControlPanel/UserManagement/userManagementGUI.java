package ControlPanel.UserManagement;

import ControlPanel.MainGUI.controlPanelGUI;
import ControlPanel.Utility.controlPanelExitAlert;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userManagementGUI extends controlPanelGUI {

    public userManagementGUI(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void userManagementGUI() {
        JFrame frame = new JFrame("User Management");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/2, dim.height/2));


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);

        String[] userArray = {"User 1", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4", "User 2", "User 3", "User 4"};

        JLabel header = new JLabel("User Management");
        panel1.setPreferredSize(new Dimension(dim.width/2, dim.height/10));
        panel1.add(header);

        JList list = new JList(userArray);
        JScrollPane userList = new JScrollPane(list);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        panel2.add(userList);

//
        userList.setPreferredSize(new Dimension(dim.width/5, dim.height/5));

        JButton editUser = new JButton("Edit User");
        panel3.add(editUser, BorderLayout.NORTH);
        JButton deleteUser = new JButton("Delete User");
        panel3.add(deleteUser, BorderLayout.SOUTH);

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanelExitAlert.alterWindow();
            }
        });

        final int[] listIndex = new int[1];

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listIndex[0] = e.getFirstIndex();
            }
        });

        deleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Are you sure pop up
                //model.remove(listIndex[0]);

            }
        });

        panel3.setBorder(new EmptyBorder(0,0,0,70));

        frame.getContentPane().add(panel1,  BorderLayout.NORTH);
        frame.getContentPane().add(panel2, BorderLayout.CENTER);
        frame.getContentPane().add(panel3, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
