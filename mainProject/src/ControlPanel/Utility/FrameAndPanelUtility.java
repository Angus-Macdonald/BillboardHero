package ControlPanel.Utility;

import javax.swing.*;
import java.awt.*;

public class FrameAndPanelUtility {

    public static Dimension frameManage(JFrame frame){
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/4, dim.height/2));
        return dim;
    }

    public static void frameManage(JFrame frame, int rows, int columns){
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/5, dim.height/2));
        frame.setLayout(new GridLayout(rows, columns));
    }

    public static void panelInitialise(JPanel[] panel){
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
    }

}
