import domain.Person;

import java.util.concurrent.atomic.AtomicReference;

public class VolatileTest extends Thread {
//volatile 会每次都强制去主内存取值，不加的话会在当前线程的堆栈取值

    volatile boolean flag = false;
    int cnt = 0;

    @Override
    public void run() {
        while (!flag) {
            cnt++;
            System.err.println(cnt);
        }
    }

    public static void main(String[] args) throws Exception {
        VolatileTest test = new VolatileTest();
        test.start();
        Thread.sleep(20);
        test.flag = true;
        System.out.println("step: " + test.cnt);
        AtomicReference<Person> ar= new AtomicReference<>();
        Person vison = new Person("vison", 25);
        ar.set(vison);
        System.out.println(ar.get());
        Person hashagi = new Person("hashagi", 26);
        ar.compareAndSet(vison,hashagi);
        System.out.println(ar.get());

    }
}
