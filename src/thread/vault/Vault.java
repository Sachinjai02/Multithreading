package thread.vault;

public class Vault {
    private int password;

    public Vault(int pwd) {
        this.password = pwd;
    }

    public boolean isPasswordCorrect(int pwd) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this.password == pwd;
    }
}
