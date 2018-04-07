import java.util.ArrayList;

public class BakeryLock extends ImplementationFixnumLock {

    static ArrayList<Integer> tickets = getFilledList(threadNumber, 0);
    static ArrayList<Boolean> choosing = getFilledList(threadNumber, false);

    @Override
    public void lock()
    {
        choosing.set(pid, true);
        int max = 0;
        for (int i = 0; i < threadNumber; i++) {
            if (tickets.get(i) > max) {
                max = tickets.get(i);
            }
        }
        // System.out.println(max);
        tickets.set(pid, max + 1);
        choosing.set(pid, false);



        for (int i = 0; i < tickets.size(); ++i) {
            if (i != pid) {

                while (choosing.get(i)) {
                    Thread.yield();
                }
                while (!tickets.get(i).equals(0) && (tickets.get(pid) > tickets.get(i) ||
                        (tickets.get(pid).equals(tickets.get(i)) && pid > i))) {
                    Thread.yield();
                }
            }
        }
        System.out.println("In the critical section: " + tickets.get(pid));
    }

    public void unlock() {
        tickets.set(pid, 0);
    }
}
