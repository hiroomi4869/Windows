package Go; /*************************MainWindow**********************/
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
//抽象类，不能用于构造方法创建对象
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
//负责创建标签对象，标签用来显示信息，但没有编辑功能
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;             //利用import引用各类包

public class MainWindow extends Frame implements Runnable
{
    Go panelGo=new Go();   //用Go类声明并创建一个panelGo对象
    Image myImage;
    int PORT;
    Socket sendSocket;               //主动连接Socket
    PrintWriter writer;              //用来发送message
    boolean stopFlag;
    boolean isInitiative;
    Point messagePoint;
    Point goStartPoint=null;         //初始化
    Point yellowPoint=null;
    boolean stepColor=true;
    Point LastPoint=null;           //移除黄点时，判断位置变动
    BorderLayout borderLayout1 = new BorderLayout();    //用BorderLayout布局
    Panel panel1 = new Panel();     //创建面板
    Panel panel2 = new Panel();
    BorderLayout borderLayout2 = new BorderLayout();
    Panel panel3 = new Panel();
    CheckboxGroup checkboxGroup1 = new CheckboxGroup();    //选项框
    Checkbox checkbox1 = new Checkbox();   //声明创建Checkbox类的对象
    Checkbox checkbox2 = new Checkbox();
    Label label1 = new Label();
    TextField textField1 = new TextField();
    Button button1 = new Button();
    Label label2 = new Label();
    Choice choice1 = new Choice();
    Button button2 = new Button();
    GridLayout gridLayout1 = new GridLayout();            //利用GridLayout布局
    BorderLayout borderLayout3 = new BorderLayout();


    public MainWindow()         //构造MainWindow方法
    {
        try
        {
            jbInit();     //可能发生异常的语句
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }       //异常处理
    }
    private void jbInit() throws Exception   //用关键字throws声明抛出异常
    {
        choice1.setBackground(new Color(236, 190, 120));   //设置棋盘底色
        button1.setBackground(new Color(236, 190, 120));
        this.setResizable(false);
        new Thread(this).start();                         //启动监听线程
        this.PORT=1976;
        this.isInitiative=false;                          //是否主动连接
        this.stopFlag=false;                              //是否继续监听的标志
        this.choice1.addItem("黑");
        this.choice1.addItem("白");
        LastPoint=new Point();
        messagePoint=new Point();
        this.setSize(470,450);
        this.setTitle("围棋程序       作者:围棋对弈小组");   //设置标题
        this.panelGo.setEnabled(false);                 //开始之前屏蔽掉盘面
        checkbox1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                checkbox1_mouseClicked(e);
            }
        });            //获取监听器
        this.goStartPoint=this.panelGo.getLocation();    //
        this.setLayout(borderLayout1);            //设置背景布局
        panel1.setLayout(borderLayout2);
        checkbox1.setCheckboxGroup(checkboxGroup1);
        checkbox1.setLabel("单机");
        checkbox2.setCheckboxGroup(checkboxGroup1);
        checkbox2.setLabel("联机");              //设置名为联机的复选框
        checkbox2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                checkbox2_mouseClicked(e);
            }
        });       //获取监听器
        label1.setText("对方地址");//
        button1.setLabel("连接");      //设置名为连接的按钮
        button1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                button1_actionPerformed(e);
            }
        });               //告诉监听器所发生的事件，并执行方法
        label2.setText("  ");
        button2.setBackground(new Color(236, 190, 120));
        button2.setLabel("开始");     //设置名为开始的按钮
        button2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                button2_actionPerformed(e);
            }
        });          //
        panel3.setLayout(gridLayout1);     //设置布局
        gridLayout1.setRows(8);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(100);
        gridLayout1.setVgap(10);      //
        panel2.setLayout(borderLayout3);   //
        this.panel2.setSize(500,70);       //
        panelGo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(MouseEvent e)
            {
                panelGo_mouseMoved(e);
            }
        });            //获取监听器

        panelGo.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                panelGo_mouseClicked(e);
            }
        });           //获取监听器

        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                this_windowClosing(e);
            }
        });          //获取监听器
        panel3.setBackground(new Color(236, 190, 120));   //设置背景颜色
        //初始化创建的对象
        panel3.add(checkbox1, null);
        panel3.add(checkbox2, null);
        panel3.add(label1, null);
        panel3.add(textField1, null);
        panel3.add(button1, null);
        panel3.add(choice1, null);
        panel3.add(button2, null);
        panel3.add(label2, null);
        this.panel1.add(this.panelGo,BorderLayout.CENTER);  //将panelGo添加到BorderLayout布局的中间
        this.panel1.add(panel3, BorderLayout.EAST);  //将pane13添加到BorderLayout布局的东区域
        this.add(panel2, BorderLayout.SOUTH);   //将pane12添加到BorderLayout布局的南区域
        this.add(panel1, BorderLayout.CENTER);   //将pane13添加到BorderLayout布局的中间
        this.disableLink();                     //废掉控件
        this.checkboxGroup1.setSelectedCheckbox(this.checkbox1);   //如果复选框处于选中状态该方法返回true，否则返回false
        this.yellowPoint=new Point(1000,1000);  //初始化一个世纪外的黄点
        this.centerWindow();

        loginDialog(this);    //调用loginDialog方法传进参数this
    }
    /************用户登录界面************/
    private JDialog login;   //声明一个私有类变量
    private void loginDialog(final Frame frame) {

        login = new JDialog();
        login.setTitle("登录");    //设置标题
        login.setLayout(new FlowLayout());   //利用流布局来设置窗口的布局
        login.add(new JLabel("用户名:"));   //添加标签为用户名的对象
        final JTextField name = new JTextField(10);   //设置文本框中字符不得超过10个
        login.add(name);
        login.add(new JLabel("密    码:"));
        final JPasswordField password = new JPasswordField(10);   //设置密码框中字符不得多于10个
        password.setEchoChar('*');   //设置显示密码对应的字符为*
        login.add(password);
        JButton confirm = new JButton("登录");    //定义一个登录按钮
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (compareUserData(name.getText().trim(), new String(password.getPassword()))) {
                    login.setVisible(false);
                    frame.show();
                    myImage=frame.createImage(16,16);//用来纪录有黄点之前的图像
                } else {
                    JOptionPane.showMessageDialog(login, "用户名或密码错误！", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });   //获取监听器，并执行事件
        login.add(confirm);

        final JDialog regDialog = new JDialog(login, "注册", true);     //定义一个对象，该对象无法被继承
        registerDialog(regDialog);
        JButton register = new JButton("注册");    //设置注册的按钮
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regDialog.setVisible(true);
            }
        });    //获取监听器，执行regDialog为可见
        login.add(register);
        login.setSize(200, 130);
        login.setResizable(false);
        login.setLocationRelativeTo(null);
        login.setVisible(true);   //确保窗体里面的组件可见
    }

    private boolean compareUserData(String name, String password) {
        try {
            RandomAccessFile out = new RandomAccessFile("user.dat", "rw");
            String fname, fpassword = null;
            while (out.getFilePointer() < out.length()) {
                fname = out.readUTF();
                out.readUTF();
                fpassword = out.readUTF();
                if (fname.equals(name) && fpassword.equals(password)) return true;
            }
            out.close();   //可能发生异常的语句
        } catch (IOException e) {
            e.printStackTrace();
        }         //异常语句的处理
        return false;
    }

    private void registerDialog(final JDialog regDialog) {

        Box box1 = Box.createVerticalBox();
        box1.add(new JLabel("用户名:", JLabel.RIGHT));  //添加用户名标签，用户名在标签中靠右对齐
        box1.add(Box.createVerticalStrut(10));
        box1.add(new JLabel("性别:", JLabel.RIGHT)); //添加性别标签，性别在标签中靠右对齐
        box1.add(Box.createVerticalStrut(10));
        box1.add(new JLabel("密码:", JLabel.RIGHT), -1); //添加密码标签，密码在标签中靠右对齐
        box1.add(Box.createVerticalStrut(10));
        box1.add(new JLabel("确认密码:", JLabel.RIGHT)); //添加确认密码标签，确认密码在标签中靠右对齐

        Box box2 = Box.createVerticalBox();
        final JTextField nameTextField = new JTextField(10);  //设置用户名文本框字符不得超过10个，且文本框不能被继承
        box2.add(nameTextField);
        box2.add(Box.createVerticalStrut(8));
        final CheckboxGroup cbg = new CheckboxGroup();
        Box box21 = Box.createHorizontalBox();
        final Checkbox cb1 = new Checkbox("男", cbg, true);   //复选框默认状态选定为男性
        box21.add(cb1);
        box21.add(new Checkbox("女", cbg, false));      //当鼠标点击女性复选框时，flase自动变为true
        box2.add(box21);
        box2.add(Box.createVerticalStrut(8));
        //定义密码框中的字符长度小于10个
        final JPasswordField pass1 = new JPasswordField(10);
        box2.add(pass1);
        box2.add(Box.createVerticalStrut(8));
        final JPasswordField pass2 = new JPasswordField(10);
        box2.add(pass2);

        Box baseBox = Box.createHorizontalBox();
        baseBox.add(box1);
        baseBox.add(box2);

        regDialog.setLayout(new FlowLayout());
        regDialog.add(baseBox);
        JButton confirm = new JButton("确定");   //创建并声明确定按钮
        JButton cancel  = new JButton("取消");   //创建并声明取消按钮
        regDialog.add(confirm);
        regDialog.add(cancel);
        regDialog.setSize(200, 200);    //设置大小
        regDialog.setResizable(false);
        regDialog.setLocationRelativeTo(null);

        confirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                String sex = null;
                if (cbg.getSelectedCheckbox() == cb1)
                    sex = "男";
                else
                    sex = "女";

                if (saveUserData(nameTextField.getText().trim(), sex,
                        new String(pass1.getPassword()), new String(pass2.getPassword())))
                    regDialog.setVisible(false);
                else
                    JOptionPane.showMessageDialog(regDialog, "输入有误，请检查", "错误提示",
                            JOptionPane.ERROR_MESSAGE);
            }
        });        //获取监听器
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                regDialog.setVisible(false);
            }
        });    //获取监听器
    }

    private boolean saveUserData(String name, String sex, String password1, String password2) {
        if (!password1.equals(password2)) return false;

        try {
            RandomAccessFile out = new RandomAccessFile("user.dat", "rw");
            out.seek(out.length());
            out.writeUTF(name);
            out.writeUTF(sex);
            out.writeUTF(password1);
            out.close();                   //可能发生异常的语句
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }      //异常的处理
        return true;
    }
    void centerWindow()
    {
        //返回一个Dimension对象的引用，该对象实体中含有名字是width和height的成员变量。
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int pX=(d.width-this.getWidth())/2;
        int pY=(d.height-this.getHeight())/2;
        this.setLocation(pX,pY);
    }
    /**********主方法*************/
    public static void main(String args[])
    {
        MainWindow main=new MainWindow();
    }

    //监听线程
    public void run()
    {
        try
        {
            ServerSocket serverSocket=new ServerSocket(PORT);
            Socket receiveSocket=null;
            receiveSocket=serverSocket.accept();
            if(this.isInitiative)                       //如果已在进行，则不接受连接
                this.stopFlag=true;
            this.checkboxGroup1.setSelectedCheckbox(this.checkbox2);     //自动选择联机
            this.button1.setEnabled(false);
            this.choice1.setEnabled(true);
            this.textField1.setEnabled(false);
            this.checkbox1.setEnabled(false);
            this.checkbox2.setEnabled(false);
            this.writer=new PrintWriter(receiveSocket.getOutputStream(),true);
            BufferedReader reader=new BufferedReader(new InputStreamReader(receiveSocket.getInputStream()));
            String message;
            while(!this.stopFlag)
            {
                this.panelGo.showError("接收连接成功");
                message=reader.readLine();   //按行读取文件中的内容
                this.doMessage(message);

            }
            reader.close();
            receiveSocket.close();
            serverSocket.close();
        }catch(IOException ioe){this.panelGo.showError("意外中断");}

    }
    //处理接收到的信息
    void doMessage(String message)
    {
        if(message.startsWith("start"))//判断开始
        {
            this.panelGo.showError("对方已开始");
            if(message.equals("start_black"))
                this.choice1.select("白");
            else
                this.choice1.select("黑");
            if(this.choice1.getSelectedItem().equals("黑"))//只要你是黑的，就先走
                this.panelGo.setEnabled(true);
            this.paintMyColor();//表明颜色
            this.disableLink();
        }
        else//下子的信息
        {
            int color=Integer.parseInt(message.substring(0,1));
            this.messagePoint.x=Integer.parseInt(message.substring(1,3));
            this.messagePoint.y=Integer.parseInt(message.substring(3,5));
            this.panelGo.setEnabled(true);//解禁
            this.panelGo.doStep(this.messagePoint,color);
        }


    }

    //为鼠标定位
    void panelGo_mouseMoved(MouseEvent e)
    {
        Point realPoint=e.getPoint();
        Point mousePoint=this.panelGo.getMousePoint(realPoint,this.goStartPoint);
        this.removeLastMousePoint(this.LastPoint,mousePoint);
        this.LastPoint.x=mousePoint.x;
        this.LastPoint.y=mousePoint.y;
        if(this.isPlace(mousePoint))
            this.showMousePoint(mousePoint);


    }
    //加黄点的范围
    boolean isPlace(Point p)
    {
        if(p.x>19||p.x<1||p.y<1||p.y>19)
            return false;
        int color;
        One one;
        one=(One)(this.panelGo.myHash.get(p));
        color=one.color;
        if(color!=0)
            return false;
        return true;


    }
    void panelGo_mouseClicked(MouseEvent e)
    {
        if(this.isSingle())
        {
            this.doSingle();
        }
        else
        {
            this.doMultiple();
        }
    }
    //开始
    void button2_actionPerformed(ActionEvent e)
    {

        if(e.getActionCommand().equals("开始"))
        {
            this.disableLink();
            this.checkbox1.setEnabled(false);
            this.checkbox2.setEnabled(false);
            this.button2.setLabel("退出");
            if(this.isSingle())
                this.panelGo.setEnabled(true);
            else//联机版时
            {
                if(this.choice1.getSelectedItem().equals("黑"))
                {
                    this.writer.println("start_black");

                }
                else
                    this.writer.println("start_white");
            }
            this.paintMyColor();//表明颜色
        }
        else if(e.getActionCommand().equals("退出"))
        {
            this.dispose();
            System.exit(0);
        }
    }
    //disable联机时用的控件
    void disableLink()
    {
        this.textField1.setBackground(new Color(236, 190, 120));
        this.textField1.setEnabled(false);
        this.choice1.setEnabled(false);
        this.button1.setEnabled(false);
    }
    //enable联机时的控件
    void enableLink()
    {
        this.textField1.setBackground(Color.white);
        this.textField1.setEnabled(true);
        this.choice1.setEnabled(true);
        this.button1.setEnabled(true);
    }
    //判断类型
    boolean isSingle()
    {
        return this.checkbox1.getState();
    }

    void single()
    {

    }

    void multiple()
    {
    }

    //加小黄点
    void showMousePoint(Point mousePoint)
    {
        Graphics g=this.panelGo.getGraphics();
        g.setColor(Color.yellow);
        g.fillOval(mousePoint.x*20-6,mousePoint.y*20-6,this.panelGo.INTERVAL-8,this.panelGo.INTERVAL-8);
        this.yellowPoint.x=mousePoint.x;//定位黄点
        this.yellowPoint.y=mousePoint.y;

        Graphics myG=this.myImage.getGraphics();
        this.createMyImage(myG,this.yellowPoint,0);
    }
    //消除前一个黄点
    void removeLastMousePoint(Point thatPoint,Point thisPoint)
    {
        if(thatPoint.x!=thisPoint.x||thatPoint.y!=thisPoint.y)
        {
            Graphics g=this.panelGo.getGraphics();
            if(this.yellowPoint!=null&&this.myImage!=null)
                g.drawImage(this.myImage,this.yellowPoint.x*20-8,this.yellowPoint.y*20-8,16,16,null);
            this.yellowPoint.x=1000;//不在范围内，就一边去
            this.yellowPoint.y=1000;

        }
    }

    //构成所需要的Image
    void createMyImage(Graphics g,Point thisPoint,int color)
    {
        int px=thisPoint.x;
        int py=thisPoint.y;
        Color myColor=this.panelGo.getBackground();  //设置背景色
        //设置棋盘线
        if(px==1&&py==1&&color==0)//四个角
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,16);
            g.drawLine(5,5,5,16);
            g.drawLine(8,8,16,8);
            g.drawLine(5,5,16,5);
        }
        else if(px==1&&py==19&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,0);
            g.drawLine(8,8,16,8);
            g.drawLine(5,11,16,11);
            g.drawLine(5,11,5,0);
        }
        else if(px==19&&py==1&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,16);
            g.drawLine(8,8,0,8);
            g.drawLine(11,5,11,16);
            g.drawLine(11,5,0,5);
        }
        else if(px==19&&py==19&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,0);
            g.drawLine(8,8,0,8);
            g.drawLine(11,11,11,0);
            g.drawLine(11,11,0,11);
        }
        else if(px==1&&color==0)//四个边
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,16,8);
            g.drawLine(8,0,8,16);
            g.drawLine(5,0,5,16);
        }
        else if(px==19&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,0,8);
            g.drawLine(8,0,8,16);
            g.drawLine(11,0,11,16);
        }
        else if(py==1&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,16);
            g.drawLine(0,8,16,8);
            g.drawLine(0,5,16,5);
        }
        else if(py==19&&color==0)
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(8,8,8,0);
            g.drawLine(0,8,16,8);
            g.drawLine(0,11,16,11);
        }
        //八个小黑点
        else
        if(color==0&&((px==4&&py==4)||(px==4&&py==10)||(px==4&&py==16)||(px==10&&py==4)||(px==10&&py==10)||(px==10&&py==16)||(px==16&&py==4)||(px==16&&py==10)||(px==16&&py==16)))
        {
            g.setColor(myColor);
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(0,8,16,8);
            g.drawLine(8,0,8,16);
            g.fillOval(5,5,6,6);
        }

        else if(color==0)
        {
            g.setColor(myColor);//
            g.fillRect(0,0,16,16);
            g.setColor(Color.black);
            g.drawLine(0,8,16,8);
            g.drawLine(8,0,8,16);
        }



    }

    //单机版走步
    void doSingle()
    {

        if(this.stepColor)
            this.panelGo.doStep(this.yellowPoint,1);
        else
            this.panelGo.doStep(this.yellowPoint,2);
        if(!this.panelGo.errorFlag)//如果不违例，则换颜色
        {
            this.stepColor=!this.stepColor;
            this.paintThisColor(this.stepColor);
        }
        else
            this.panelGo.errorFlag=false;
        this.yellowPoint.x=1000;//刚走的子不至于删掉
        this.yellowPoint.y=1000;

    }
    //联机版走步
    void doMultiple()
    {
        int color;
        if(this.choice1.getSelectedItem().equals("黑"))
            color=1;
        else
            color=2;

        this.panelGo.doStep(this.yellowPoint,color);
        //如果走法不对，返回
        if(this.panelGo.errorFlag)
        {
            this.panelGo.errorFlag=false;
            return;
        }
        this.panelGo.setEnabled(false);
        String message=this.getMessage(color,this.yellowPoint.x,this.yellowPoint.y);
        this.writer.println(message);
        this.yellowPoint.x=99;//刚走的子不至于删掉，还要可以解析
        this.yellowPoint.y=99;
    }
    //处理发送字符串
    String getMessage(int color,int x,int y)
    {
        String strColor=String.valueOf(color);
        String strX;
        String strY;
        //如果单数的话，就加一位
        if(x<10)
            strX="0"+String.valueOf(x);
        else
            strX=String.valueOf(x);

        if(y<10)
            strY="0"+String.valueOf(y);
        else
            strY=String.valueOf(y);

        return strColor+strX+strY;
    }

    void this_windowClosing(WindowEvent e)
    {
        this.dispose();
        System.exit(0);
    }

    void checkbox2_mouseClicked(MouseEvent e)
    {
        this.enableLink();
    }

    void checkbox1_mouseClicked(MouseEvent e)
    {
        this.disableLink();
    }

    void button1_actionPerformed(ActionEvent e)
    {
        this.goToLink(this.textField1.getText().trim(),this.PORT);
    }
    //主动连接serverSocket
    void goToLink(String hostName,int port)
    {
        try
        {
            this.stopFlag=true;
            this.sendSocket=new Socket(hostName,port);
            this.panelGo.showError("连接成功！！");
            this.choice1.setEnabled(true);
            this.button1.setEnabled(false);
            this.checkbox1.setEnabled(false);
            this.checkbox2.setEnabled(false);
            this.textField1.setEnabled(false);
            this.writer=new PrintWriter(this.sendSocket.getOutputStream(),true);
            new Listen(sendSocket,this).start();
        }
        catch(IOException ioe){
            this.panelGo.showError("意外中断");
        }
    }

    //开始时根据颜色画己方颜色
    void paintMyColor()
    {
        Graphics g=this.label2.getGraphics();
        if(this.choice1.getSelectedItem().equals("黑"))
            g.fillOval(20,10,30,30);
        else
        {
            g.setColor(Color.white);
            g.fillOval(20,10,30,30);
            g.setColor(Color.black);
            g.drawOval(20,10,30,30);
        }
    }
    //单机版画每步颜色
    void paintThisColor(boolean whatColor)
    {
        Graphics g=this.label2.getGraphics();
        if(whatColor)
            g.fillOval(20,10,30,30);
        else
        {
            g.setColor(Color.white);
            g.fillOval(20,10,30,30);
            g.setColor(Color.black);
            g.drawOval(20,10,30,30);
        }
    }
}