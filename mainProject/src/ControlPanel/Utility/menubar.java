package ControlPanel.Utility;

import javax.swing.*;
import java.awt.*;

import static ControlPanel.Utility.logoutAlert.logoutAlert;

public class menubar {
    public static void menubar(JFrame frame){
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

    }
}
