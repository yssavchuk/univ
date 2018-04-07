public class TestPoolMain {
    private static final int POOL_SIZE = 10;

    public static void main(String[] args) {
        testNThreads();
    }

    public static void testNThreads() {
        Pool pool = new Pool(POOL_SIZE);
        System.out.println("Starting...");
        try {
           pool.testLock(BakeryLock.class);
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
 }
