package thread.rentrant.lock.readwritelock.inventoryitems;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryDatabase {

    private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
        readLock.lock();
        try {
            Integer lower = priceToCountMap.ceilingKey(lowerBound);
            Integer upper = priceToCountMap.floorKey(upperBound);

            if(lower == null || upper == null) return 0;

            SortedMap<Integer, Integer> priceRangeMap = priceToCountMap.subMap(lower, upper);
            final int[] sum = new int[1];
            priceRangeMap.values().stream().forEach( i -> sum[0] += i);
            return sum[0];
        } finally {
            readLock.unlock();
        }
    }

    public void addItem(int price) {
        writeLock.lock();
        try {
            Integer items = priceToCountMap.getOrDefault(price,0);
            priceToCountMap.put(price, items+1);
        } finally {
            writeLock.unlock();
        }

    }

    public void removeItem (int price) {
        writeLock.lock();
        try {
            Integer item = priceToCountMap.get(price);
            if(item == null) {
                return;
            }
            if(item == 1) {
                priceToCountMap.remove(item);
            } else {
                priceToCountMap.put(price, item-1);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
