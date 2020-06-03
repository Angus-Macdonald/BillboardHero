package ControlPanel.Home.GUI.UserManagement;

import javax.swing.*;
import java.awt.*;

public class editUser  extends userManagement {
    public editUser(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    public static void editUserWindow(){
        JFrame frame = new JFrame("Edit User");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/5, dim.height/2));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);

        JPanel panel1 = new JPanel();
        JLabel header = new JLabel("Edit User");
        panel1.add(header);


    }
}
