public class DeadLockPCQueue extends PCQueue {
    @Override
    protected synchronized void notificate(String action, int item){
        String message = action + item;
        if(Math.random() > 0.2){
            notifyAll();
        }
        else {
            message+= " and blocked";
        }
        System.out.println(message);
    }
}
