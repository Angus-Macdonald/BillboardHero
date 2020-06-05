package ControlPanel.Home.GUI.UserManagement;

import javax.swing.*;
import java.awt.*;

import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;

public class DeleteUserAlert extends UserManagement{
    public DeleteUserAlert(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    static void deleteUserAlert(){
        JFrame frame = new JFrame("Alert");
        frameManage(frame, 3 ,1);
        JPanel[] panel = new JPanel[3];
        panelInitialise(panel);


        JLabel alert = new JLabel("Are you sure you want to delete UserID:  " + UserManagement.getSelectedUser());
        JLabel passLabel = new JLabel("Enter Your Password To Continue: ");
        JPasswordField passwordField = new JPasswordField(10);
        JButton yes = new JButton("Delete User");
        JButton cancel = new JButton("Cancel");



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
