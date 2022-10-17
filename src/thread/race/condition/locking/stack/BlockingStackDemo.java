package thread.race.condition.locking.stack;

import java.util.Random;

public class BlockingStackDemo {

    private static class BlockingStack<T> {

        public static void main(String[] args) throws InterruptedException {
            BlockingStack<Integer> stack = new BlockingStack<>();
            Random random = new Random();
            Thread pusher = new Thread(()-> {
                for(int i=0;i<100;++i) {
                    System.out.println(Thread.currentThread().getName() + " pushed " + i);
                    stack.push(i);
                }
            });

            Thread pusher2 = new Thread(()-> {
                for(int i=200;i<300;++i) {
                    System.out.println(Thread.currentThread().getName() + " pushed " + i);
                    stack.push(i);
                }
            });
            pusher.start();
            pusher2.start();
            pusher.join();
            pusher2.join();

            while(stack.size != 0) {
                System.out.println("popped : "+stack.pop());
            }

        }
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
