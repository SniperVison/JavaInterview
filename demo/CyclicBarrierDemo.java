import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 循环栅栏
 */
public class CyclicBarrierDemo {
    private static final int count = 500;
    static int num = 0 ;
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
        System.out.println("优先处理:" + (num++));
    });

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < count; i++) {
            final int threamNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    test(threamNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        System.out.println("finish");
    }

    public static void test(int threadNum) throws Exception {
        System.out.println("threadNum: " + threadNum + "is ready");
        try {
            cyclicBarrier.await(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("threadNum: " + threadNum + "is finish");
    }
}
