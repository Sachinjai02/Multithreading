package thread.race.condition.atomic.core.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

import static java.util.concurrent.locks.LockSupport.parkNanos;

public class LockFreeStackDemo {

    public static void main(String[] args) throws InterruptedException {
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        Random random = new Random();

        for(int i=0;i<100000;++i) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();
        int numberPushingThreads = 2;
        int numberPoppingThreads = 2;

        for(int i=0;i<numberPoppingThreads;++i) {
            threads.add(new Thread(()-> {
                while(true) { stack.pop(); }
            }));
        }
        for(int i=0;i<numberPushingThreads;++i) {
            threads.add(new Thread(()-> {
                while(true)
                stack.push(random.nextInt());
            }));
        }
        for(int i=0;i<threads.size();++i) {
            threads.get(i).setDaemon(true);
            threads.get(i).start();
        }

        Thread.sleep(10000);
        System.out.println("Number of operations done in 10 secs  " + stack.counter.get());
    }
    private static class LockFreeStack<T> {


        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);
        private AtomicInteger size = new AtomicInteger(0);
        public void push(T value) {
            StackNode<T> node = new StackNode<>(value);
            while(true) {
                StackNode<T> currHead = head.get();
                node.next = currHead;
                if(head.compareAndSet(currHead, node)) {
                    break;
                } else {
                    parkNanos(1);
                }
            }
           // size.incrementAndGet();
            counter.incrementAndGet();
        }

        public T pop() {
            StackNode<T> currHead = head.get();
            StackNode<T> nextHead = null;
            while(currHead != null) {
                nextHead = currHead.next;
                if(head.compareAndSet(currHead, nextHead)) {
                    //size.decrementAndGet();
                    break;
                } else {
                    parkNanos(1);
                    currHead = head.get();
                }
            }

            counter.incrementAndGet();
            return currHead != null ? currHead.value : null;
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;
        public StackNode(T value) {
            this.value = value;
        }
    }
}
