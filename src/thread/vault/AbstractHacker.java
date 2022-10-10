package thread.vault;

public class AbstractHacker extends Thread {
    public static int MAX_VALUE = 10000;
    public static int MIN_VALUE = 0;
    protected Vault v;

    public AbstractHacker(Vault v) {
        this.v = v;
        this.setName(this.getClass().getSimpleName());
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public synchronized void start() {
        System.out.println(this.getName() + " is attempting to hack the vault" );
        super.start();
    }

}
