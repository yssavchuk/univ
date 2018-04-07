

public class PCQueue {

    protected int item = EMPTY_QUEUE;
    protected static int EMPTY_QUEUE = -1;

    public synchronized void put(int item){
        while (this.item != EMPTY_QUEUE){
            try {
                int i = 0;
                for(; i < 10; i++)
                    wait(100);
                if (i == 10){
                    System.out.println("Mutual blocking was detected");
                }
            }
            catch (InterruptedException ex){
            }
        }
        this.item = item;
        notificate("Produced: ", item);
    }

    public synchronized int take(){
        while (this.item == EMPTY_QUEUE){
            try {
                int i = 0;
                for(; i < 10; i++)
                    wait(100);
                if (i == 10){
                    System.out.println("Mutual blocking was detected");
                }
            }
            catch (InterruptedException ex){
            }
        }
        int taken = this.item;
        this.item = EMPTY_QUEUE;
        notificate("Consumed: ", taken);
        return taken;
    }

    protected synchronized void notificate(String action, int item){
        System.out.println(action + item);
        notifyAll();
    }
}