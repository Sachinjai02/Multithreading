package thread.data.race;

public class DemoDataRace {

    public static void main(String[] args) {
        SharedClass object = new SharedClass();
        Thread t1 = new Thread(() -> {
            while(true) {
                object.increment();
            }
        });
        Thread t2 = new Thread(()-> {
            while(true) {
                object.checkForDataRace();
            }
        });
        t1.start();
        t2.start();
    }
    public static class SharedClass {

        //volatile instructs the compiler/CPU to execute the instructions before and after the shared volatile variables in order
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if(y>x) {
                System.out.println("y>x - Data race condition is detected..");
            }
        }
    }
}
