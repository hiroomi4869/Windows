package Week2;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Window extends JFrame {

    String TITLE_TEXT="通用程序图形化界面";
    String OUTPUT_TEXT="OUTPUT";
    String out="";

    JLabel output = new JLabel(OUTPUT_TEXT);
    Integer WINDOW_WIDTH=400;
    Integer WINDOW_HEIGHT=1200;

    public Window(List<String> list){
        setLayout(new FlowLayout());
        setTitle(TITLE_TEXT);

        getContentPane().add(output);

        for (String temp:list
             ) {
            System.out.println(out);
            out=out+"\n"+temp;
        }

        output.setText(out);
        output.

        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
