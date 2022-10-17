package thread.race.condition.non.atomic;

public class InventoryCounter {

    private int items = 0;
    private Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter ic = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(ic);
        DecrementingThread decrementingThread = new DecrementingThread((ic));
        long start = System.currentTimeMillis();
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        long end = System.currentTimeMillis();
        System.out.println("Number of items left in inventory " + ic.items);
        System.out.println("Took time = " + (end-start));
    }
    static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        public IncrementingThread(InventoryCounter ic) {
            this.inventoryCounter = ic;
        }

        @Override
        public void run() {
            for(int i=0;i<100000;++i) {
                this.inventoryCounter.increment();
            }
        }
    }

    static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        public DecrementingThread(InventoryCounter ic) {
            this.inventoryCounter = ic;
        }

        @Override
        public void run() {
            for(int i=0;i<100000;++i) {
                this.inventoryCounter.decrement();
            }
        }
    }

    private void increment() {
        synchronized (this.lock) {
            ++this.items;
        }
    }
    private void decrement() {
        synchronized (this.lock) {
            --this.items;
        }
    }
}
