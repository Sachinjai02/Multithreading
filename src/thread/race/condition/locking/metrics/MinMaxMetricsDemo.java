package thread.race.condition.locking.metrics;

import java.util.Random;

public class MinMaxMetricsDemo {

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();
        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);
        MinMaxMetricsPrinter printer = new MinMaxMetricsPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        printer.start();
    }

    public static class MinMaxMetricsPrinter extends Thread {
        private MinMaxMetrics metrics;
        public MinMaxMetricsPrinter(MinMaxMetrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Max time taken by API : " + metrics.getMaxTime());
                System.out.println("Max time taken by API : " + metrics.getMinTime());
            }
        }
    }
    public static class BusinessLogic extends Thread {
        private MinMaxMetrics metrics;
        private Random random = new Random();
        public BusinessLogic(MinMaxMetrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while(true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.metrics.addSample(System.currentTimeMillis() - start);
            }
        }
    }
    public static class MinMaxMetrics {
        private volatile long minTime = Long.MAX_VALUE;
        private volatile long maxTime = 0;

        public MinMaxMetrics() {

        }
        public synchronized void addSample(long sample) {
            minTime = Math.min(minTime, sample);
            maxTime = Math.max(maxTime, sample);
        }
        public long getMinTime() {
            return this.minTime;
        }

        public long getMaxTime() {
            return this.maxTime;
        }
    }
}
