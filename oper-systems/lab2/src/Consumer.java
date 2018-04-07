
public class Consumer extends Thread {
    private PCQueue queue;
    private int expected = 1;

    public Consumer(PCQueue q){
        this.queue = q;
    }

    @Override
    public synchronized void run() {
        while (true) {
            int take = queue.take();
            if(expected != take){
                System.out.println("Lost item detected");
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            expected = (take + 1) % 100;
        }
    }
}
