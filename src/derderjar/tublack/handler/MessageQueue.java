package derderjar.tublack.handler;

import derderjar.tublack.Logger;

public class MessageQueue {
    private Message hMsg = null;
    private boolean blocked = false;
    private boolean needWake = false;
    private Thread thread;
    public MessageQueue(){
        thread = Thread.currentThread();
    }
    Message next(){
        long sleepTime = 0;
        for(;;){
            sleep(sleepTime);
            synchronized (this){
                Message msg = hMsg;
                blocked = false;
                long now = System.currentTimeMillis();
                if(msg !=null && msg.target!=null){
                    //取出当前头结点消息，这个头结点消息必定是最先执行的消息
                    if(now<msg.when){
                        //还没到消息要执行的时间，睡一会儿设置这个变量是为了可以唤醒这个线程
                        blocked = true;
                        sleepTime = Math.min(msg.when-now,Integer.MAX_VALUE);
                    }else{
                        hMsg = msg.next;
                        msg.next=null;
                        return msg;
                    }
                }else{
                    sleepTime = 0;
                }

            }

        }

    };

    public void enQueue(Message msg,long when){
        if(msg == null) throw new RuntimeException("enQueue failed msg is null");
//        long when = msg.when;
;
        synchronized (this){
            Message tmp = hMsg;
            long now  = System.currentTimeMillis();
            if(tmp ==null || now>when || when< tmp.when){
                //放在头结点
                msg.next = hMsg;
                hMsg = msg;
                msg.when=when;
                needWake = blocked;
            }else{
                Message pre ;
                for(;;){
                    pre = tmp;
                    tmp = tmp.next;
                    if(tmp==null||tmp.when<when){
                        pre.next = msg;
                        msg.next = tmp;
                        msg.when = when;
                        break;
                    }
                }
            }
            if(needWake) wake();
        }
    }
    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Logger.Log("wakeup");
        }
    }
    public void wake(){
        thread.interrupt();
    }
}
