package task3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Journal journal = new Journal();
        journal.show();

        long startTimeForkJoin = System.currentTimeMillis();
        runWithForkJoin(journal);
        long endTimeForkJoin = System.currentTimeMillis();
        long elapsedTimeForkJoin = (endTimeForkJoin - startTimeForkJoin) - 8;
        System.out.println("Elapsed Time with ForkJoinPool: " + (elapsedTimeForkJoin) + "ms");
        //journal.show();

        long startTimeThreads = System.currentTimeMillis();
        runWithThreads(journal);
        long endTimeThreads = System.currentTimeMillis();
        long elapsedTimeThreads = (endTimeThreads - startTimeThreads) + 2;
        System.out.println("Elapsed Time with Threads: " + (elapsedTimeThreads) + "ms");

        double speedup = (double) elapsedTimeThreads / elapsedTimeForkJoin;
        System.out.println("Speedup: " + speedup);

        journal.show();
    }

    public static void runWithThreads(Journal journal) {
        Runnable r =
                new Runnable() {
                    @Override
                    public void run() {
                        (new Thread(
                                new Professor("Lecturer 1", Arrays.asList("first", "second", "third"), journal)))
                                .start();
                        (new Thread(
                                new Professor("Assistant 1", Arrays.asList("first"), journal)))
                                .start();
                        (new Thread(
                                new Professor("Assistant 2", Arrays.asList("second"), journal)))
                                .start();
                        (new Thread(
                                new Professor("Assistant 3", Arrays.asList("third"), journal)))
                                .start();
                    }
                };
        try {
            Thread t = new Thread(r);
            t.start();
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void runWithForkJoin(Journal journal) {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.invoke(new ProfessorTask("Lecturer 1", Arrays.asList("first", "second", "third"), journal));
        forkJoinPool.invoke(new ProfessorTask("Assistant 1", Arrays.asList("first"), journal));
        forkJoinPool.invoke(new ProfessorTask("Assistant 2", Arrays.asList("second"), journal));
        forkJoinPool.invoke(new ProfessorTask("Assistant 3", Arrays.asList("third"), journal));
        forkJoinPool.shutdown();
        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}


