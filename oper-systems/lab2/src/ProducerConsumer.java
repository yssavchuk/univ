public class ProducerConsumer {
    public static void main(String[] args) {
        PCQueue q = null;

//        q = new PCQueue();
//        startPC(q);

        q = new DeadLockPCQueue();
        startPC(q);

//        q = new ItemLossPCQueue();
//        startPC(q);
    }

    private static void startPC(PCQueue queue) {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException ex) {
        }
    }
}
