package task2;
import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < Main.BUFFER_SIZE; i++) {
                int item = random.nextInt(100);
                drop.put(item);
                System.out.format("Item produced: %d%n", item);
                 Thread.sleep(random.nextInt(500));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
