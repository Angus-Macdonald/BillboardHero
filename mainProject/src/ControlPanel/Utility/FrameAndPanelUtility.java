package ControlPanel.Utility;

import javax.swing.*;
import java.awt.*;

public class FrameAndPanelUtility {

    /**
     * This function takes a JFrame and applies some recurred functions that were used. This in comparison to the next function only changes the Close window and screen size,
     * where the latter will also create a frame GridLayout.
     * @param frame
     * @return
     */
    public static Dimension frameManage(JFrame frame){
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/4, dim.height/2));
        return dim;
    }

    /**
     * This function does the same as the last, except is also takes 2 ints in which allow the user to create the frame with a grid layout.
     * @param frame
     * @param rows
     * @param columns
     */
    public static void frameManage(JFrame frame, int rows, int columns){
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(dim.width/5, dim.height/2));
        frame.setLayout(new GridLayout(rows, columns));
    }

    /**
     * This function eliminates panel initialisation recursion by taking a JPanel[] and initialising all the JPanels
     * @param panel
     */
    public static void panelInitialise(JPanel[] panel){
        for(int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
    }

}
