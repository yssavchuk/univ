public class Producer extends Thread {

    private PCQueue queue;
    private int counter = 0;

    public Producer(PCQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            queue.put(counter = (counter + 1)%100);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
