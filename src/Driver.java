import java.util.Scanner;

public class Driver {
    public static int user_flag;
    public static void main(String args[]){
        //calls Welcome GUI
            java.awt.EventQueue.invokeLater(new Runnable() {public void run() {new GUI_Welcome().setVisible(true);}});
    }
}