package thread.vault;

import java.util.Random;

public class King {
    public static void main(String[] args) {
        Vault vault = new Vault(new Random().nextInt(AbstractHacker.MAX_VALUE));
        AscendingHackerThread asc = new AscendingHackerThread(vault);
        DescendingHackerThread desc = new DescendingHackerThread(vault);
        PoliceThread policeThread = new PoliceThread();

        policeThread.start();
        asc.start();
        desc.start();

    }
}
