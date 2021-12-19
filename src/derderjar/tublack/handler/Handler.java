package derderjar.tublack.handler;

import com.sun.istack.internal.NotNull;
import derderjar.tublack.Logger;

public class Handler {
    private Looper looper ;
    private MessageQueue queue;

    public interface Callback{
        void handleMessage(Message msg);
    }

    private Callback cb ;

    public Handler(Callback cb){
        this.cb = cb;
        looper = Looper.myLooper();
        queue = looper.queue;
    }
    public Message obtain(int what, int arg, Object obj,Runnable callback){

        return Message.obtain(what, arg, obj,this,callback);
    }
    public Message obtain(int what, int arg){

        return Message.obtain(what,arg,this);
    }
    public Message obtain(int what){
        Logger.Log("obtain msg what = "+what);
        return Message.obtain(what,this);
    }
    public void sendMessage(Message msg){
        queue.enQueue(msg,0);
    }
    public void sendMessageDelayed(Message msg,long delayTime){
        queue.enQueue(msg,System.currentTimeMillis()+delayTime);
    }
    public void post(Runnable r){
//        queue.enQueue(getPostMessage(r),0);
        sendMessageDelayed(getPostMessage(r),0);
    }
    public void postDelayed(Runnable r,long delayTime){
        sendMessageDelayed(getPostMessage(r),delayTime);
    }
    private Message getPostMessage(Runnable r){
        Message msg = Message.obtain();
        msg.callback =r;
        msg.target = this;
        return msg;
    }
    public void dispatchMessage(@NotNull Message msg){
        if(msg.callback!=null){
            msg.callback.run();
        }else {
            if (cb!=null) {
                cb.handleMessage(msg);
            }
        }
    }
}
