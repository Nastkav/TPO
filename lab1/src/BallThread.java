public class BallThread extends Thread {
    private Ball b;

    private int priority;

    public BallThread(Ball ball, int priority){

        b = ball;
        this.priority = priority;
        this.setPriority(priority); // встановлення пріоритету потоку
    }

    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);


            }
        } catch(InterruptedException ex){

        }
    }
}

