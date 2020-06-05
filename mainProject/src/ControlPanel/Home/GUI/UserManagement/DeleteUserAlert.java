package ControlPanel.Home.GUI.UserManagement;

import ControlPanel.Utility.FrameAndPanelUtility;
import ControlPanel.Utility.FrameDispose;
import Server.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static ControlPanel.Utility.FrameAndPanelUtility.frameManage;
import static ControlPanel.Utility.FrameAndPanelUtility.panelInitialise;
import static ControlPanel.Utility.FrameDispose.disposeFrames;

public class DeleteUserAlert extends UserManagement{
    public DeleteUserAlert(int userID, int sessionToken, boolean createBBPermission, boolean editBBPermission, boolean scheduleBBPermission, boolean editUsersPermission) {
        super(userID, sessionToken, createBBPermission, editBBPermission, scheduleBBPermission, editUsersPermission);
    }

    /**
     * Alert Window if the user selects to delete another user. User must confirm they have chosen, otherwise cancels.
     */
    static void deleteUserAlert() {
        if (getUserID() != getSelectedUser()) {
            JFrame frame = new JFrame("Alert");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation((dim.width / 5) * 2, (dim.height / 5) * 2);
            JPanel[] panel = new JPanel[2];
            frame.setLayout(new GridLayout(2, 1));
            panelInitialise(panel);

            JLabel alert = new JLabel("Are you sure you want to delete UserID:  " + UserManagement.getSelectedUser());

            JButton yes = new JButton("Delete User");
            JButton cancel = new JButton("Cancel");

            yes.addActionListener(e ->
            {
                try {
                    Client.deleteUserS(getSelectedUser());
                    disposeFrames();
                    userManagementGUI();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            cancel.addActionListener(e -> {
                try {
                    FrameDispose.disposeFrames();
                    userManagementGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            panel[0].add(alert);
            panel[1].add(yes);
            panel[1].add(cancel);

            for (JPanel pan : panel) {
                frame.getContentPane().add(pan);
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
