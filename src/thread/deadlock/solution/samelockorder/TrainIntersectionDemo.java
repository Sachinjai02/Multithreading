package thread.deadlock.solution.samelockorder;

import java.util.Random;

public class TrainIntersectionDemo {


    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainA = new TrainA(intersection);
        Thread trainB = new TrainB(intersection);
        trainA.start();
        trainB.start();
    }

    public static class TrainB extends Thread {
        private Intersection intersection;
        private Random random;
        public TrainB(Intersection intersection) {
            this.intersection = intersection;
            this.random = new Random();
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(this.random.nextInt(5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadB();

            }
        }
    }

    public static class TrainA extends Thread {
        private Intersection intersection;
        private Random random;
        public TrainA(Intersection intersection) {
            this.intersection = intersection;
            this.random = new Random();
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(this.random.nextInt(5));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadA();

            }
        }
    }
    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by " + Thread.currentThread().getName());
                    synchronized (roadB) {
                        System.out.println("Train is passing through road A");
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            }
        }

        public void takeRoadB() {
            synchronized (roadA) {
                System.out.println("Road A is locked by " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
