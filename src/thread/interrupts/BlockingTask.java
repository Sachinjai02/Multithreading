package thread.interrupts;

public class BlockingTask implements Runnable {
    private final long sleep;

    BlockingTask(long sleep) {
        this.sleep = sleep;
    }
    @Override
    public void run() {
        try {
            System.out.println("Sleeping for " + this.sleep + " millis");
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("Exiting blocking task thread");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingTask(50000000));
        thread.start();
        System.out.println("Going to interrupt the blocking thread");
        thread.interrupt();
    }
}
