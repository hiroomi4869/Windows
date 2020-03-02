package Go;
/***********************Listen******************************/
import java.io.*;
import java.net.*;

public class Listen extends Thread                      //继承
{
    Socket socket;
    MainWindow mainWindow;                              //主窗口
    public Listen(Socket socket,MainWindow mainWindow)  //监听
    {
        this.socket=socket;                             //使用this区分成员变量和局部变量
        this.mainWindow=mainWindow;                     //使用this区分成员变量和局部变量
    }
    public void run()                                   //运行
    {
        try
        {
            this.activeListen(this.socket);
        }
        catch(IOException ioe){                         //异常处理
            this.mainWindow.panelGo.showError("意外中断");
        }
    }
    void activeListen(Socket socket) throws IOException   //活动监听者
    {
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //缓冲流
        String message;                                   //键入通知
        while(true)
        {
            message=reader.readLine();                    //读入信息
            this.mainWindow.doMessage(message);           //在主窗口中执行读取的信息
        }
    }
}