package thread.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> numbers = Arrays.asList(234533, 2335, 2345, 232 , 23, 2, 4);
        List<FactorialTask> tasks = new ArrayList<>();
        for(int num: numbers) {
            tasks.add(new FactorialTask(num));
        }
        List<Thread> factThreads = new ArrayList<>();
        for(FactorialTask t : tasks) {
            factThreads.add(new Thread(t));
        }

        for(Thread t : factThreads) {
            t.start();
        }

        for(Thread t : factThreads) {
            t.join();
            //t.join(5000);
        }

        for(int i=0;i< tasks.size();++i) {
            FactorialTask t = tasks.get(i);
            Thread th = factThreads.get(i);
            if(t.isFinished()) {
                System.out.println("Factorial for " + t.number + " is " + t.getFactorial());
            } else {
                th.interrupt();
                System.out.println("Factorial calculation for " + t.number + " is still in progress");
            }
        }

    }
}
