import java.io.IOException;

public class SrCaller implements Runnable{
    Sr target;
    Thread t;
    public SrCaller(Sr target){
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

