import java.io.IOException;

public class ShankarCaller implements Runnable{
    Shankar target;
    Thread t;
    public ShankarCaller(Shankar target){
        this.target=target;
        t=new Thread(this);
        t.start();
    }
    public void run(){
        try {
            System.out.println("Function being called");
            target.func();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }


