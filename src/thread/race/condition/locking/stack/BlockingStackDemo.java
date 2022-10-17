package thread.race.condition.locking.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockingStackDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingStack<Integer> stack = new BlockingStack<>();
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
        System.out.println("Number of operations done in 10 secs  " + stack.counter);

    }
    private static class BlockingStack<T> {


        private StackNode<T> head;
        private int counter = 0;
        private int size = 0;
        public synchronized void push(T value) {
            StackNode<T> node = new StackNode<>(value);
            node.next = head;
            head = node;
            ++counter;
            ++size;
        }

        public synchronized T pop() {
            if(head == null) {
                ++counter;
                return null;
            }
            T currVal = head.value;
            head = head.next;
            ++counter;
            --size;
            return currVal;
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
