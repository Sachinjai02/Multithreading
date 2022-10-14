package thread.rentrant.lock.inventoryitems;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryDatabase {

    private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
    private ReentrantLock lock = new ReentrantLock();

    public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
        lock.lock();
        try {
            Integer lower = priceToCountMap.ceilingKey(lowerBound);
            Integer upper = priceToCountMap.floorKey(upperBound);

            if(lower == null || upper == null) return 0;

            SortedMap<Integer, Integer> priceRangeMap = priceToCountMap.subMap(lower, upper);
            final int[] sum = new int[1];
            priceRangeMap.values().stream().forEach( i -> sum[0] += i);
            return sum[0];
        } finally {
            lock.unlock();
        }
    }

    public void addItem(int price) {
        lock.lock();
        try {
            Integer items = priceToCountMap.getOrDefault(price,0);
            priceToCountMap.put(price, items+1);
        } finally {
            lock.unlock();
        }

    }

    public void removeItem (int price) {
        lock.lock();
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
            lock.unlock();
        }
    }
}
