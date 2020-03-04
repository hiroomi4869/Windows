package Week2;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class MyFrame {


    public MyFrame(List<Vector> list) {
        GUI window =new GUI(list);



        JFrame jf = new JFrame();
        jf.add(window);
        jf.setVisible(true);
        jf.setSize(600,300);
    }
}
