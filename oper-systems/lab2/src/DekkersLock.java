import java.util.ArrayList;

public class DekkersLock extends ImplementationFixnumLock {

    static int threadNumber = 2;

    private static int turn = 0;
    private static ArrayList<Boolean> flag = getFilledList(threadNumber, false);

    @Override
    public void lock() {
        flag.set(pid, true);
        while( flag.get(invertedPid())) {
            if (turn != pid) {
                flag.set(pid, false);
                while (turn != pid) {
                    Thread.yield();
                }
                flag.set(pid, true);
            }
        }
    }

    @Override
    public void unlock() {
        flag.set(pid, false);
        turn = invertedPid();
    }

    private int invertedPid() {
        return pid^1;
    }
}
