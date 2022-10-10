package thread.vault;

public class PoliceThread extends Thread {
    @Override
    public void run() {
        for(int i=0;i<10;i++) {
            try {
                System.out.println("Timer at :" + i );
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Caught the thieves!!");
        System.exit(0);
    }
}
