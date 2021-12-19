package derderjar.tublack;

import derderjar.tublack.handler.Handler;
import derderjar.tublack.handler.Looper;

import java.util.Scanner;

public class Main {
    private static Handler handler ;
    public static void main(String[] args) {
        //首先创建个主线程Looper,它将会初始化化消息队列
        Looper.prepareMainLooper();
        //创建线程的Handler对象
        handler = new Handler(msg -> Logger.Log("handleMessage:"+msg.what));
        //接着创建一个子线程,这个线程我们用来发送消息
        Thread t = new Thread(()-> {
                Scanner sc = new Scanner(System.in);
                postMessage();
                while(true){
                    Logger.Log("please input a message");
                    if(sc.hasNextLine()){
                        String s = sc.nextLine();
                        String [] arr = s.split("\\s+");
                        if(arr.length<2) {
                            Logger.Log("you type a wrong message");
                            continue;
                        }
                        handler.sendMessageDelayed(handler.obtain(Integer.valueOf(arr[0])),Long.valueOf(arr[1]));
                    }
                }
        });
        t.start();
        //创建完毕 主线程进入loop
        Looper.loop();
    }

    private static void postMessage(){
        handler.postDelayed(() -> Logger.Log("post Message exec"),10000);
    }
}
