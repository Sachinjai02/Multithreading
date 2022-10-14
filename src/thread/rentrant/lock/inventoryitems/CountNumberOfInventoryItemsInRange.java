package thread.rentrant.lock.inventoryitems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountNumberOfInventoryItemsInRange {

    private static int MAXIMUM_PRICE = 1000;
    private static Random random =  new Random();
    public static void main(String[] args) throws InterruptedException {
        //Writer thread to populate the tree
        InventoryDatabase database = new InventoryDatabase();
        for(int i=0;i<100000;i++) {
            database.addItem(random.nextInt(MAXIMUM_PRICE));
        }

        Thread writer = new Thread ( () -> {
            while(true) {
                database.addItem(random.nextInt(MAXIMUM_PRICE));
                database.removeItem(random.nextInt(MAXIMUM_PRICE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        //have many threads reading the priced range inventory items
        List<Thread> threads = new ArrayList<>();

        for(int i=0;i<7;++i) {
            threads.add(new Thread(() -> {
                for(int j=0;j<100000;++j) {
                      Integer upper = random.nextInt(MAXIMUM_PRICE);
                      Integer lower = upper > 0 ? random.nextInt(upper) : 0;
                      database.getNumberOfItemsInPriceRange(lower, upper);
                }
            }));
        }
        Long start = System.currentTimeMillis();
        for(Thread reader: threads) {
            reader.start();
        }

        for(Thread reader: threads) {
            reader.join();
        }

        Long end = System.currentTimeMillis();
        System.out.println("Time take to finish all reader threads :"  + (end-start));

    }
}
