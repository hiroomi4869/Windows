package Go; /*************************One************************/
import java.awt.*;
public class One
{
    int BLACK=1;//定义黑子等于1
    int WHITE=2;//定义白子为2
    int BLANK=0;
    Point OUT=new Point(-1,-1);

    int whichStep;
    int color=BLANK;       //定义颜色
    boolean isthere=false;//此处为错误的
    Point pointAround[]={OUT,OUT,OUT,OUT};
    int posX;             //定义相邻点X
    int posY;             //定义相邻点Y

}