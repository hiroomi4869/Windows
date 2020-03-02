package Go; /***********************Go*************************************/
import java.awt.*;
import java.util.*;//引入包

public class Go extends Panel
{
    int whichStep;
    Hashtable myHash;
    Point pointNow;             //当前的点
    Point STARTPOINT;           //起始点
    int INTERVAL;               //等高距
    Vector vec;                 //定义矢量
    Point robPoint;             //打劫点 
    Point mousePoint;           //鼠标点
    boolean errorFlag;          //行棋错误标志

    //构建器
    public Go()
    {
        super();                                  //super函数，方便子类调用父类里面的成员变量与构造方法
        pointNow=new Point(1000,1000);            //把初始红点画在外面
        errorFlag=false;                          //行棋错误标志
        whichStep=0;
        STARTPOINT=new Point(20,20);              //新的点所在的位置
        INTERVAL=20;                              //棋盘的间隔
        myHash=new Hashtable();                   //创建对象
        robPoint=null;                            //打劫点
        mousePoint=new Point();                   //开辟鼠标点内存
        vec=new Vector();                         //存放校验的子
        this.initMyHash(STARTPOINT,INTERVAL);
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }                                          //异常处理

    }
    //初始化hashtable
    void initMyHash(Point startPoint,int interval)
    {
        One one;
        Point key;                                //逻辑点标志
        int i,j;                                  //定义i,j
        for(i=1;i<=19;i++)
            for(j=1;j<=19;j++)                    //循环
            {
                key=new Point(i,j);
                one=new One();                    //创建对象
                one.posX=startPoint.x+(i-1)*interval;//相邻点处理，获取
                one.posY=startPoint.y+(j-1)*interval;//相邻点处理。获取//获取相邻点

                one.pointAround[0]=new Point(i,j-1);      //上
                one.pointAround[1]=new Point(i,j+1);      //下
                one.pointAround[2]=new Point(i-1,j);      //左
                one.pointAround[3]=new Point(i+1,j);      //右
                if(i==1)one.pointAround[2]=one.OUT;       //左的周围
                if(i==19)one.pointAround[3]=one.OUT;      //右的周围
                if(j==1)one.pointAround[0]=one.OUT;       //上的周围
                if(j==19)one.pointAround[1]=one.OUT;      //下的周围

                myHash.put(key,one);                      //我的我的哈希表并且在里键入关键字
            }
    }


    //更新盘面
    public void paint(Graphics g)
    {
        Point startPoint=STARTPOINT;                     //创建对象，开始点
        int interval=INTERVAL;                           //定义成员变量
        this.paintChessboard(g,startPoint,interval);     //棋盘的操作
        this.paintChessman(g,startPoint,interval);       //棋子的操作
    }
    //画棋盘
    void paintChessboard(Graphics g,Point startPoint,int interval)
    {
        int pX=startPoint.x;                           //定义相邻点X
        int pY=startPoint.y;                           //相邻点Y
        int LINELENGTH=interval*18;                    //定义路线长度
        int i;
        for(i=0;i<19;i++)
        {
            g.drawLine(pX+i*interval,pY,pX+i*interval,pY+LINELENGTH);
            g.drawLine(pX,pY+i*interval,pX+LINELENGTH,pY+i*interval);
        }//for循环，以便构造19*19的棋盘
        g.fillOval(pX+interval*3-3,pY+interval*3-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*9-3,pY+interval*3-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*15-3,pY+interval*3-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*3-3,pY+interval*9-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*9-3,pY+interval*9-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*15-3,pY+interval*9-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*3-3,pY+interval*15-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*9-3,pY+interval*15-3,(int)(interval-14),(int)(interval-14));
        g.fillOval(pX+interval*15-3,pY+interval*15-3,(int)(interval-14),(int)(interval-14));
        //路线长度和间隔的确定

        g.drawRect(pX-3,pY-3,366,366);//棋盘大小
    }
    //加棋子
    void paintChessman(Graphics g,Point startPoint,int interval)
    {//加入棋子，并且是按照一黑一白
        int pX=startPoint.x;                                 //相邻点X
        int pY=startPoint.y;                                 //相邻点Y
        Enumeration enun=myHash.elements();                  //创建对象
        while(enun.hasMoreElements())
        {
            One one=(One)enun.nextElement();
            if(one.color!=one.BLANK)
            {
                if(one.color==one.BLACK)
                    g.setColor(Color.black);
                else if(one.color==one.WHITE)
                    g.setColor(Color.white);
                else
                    break;                                   //如果盘里加满了棋子，则跳出循环
                g.fillOval(one.posX-8,one.posY-8,interval-4,interval-4);
                //路线长度
                g.setColor(Color.black);                     //获取颜色
                g.drawOval(one.posX-8,one.posY-8,interval-4,interval-4);
                //画椭圆
            }
        }
        g.setColor(Color.red);                               //画红点
        g.fillOval(this.pointNow.x*20-5,this.pointNow.y*20-5,10,10);
        //路线间隔
    }
    //处理每一步
    void doStep(Point whatPoint,int whatColor)
    {
        //如果点在盘外，返回
        if(whatPoint.x<1||whatPoint.x>19||whatPoint.y<1||whatPoint.y>19)
        {
            this.showError("不能下在此处");
            this.errorFlag=true;                                 //在棋盘外，出错
            return;
        }
        //如果点上有子，则返回
        if(((One)myHash.get(whatPoint)).color!=0)
        {
            this.showError("此处已有子");
            this.errorFlag=true;                                //此点已经有棋子了，不能下
            return;
        }
        //如果已经开劫了，则应先应劫，否则返回
        if(this.isRob(whatPoint))
        {
            this.showError("已经开劫，请先应劫");
            this.errorFlag=true;
            return;
        }

        this.updateHash(whatPoint,whatColor);                    //我的棋子所在的地方
        this.getRival(whatPoint,whatColor);                 //对手的棋子所在的地方
        //如果没有气也没有己类
        if(!this.isLink(whatPoint,whatColor)&&!this.isLink(whatPoint,0))
        //0相当于ｏｎｅ．ＢＬＡＮＫ
        {
            this.showError("此处不可放子");
            this.errorFlag=true;
            this.singleRemove(whatPoint);                       //棋子所在的地方
            return;//返回
        }
        this.pointNow.x=whatPoint.x;
        this.pointNow.y=whatPoint.y;                            //得到当前红点
        this.repaint();                                         //返回到当前的点

    }

    //取异类并判断执行吃子
    void getRival(Point whatPoint,int whatColor)
    {
        boolean removeFlag=false;                             //判断这一步到底吃没吃子
        One one;
        one=(One)(this.myHash.get(whatPoint));                //确定该步的位置
        Point otherPoint[]=one.pointAround;                   //该步周围的棋子
        int i;
        for(i=0;i<4;i++)
        {
            One otherOne=(One)(this.myHash.get(otherPoint[i]));//举出异类实例
            if(!otherPoint[i].equals(one.OUT))
                if(otherOne.color!=one.BLANK&&otherOne.color!=whatColor)
                {
                    if(this.isRemove(otherPoint[i]))          //如果有气
                        this.vec.clear();                     //不吃子
                    else
                    {
                        this.makeRobber(otherPoint[i]);
                        this.doRemove();                  //进行吃子行为
                        this.vec.clear();                 //改点被清空
                        removeFlag=true;                  //返回
                    }
                }
        }
        if(!removeFlag)
            this.robPoint=null;                                //如果没吃子的话消掉打劫点

    }
    //判断是否因打劫不能下
    boolean isRob(Point p)
    {
        if(this.robPoint==null)                                //打劫点不存在
            return false;                                      //返回错误
        if(this.robPoint.x==p.x&&this.robPoint.y==p.y)         //打劫点存在进行判断
            return true;                                       //返回正确
        return false;                                          //返回错误
    }
    //建立打劫点
    void makeRobber(Point point)
    {
        if(this.vec.size()==1)
            this.robPoint=point;                           //建立新打劫点
        else
            this.robPoint=null;                                //吃多个的话消掉打劫点
    }
    //判断吃子
    boolean isRemove(Point point)
    {
        if(this.vec.contains(point))
            return false;
        if(this.isLink(point,0))                                //有气的话
            return true;
        this.vec.add(point);                                    //没有气就加入这个点
        One one;
        one=(One)(this.myHash.get(point));                      //创建新的点
        Point otherPoint[]=one.pointAround;                     //新的点周围的棋子
        int i;
        for(i=0;i<4;i++)
        {
            One otherOne=(One)(this.myHash.get(otherPoint[i]));//举出同类实例
            if(!otherPoint[i].equals(one.OUT))                 //如果周围的点不等同于输入点的颜色
                if(otherOne.color==one.color)                  //如果周围的点是颜色相同
                    if(this.isRemove(otherPoint[i]))       //这里递归，移除
                        return true;
        }
        return false;

    }
    //执行消子
    void doRemove()                                            //执行消棋子的步骤
    {
        Enumeration enun=this.vec.elements();                  //列出各种情况
        while(enun.hasMoreElements())
        {
            Point point=(Point)enun.nextElement();             //指向下一个元素
            this.singleRemove(point);                          //单独移除的点
        }
    }
    //消单个子
    void singleRemove(Point point)
    {
        One one=(One)(this.myHash.get(point));
        one.isthere=false;
        one.color=one.BLANK;
        Graphics g=this.getGraphics();
        g.clearRect(point.x*20-8,point.y*20-8,20,20);         //删除画面上的子
    }

    //判断有气
    boolean isLink(Point point,int color)                     //进行判断是否有气
    {
        One one;
        one=(One)(this.myHash.get(point));                   //新输入的点
        Point otherPoint[]=one.pointAround;                  //周围的点
        int i;
        for(i=0;i<4;i++)
        {
            One otherOne=(One)(this.myHash.get(otherPoint[i]));//举出同类实例
            if(!otherPoint[i].equals(one.OUT))                //如果周围的点不等同于输入的点的颜色
                if(otherOne.color==color)                     //如果周围的点颜色相同
                {
                    return true;                              //返回TRUE
                }
        }
        return false;                                          //如果不满足，返回错误

    }
    //每一步更新myHash
    void updateHash(Point whatPoint,int whatColor)            //更新操作
    {
        One one=(One)(this.myHash.get(whatPoint));            //创建myHash
        one.isthere=true;                                     //所下的点符合
        one.color=whatColor;                                  //确定颜色
        this.whichStep=this.whichStep+1;                      //走步增加1
        one.whichStep=this.whichStep;                         //确定走步
    }


    Point getMousePoint(Point p1,Point p2)                    //p1为真实点，p2为相对原点
    {
        this.mousePoint.x=Math.round((float)(p1.x-p2.x)/this.INTERVAL);
        //用四舍五入计算逻辑点位置
        this.mousePoint.y=Math.round((float)(p1.y-p2.y)/this.INTERVAL);
        //用四舍五入计算逻辑点位置
        return this.mousePoint;                               //返回该位置
    }
    //显示错误信息
    void showError(String errorMessage)
    {
        Graphics g=this.getGraphics();                       //创建图g
        g.setColor(new Color(236,190,98));                   //定义错误信息的颜色
        g.fillRect(20,400,30,200);                           //路线长度
        g.setColor(Color.red);                               //画红点
        g.drawString(errorMessage,60,415);                   //错误信息
        g.fillOval(20,400,20,20);                            //路线间隔

    }
    private void jbInit() throws Exception                   //扔出错误
    {
        this.setBackground(new Color(236, 190, 120));        //背景色
    }
}