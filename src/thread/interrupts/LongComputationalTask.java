package thread.interrupts;

import java.math.BigInteger;

public class LongComputationalTask implements Runnable {
    private BigInteger base;
    private BigInteger pow;

    public LongComputationalTask(BigInteger base, BigInteger pow) {
        this.base = base;
        this.pow = pow;
    }

    @Override
    public void run() {
        BigInteger powRes = BigInteger.ONE;
        for(BigInteger i = BigInteger.ONE; ! i.equals(pow) ; i=i.add(BigInteger.ONE)) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Exiting the long computational task .. ");
                return;
            }
            powRes = powRes.multiply(base);
        }
        System.out.println(base + "^" + pow + " = " + powRes);
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationalTask(new BigInteger("2345656"), new BigInteger("1000998888")));
        thread.start();
        System.out.println("going to interrupt the long computational task");
        thread.interrupt();
    }
}
