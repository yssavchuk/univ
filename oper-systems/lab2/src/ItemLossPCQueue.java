public class ItemLossPCQueue extends PCQueue {

    @Override
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

        if (Math.random() > 0.2 ){
            this.item = item;
            notificate("Produced: ", item);
        }
        else {
            notificate("Lost item: ", item);
        }
    }
}
