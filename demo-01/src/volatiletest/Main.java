package volatiletest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {

    volatile int number = 0;

    public void addNumber(){
        number = 60;
    }

    public void addPlus(){
        number ++;
    }
    //创建一个原子类，初始值为：0
    AtomicInteger atomicInteger = new AtomicInteger();

    public void  addAtomic(){
        atomicInteger.getAndIncrement();
    }
}


public class Main {

    public static void main(String[] args) {
        MyData myData = new MyData();

        for(int i=0; i < 20; i++){
            new Thread(()->{
                for(int j=0; j < 1000; j++){
                    myData.addPlus();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }
        //后台默认有两个线程一个是main线程，一个是GC线程
        while(Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t int type,finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type,finally number value: " + myData.atomicInteger);
    }
    //可以保证可见性，可以及时通知其他线程主物理内存的值已经被修改
    private static void seeOKbyVolatile() {
        //System.out.println("Hello World!");
        MyData myData = new MyData();
        //暂停一会儿线程
        new Thread(()-> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addNumber();
            System.out.println(Thread.currentThread().getName() + "\t updated number value："+myData.number);
            }, "AAA").start();

        //第2个线程就是我们的main线程

        while(myData.number == 0){
            // mian线程就一直再这里等待循环，直到number值不在等于零
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over");
    }
}
