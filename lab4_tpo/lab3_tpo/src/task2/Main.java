package task2;

public class Main {
    public static final int BUFFER_SIZE = 5000;

    public static void main(String[] args) {
        Drop drop = new Drop(BUFFER_SIZE);
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}