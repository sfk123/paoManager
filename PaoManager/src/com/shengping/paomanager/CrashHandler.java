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
	/** ʹ��Properties�������豸����Ϣ�ʹ����ջ��Ϣ */  
    private Properties mDeviceCrashInfo = new Properties();  
	/** ϵͳĬ�ϵ�UncaughtException������ */  
    private Thread.UncaughtExceptionHandler mDefaultHandler;  
	 /** CrashHandlerʵ�� */  
    private static CrashHandler INSTANCE;  
    /** ��ȡCrashHandlerʵ�� ,����ģʽ */  
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
            // ����û�û�д�������ϵͳĬ�ϵ��쳣������������  
            mDefaultHandler.uncaughtException(thread, ex);  
        }else{
        	// Sleepһ����������  
            // �����߳�ֹͣһ����Ϊ����ʾToast��Ϣ���û���Ȼ��Kill����  
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
     * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. �����߿��Ը����Լ���������Զ����쳣�����߼� 
     *  
     * @param ex 
     * @return true:��������˸��쳣��Ϣ;���򷵻�false 
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
        // toString() ���ַ�������ʽ���ظû������ĵ�ǰֵ��  
       final String result = info.toString();  
        printWriter.close();  
        // ʹ��Toast����ʾ�쳣��Ϣ  
        new Thread() {  
            @Override  
            public void run() {  
                // Toast ��ʾ��Ҫ������һ���̵߳���Ϣ������  
                Looper.prepare();  
                Toast.makeText(mContext, "���������:" + msg+"\n"+result, 10000).show();  
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
     * �ռ�����������豸��Ϣ 
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
        // ʹ�÷������ռ��豸��Ϣ.��Build���а��������豸��Ϣ,  
        // ����: ϵͳ�汾��,�豸������ �Ȱ������Գ����������Ϣ  
        // ���� Field �����һ�����飬��Щ����ӳ�� Class ��������ʾ�����ӿ��������������ֶ�  
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                // setAccessible(boolean flag)  
                // ���˶���� accessible ��־����Ϊָʾ�Ĳ���ֵ��  
                // ͨ������Accessible����Ϊtrue,���ܶ�˽�б������з��ʣ���Ȼ��õ�һ��IllegalAccessException���쳣  
                field.setAccessible(true);  
                mDeviceCrashInfo.put(field.getName(), field.get(null));  
               
            } catch (Exception e) {  
                Log.e("cmd", "Error while collect crash info", e);  
            }  
        }  
    }  
}
