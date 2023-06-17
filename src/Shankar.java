import java.io.IOException;
import java.util.Scanner;

class Shankar {
    private String identity;
    private String redi;

    public String getIdentity() {
        return identity;
    }

    public String getRedi() {
        return redi;
    }

    public Shankar(String identity, String redi) {
        this.identity = identity;
        this.redi = redi;
    }
    synchronized void func() throws IOException {
        Scanner in = new Scanner(System.in);
        int choice;
        if (identity.equals("student")) {
            System.out.println("creating Student Order object");
            StudentOrder so = new StudentOrder("Shankar");
            so.order();
        }
        if (identity.equals("owner")) {
            System.out.println("1: Edit Menu 2: Take Orders");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    EditMenu m = new EditMenu();
                    m.edit();
                    break;
                case 2:
                    EditStatus es = new EditStatus();
                    es.editOption();
                    break;
                default:
                    System.out.println("Wrong choice");

            }
        }

    }
}

