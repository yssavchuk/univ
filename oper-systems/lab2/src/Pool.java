import java.util.ArrayList;

public class Pool {
    private ArrayList<Thread> threadsList = new ArrayList<>();
    private int size;

    public Pool(int poolSize) {
        this.size = poolSize;
    }

    public void threadInitialization(Class<? extends FixnumLock> fixnumLockClass) throws IllegalAccessException {
        System.out.println("Creating pool with " + size + " threads...");
        System.out.println("Class: " + fixnumLockClass.toString());
        for(int i = 0; i < size; ++i) {
            BakeryLock newInst = new BakeryLock();
            System.out.println("newInst: " + newInst);
            FixnumLockTestThread test = new FixnumLockTestThread(newInst);
            System.out.println("FixnumLockTestThread" + test);
            threadsList.add(test);
        }
    }

    public void runThreads() throws Exception {
        System.out.println("---------\nPool task\n---------");
        for(Thread thread: threadsList) {
            thread.start();
        }
    }

    public void testLock(Class<? extends FixnumLock> fixnumLockClass) throws Exception {
        threadInitialization(fixnumLockClass);
        runThreads();
    }
}