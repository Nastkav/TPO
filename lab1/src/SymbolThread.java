public class SymbolThread extends Thread{


    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                if (isDash) {
                    printer.printDash();
                } else {
                    printer.printLine();
                }
                Thread.sleep(10);
            }
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted: " + ex.getMessage());
        }
    }
}

