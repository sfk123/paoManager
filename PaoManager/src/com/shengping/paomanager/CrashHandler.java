package com.shengping.paomanager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler,OnClickListener {
	private Context mContext;
	private static final String VERSION_NAME = "versionName";  
	private static final String VERSION_CODE = "versionCode";  
	/** 使用Properties来保存设备的信息和错误堆栈信息 */  
    private Properties mDeviceCrashInfo = new Properties();  
	/** 系统默认的UncaughtException处理类 */  
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
	 /** CrashHandler实例 */  
    private static CrashHandler INSTANCE;  
    /** 获取CrashHandler实例 ,单例模式 */  
    public static CrashHandler getInstance() {  
        if (INSTANCE == null)  
            INSTANCE = new CrashHandler();  
        return INSTANCE;  
    }  
	public void init(Context context){
		mContext=context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        Thread.setDefaultUncaughtExceptionHandler(this);  
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {  
            // 如果用户没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);  
        }else{
        	// Sleep一会后结束程序  
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序  
            try {  
                Thread.sleep(10000);  
            } catch (InterruptedException e) {  
                Log.e("cmd", "Error : ", e);  
            }  
            android.os.Process.killProcess(android.os.Process.myPid());  
            System.exit(10);  
        }
	}
	 /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息;否则返回false 
     */  
    private boolean handleException(Throwable ex) {  
        if (ex == null) {  
            return true;  
        }  
        final String msg = ex.getLocalizedMessage();  
        Writer info = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(info);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        ex.printStackTrace();
        // toString() 以字符串的形式返回该缓冲区的当前值。  
       final String result = info.toString();  
        printWriter.close();  
        // 使用Toast来显示异常信息  
        new Thread() {  
            @Override  
            public void run() {  
                // Toast 显示需要出现在一个线程的消息队列中  
                Looper.prepare();  
                Toast.makeText(mContext, "程序出错啦:" + msg+"\n"+result, 10000).show();  
                Looper.loop();  
            }  
        }.start();  
        return true;  
    }
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		 android.os.Process.killProcess(android.os.Process.myPid());  
         System.exit(10);  
	}
	/** 
     * 收集程序崩溃的设备信息 
     *  
     * @param ctx 
     */  
    public void collectCrashDeviceInfo(Context ctx) {  
        try {  
            // Class for retrieving various kinds of information related to the  
            // application packages that are currently installed on the device.  
            // You can find this class through getPackageManager().  
            PackageManager pm = ctx.getPackageManager();  
            // getPackageInfo(String packageName, int flags)  
            // Retrieve overall information about an application package that is installed on the system.  
            // public static final int GET_ACTIVITIES  
            // Since: API Level 1 PackageInfo flag: return information about activities in the package in activities.  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                // public String versionName The version name of this package,  
                // as specified by the <manifest> tag's versionName attribute.  
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);  
                // public int versionCode The version number of this package,   
                // as specified by the <manifest> tag's versionCode attribute.  
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);  
            }  
        } catch (NameNotFoundException e) {  
            Log.e("cmd", "Error while collect package info", e);  
        }  
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,  
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息  
        // 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                // setAccessible(boolean flag)  
                // 将此对象的 accessible 标志设置为指示的布尔值。  
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常  
                field.setAccessible(true);  
                mDeviceCrashInfo.put(field.getName(), field.get(null));  
               
            } catch (Exception e) {  
                Log.e("cmd", "Error while collect crash info", e);  
            }  
        }  
    }  
}
