package Test;

public class Prime {
    public static void main(String[] args) {
        Integer RANGE =100;
        Run run =new Run(RANGE);
    }
}


class Run{


    public Run(Integer INPUT_RANGE) {
        out(INPUT_RANGE);
    }
    Integer out=1;

    public void out(Integer in){
        boolean[] booleanlist =new boolean[in];

        for (int i = 2; i <=in ; i++) {
            for (int k = 2; k <=in ; k++) {
                if (i*k>=in)break;





                booleanlist[i*k]=true;
            }
        }
        for (boolean flag:booleanlist
             ) {
            if (!flag){
                System.out.println(out);
            }
            out++;
        }
    }
}