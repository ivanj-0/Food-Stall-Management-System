import java.io.IOException;

public class MeeraCaller implements Runnable{
    Meera target;
    Thread t;
    public MeeraCaller(Meera target){
        this.target=target;
        t=new Thread(this);
        t.start();
    }
    public void run(){
        try {
            target.func();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

