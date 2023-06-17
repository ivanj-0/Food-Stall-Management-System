import java.io.IOException;

public class CvrCaller implements Runnable{
    Cvr target;
    Thread t;
    public CvrCaller(Cvr target){
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

