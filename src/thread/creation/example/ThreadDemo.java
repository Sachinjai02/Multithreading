package thread.creation.example;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread : " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getPriority());

                //Explicitly throwing an exception
                throw new RuntimeException("Kill the thread " + Thread.currentThread().getName());
            }
        });

        thread.setName("New Worker thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        thread.setUncaughtExceptionHandler( (t, e) -> {
            System.out.println("Critical error happened in thread : " + t.getName() + " : " + e.getMessage());
        });

        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");

        Thread.sleep(10000);
    }

}
