public class FixnumLockTestThread extends Thread {
    private FixnumLock lock;

    public FixnumLockTestThread(FixnumLock lock) {

        super();
        System.out.println("Creating new thread...");
        this.lock = lock;
    }
    @Override
    public void run() {

        while(!lock.register()) {
            Thread.yield();
        }

        lock.lock();
        System.out.println("Locked thread: " + Thread.currentThread().getName());
        f(90);
        lock.unlock();
        System.out.println("Unlocked thread: " + Thread.currentThread().getName());

        lock.unregister();
    }

    private int f(int num) {
        if(num == 0) return 0;
        return f(num-1);
    }
}
