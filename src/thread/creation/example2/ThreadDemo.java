package thread.creation.example2;

public class ThreadDemo extends Thread {
    public static void main(String[] args) {
        new ThreadDemo().start();
    }

    @Override
    public void run() {
        System.out.println("Hello from " + this.getName());
    }
}
