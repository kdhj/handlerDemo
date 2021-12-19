package derderjar.tublack;

import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void Log(String str){
        System.out.println(getDate()+":"+Thread.currentThread().getName()+"-"+Thread.currentThread().getId()+":"+str);
    }
    public static String getDate(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss:SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }
}
