package task2;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        try {
            for (int i = 0; i < Main.BUFFER_SIZE; i++) {
                int item = drop.take();
                System.out.format("Item received: %d%n", item);
                Thread.sleep(random.nextInt(500));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
