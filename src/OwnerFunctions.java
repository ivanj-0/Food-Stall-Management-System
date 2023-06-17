import java.util.Scanner;

public class OwnerFunctions {
    public void order() {
        Scanner in = new Scanner(System.in);
            try {
                System.out.println("Enter redi name (Shankar, Meera, CVR, SR):");
                String redi = in.next();
                RediID.setID(redi);
                redi = redi.toLowerCase();
                switch (redi) {
                    case "shankar":
                        Shankar s = new Shankar("owner", "shankar");
                        ShankarCaller call1 = new ShankarCaller(s);
                        call1.t.join();
                        break;
                    case "meera":
                        Meera m = new Meera("owner", "meera");
                        MeeraCaller call2 = new MeeraCaller(m);
                        call2.t.join();
                        break;
                    case "cvr":
                        Cvr c = new Cvr("owner", "cvr");
                        CvrCaller call3 = new CvrCaller(c);
                        call3.t.join();
                        break;
                    case "sr":
                        Sr r = new Sr("owner", "sr");
                        SrCaller call4 = new SrCaller(r);
                        call4.t.join();
                        break;
                    default:
                        System.out.println("Wrong");


                }
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
             System.exit(0);
    }
}