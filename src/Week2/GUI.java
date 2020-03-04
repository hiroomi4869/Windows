package Week2;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GUI extends JPanel {


    //JPanel jPanel =new JPanel();

    JList jList = new JList();




    public GUI(List<Vector> list) throws HeadlessException {



        jList.setListData((Vector) list);

        add(jList);
        setSize(150,100);
        setLocation(50,50);
        setBackground(Color.WHITE);
        setVisible(true);



    }

}
