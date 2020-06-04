package ControlPanel.Home.GUI.UserManagement;

import javax.swing.*;
import java.awt.*;

public class DeleteUserAlert extends UserManagement{
    public DeleteUserAlert(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    static void deleteUserAlert(){
        JFrame frame = new JFrame("Alert");
        JPanel[] panel = new JPanel[3];
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
        JLabel alert = new JLabel("Are you sure you want to delete UserID:  " + UserManagement.getSelectedUser());
        JLabel passLabel = new JLabel("Enter Your Password To Continue: ");
        JPasswordField passwordField = new JPasswordField(10);
        JButton yes = new JButton("Delete User");
        JButton cancel = new JButton("Cancel");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width/5)*2, (dim.height/5)*2);
        frame.setLayout(new GridLayout(3,1));

        yes.addActionListener(e->
                frame.dispose()
        );

        cancel.addActionListener(e ->
                frame.dispose()
        );

        panel[0].add(alert);
        panel[1].add(passLabel);
        panel[1].add(passwordField);
        panel[2].add(yes);
        panel[2].add(cancel);

        for(JPanel pan: panel){
            frame.getContentPane().add(pan);
        }
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }
}
