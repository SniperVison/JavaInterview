import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        defectOfABA();
    }

    //    public static void main(String[] args) throws Exception {
//        FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\001.txt");
//        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
//        BufferedReader br = new BufferedReader(isr);
//        String str = "";
//        Map<String, String> map = new HashMap<>();
//        while ((str = br.readLine()) != null) {
//            if (str.startsWith("Order ID:") || str.startsWith("Item:") || str.startsWith("Qty:") || str.startsWith("Return reason:")) {
//                map.put(str.substring(0, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
//            }
//        }
//        br.close();
//        isr.close();
//        fis.close();
//        System.out.println(map);
//    }
    static void defectOfABA() {
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        Thread coreThread = new Thread(() -> {
            final int currentValue = atomicInteger.get();
            System.out.println(Thread.currentThread().getName() + " -------currentValue=" + currentValue);
            // 这段目的：模拟处理其他业务花费的时间
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean casResult = atomicInteger.compareAndSet(1, 2);
            System.out.println(Thread.currentThread().getName()
                    + " ------ currentValue=" + currentValue
                    + ", finalValue=" + atomicInteger.get()
                    + ", compareAndSet Result=" + casResult);

        });
        coreThread.start();

        // 这段目的：为了让 coreThread 线程先跑起来
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread amateurThread = new Thread(
                () -> {
                    int currentValue = atomicInteger.get();
                    boolean casResult = atomicInteger.compareAndSet(1, 2);
                    System.out.println(Thread.currentThread().getName()
                            + " ------ currentValue=" + currentValue
                            + ", finalValue=" + atomicInteger.get()
                            + ", compareAndSet Result=" + casResult);

                    currentValue = atomicInteger.get();
                    casResult = atomicInteger.compareAndSet(2, 1);
                    System.out.println(Thread.currentThread().getName()
                            + " ------ currentValue=" + currentValue
                            + ", finalValue=" + atomicInteger.get()
                            + ", compareAndSet Result=" + casResult);
                }
        );
        amateurThread.start();
    }

}


