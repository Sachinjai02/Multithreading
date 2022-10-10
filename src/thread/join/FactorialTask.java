package thread.join;

import java.math.BigInteger;

public class FactorialTask implements Runnable{
    BigInteger factorial;
    boolean isFinished;
    int number;

    public FactorialTask(int num) {
        this.number = num;
    }

    @Override
    public void run() {
        factorial = BigInteger.ONE;
        for(int i=2;i<number;++i) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Thread has been interrupted.. ");
                return;
            }
            factorial = factorial.multiply(new BigInteger(String.valueOf(i)));
        }
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public BigInteger getFactorial() {
        return this.factorial;
    }
}
