package derderjar.tublack.handler;

public class Message {
    public int what = 0;
    public int arg = -1;
    public Object obj = null;
//    private Message msgPool = null;
    public Message next = null;
    public long when = 0;
    public Handler target = null;
    public Runnable callback = null;
    private Message(){

    }
    public static Message obtain(int what, int arg, Object obj,Handler handler,Runnable callback){
        Message msg= new Message();
        msg.what = what;
        msg.arg= arg;
        msg.obj=obj;
        msg.target = handler;
        msg.callback = callback;
        return msg;
    }
    public static Message obtain(int what, int arg,Handler handler){
        Message msg= new Message();
        msg.what = what;
        msg.arg= arg;
        msg.target = handler;
        return msg;
    }
    public static Message obtain(int what,Handler handler){
        Message msg= new Message();
        msg.what = what;
        msg.target = handler;
        return msg;
    }
    public static Message obtain(){
        return new Message();
    }
}
