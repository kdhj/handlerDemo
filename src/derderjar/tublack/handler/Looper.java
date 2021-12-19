package derderjar.tublack.handler;

public class Looper {
    final static ThreadLocal<Looper> mLooper = new ThreadLocal<Looper>();
    private static Looper mainLooper = null;
    private Thread thread = null;
    public MessageQueue queue = null;
    private Looper(){
        queue = new MessageQueue();
        thread = Thread.currentThread();
    };
    public static void prepare(){
        if(mLooper.get()!=null){
            throw new RuntimeException("only can create one looper");
        }
        mLooper.set(new Looper());
    }
    public static void prepareMainLooper(){
        if(mLooper.get()!=null){
            throw new RuntimeException("only can create one looper");
        }
        Looper l = new Looper();
        mLooper.set(l);
        mainLooper = l;
    }

    public static Looper myLooper(){
        return mLooper.get();
    }

    public static void loop(){
        //拿到当前线程的Looper实例对象
        final Looper me= mLooper.get();
        MessageQueue queue = me.queue;
        for (;;){
            Message msg = queue.next();
            if(msg!=null){
                msg.target.dispatchMessage(msg);
            }
        }
    }
}
