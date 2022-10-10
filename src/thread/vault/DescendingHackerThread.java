package thread.vault;

public class DescendingHackerThread extends AbstractHacker {

    public DescendingHackerThread(Vault v) {
        super(v);
    }

    @Override
    public void run() {
        for(int i=MAX_VALUE;i>=MIN_VALUE;--i) {
            if(v.isPasswordCorrect(i)) {
                System.out.println(this.getName() + " hacked the vault ");
                System.exit(0);
            }
        }
    }
}
