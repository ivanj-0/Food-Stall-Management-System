import java.util.Scanner;

public class StudentFunctions {
    public void order() {
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Enter redi name (Shankar, Meera, CVR, SR):");
            String redi = in.next().toLowerCase();
            RediID.setID(redi);
            switch (redi) {
                case "shankar":
                    System.out.println("Shankar case entered");
                    Shankar s = new Shankar("student", "shankar");
                    ShankarCaller call1 = new ShankarCaller(s);
                    call1.t.join();
                    break;
                case "meera":
                    Meera m = new Meera("student", "meera");
                    MeeraCaller call2 = new MeeraCaller(m);
                    call2.t.join();
                    break;
                case "cvr":
                    Cvr c = new Cvr("student", "cvr");
                    CvrCaller call3 = new CvrCaller(c);
                    call3.t.join();
                    break;
                case "sr":
                    Sr r = new Sr("student", "sr");
                    SrCaller call4 = new SrCaller(r);
                    call4.t.join();
                    break;
                default:
                    System.out.println("Wrong");
            }


        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
        }
    }
}
