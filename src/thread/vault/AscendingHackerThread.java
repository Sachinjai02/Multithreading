package thread.vault;

public class AscendingHackerThread extends AbstractHacker {

    public AscendingHackerThread(Vault v) {
        super(v);
    }

    @Override
    public void run() {
        for(int i=MIN_VALUE;i<=MAX_VALUE;++i) {
            if(v.isPasswordCorrect(i)) {
                System.out.println(this.getName() + " hacked the vault ");
                System.exit(0);
            }
        }
    }
}
