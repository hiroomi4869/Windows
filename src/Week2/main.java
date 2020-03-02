package Week2;

import java.util.ArrayList;
import java.util.List;

/**Remember to initialize the number.
 * @author Hiroomi
 * @version 1.0
 */

public class main {
    public static void main(String[] args) {
        Integer LIMIT = 200;
        Integer LEFT_INPUT = 1;
        Integer RIGHT_INPUT = 3;
        Integer LEFT_NUM = 2;
        Integer i;
        var list =new ArrayList<String>();
        while (RIGHT_INPUT<=LIMIT) {

            if (LEFT_INPUT+LEFT_NUM-1>=RIGHT_INPUT){
                LEFT_INPUT = 1;
                LEFT_NUM++;
            }

            if (LEFT_NUM>RIGHT_INPUT){
                LEFT_NUM = 2;
                RIGHT_INPUT++;
            }

            while (true) {
                {
                    Left left = new Left(LEFT_NUM, LEFT_INPUT);
                    Right right = new Right(RIGHT_INPUT);
                    if (left.getOut().equals(right.getOut())) {
                        String x = left.toString() + "=" + right.toString();
                        System.out.println(x);
                        list.add(x);
                    }
                }

                if (LEFT_INPUT + LEFT_NUM - 1 >= RIGHT_INPUT){
                    break;
                }
                LEFT_INPUT++;
            }
        }

    }

}
    class Left{
        Integer num;
        Integer out=0;
        Integer in;
        Integer i=0;

        public Left(Integer num, Integer in) {
            this.num = num;
            this.in = in;
            for (int i = in; i <=num+in-1 ; i++) {
                out=out+i*i*i;
            }
        }
        public Integer getOut() {
            return out;
        }

        @Override
        public String toString() {
            return in+"^3"+"+"+(in+1)+"^3"+"+...+"+(in+num-1)+"^3";
        }
    }

    class Right{

        Integer in;
        Integer out=0;

        public Right(Integer num) {
            this.in = num;
            out=num*num*num;
        }

        public Integer getOut() {
            return out;
        }

        @Override
        public String toString() {
            return in+"^3";
        }
    }

