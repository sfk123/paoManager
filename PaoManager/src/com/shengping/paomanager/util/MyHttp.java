package com.shengping.paomanager.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MyHttp {
	private RequestQueue mQueue ;
	private MyHttpCallBack callback;
	private DefaultRetryPolicy policy;
	private Context context;
	public MyHttp(Context context){
		this.context=context;
		mQueue = Volley.newRequestQueue(context);  
		policy=new DefaultRetryPolicy(10000, //设置默认超时时间
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	}
	public void Http_get(String url,MyHttpCallBack mycallback){
		this.callback=mycallback;
		Log.i("volley", url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,  
		        new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
		            	callback.onResponse(response);  
		            }  
		        }, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		            	callback.onErrorResponse(error);  
		            }  
		        }); 
		jsonObjectRequest.setRetryPolicy(policy);
		mQueue.add(jsonObjectRequest);  
	}
	public void Http_post(String url,Map<String, String> params,MyHttpCallBack mycallback){
		this.callback=mycallback;
		Request<JSONObject> request = new NormalPostRequest(url,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			        	callback.onResponse(response);
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			        	callback.onErrorResponse(error);
			        }
			    }, params);
		request.setRetryPolicy(policy);
		mQueue.add(request);  
	}
	/**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params 普通参数
     * @param files 文件参数
	 * @param myCaptureFile 
     * @return
     * @throws IOException
     */
    public void post(final String actionUrl, final Map<String, String> params, final Map<String, File> files, final MyHttpCallBack cb )
    {
    	Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
				String BOUNDARY = java.util.UUID.randomUUID().toString();
		        String PREFIX = "--", LINEND = "\r\n";
		        String MULTIPART_FROM_DATA = "multipart/form-data";
		        String CHARSET = "UTF-8";

		        URL uri = new URL(actionUrl);
		        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		        conn.setReadTimeout(5 * 1000*60); // 缓存的最长时间
		        conn.setDoInput(true);// 允许输入
		        conn.setDoOutput(true);// 允许输出
		        conn.setUseCaches(false); // 不允许使用缓存
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("connection", "keep-alive");
		        conn.setRequestProperty("Charsert", "UTF-8");
		        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

		        // 首先组拼文本类型的参数
		        StringBuilder sb = new StringBuilder();
		        for (Map.Entry<String, String> entry : params.entrySet())
		        {
		            sb.append(PREFIX);
		            sb.append(BOUNDARY);
		            sb.append(LINEND);
		            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
		            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
		            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
		            sb.append(LINEND);
		            sb.append(entry.getValue());
		            sb.append(LINEND);
		        }

		        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		        outStream.write(sb.toString().getBytes());
		        InputStream in = null;
		        // 发送文件数据
		        if (files != null)
		        {
		        	int i=0;
		            for (Map.Entry<String, File> file : files.entrySet())
		            {
		                StringBuilder sb1 = new StringBuilder();
		                sb1.append(PREFIX);
		                sb1.append(BOUNDARY);
		                sb1.append(LINEND);
		                // name是post中传参的键 filename是文件的名称
		                sb1.append("Content-Disposition: form-data; name=\""+file.getKey()+"\"; filename=\"" + file.getKey() + "\"" + LINEND);
		                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		                sb1.append(LINEND);
		                outStream.write(sb1.toString().getBytes());
		                System.out.println("上传文件："+file.getKey());
		                InputStream is = new FileInputStream(file.getValue());
		                byte[] buffer = new byte[1024];
		                int len = 0;
		                while ((len = is.read(buffer)) != -1)
		                {
		                    outStream.write(buffer, 0, len);
		                }

		                is.close();
		                outStream.write(LINEND.getBytes());
		                i++;
		            }

		            // 请求结束标志
		            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		            outStream.write(end_data);
		            outStream.flush();
		            // 得到响应码
		            int res = conn.getResponseCode();in = conn.getInputStream();
	                int ch;
	                StringBuilder sb2 = new StringBuilder();
	                while ((ch = in.read()) != -1)
	                {
	                    sb2.append((char) ch);
	                }
//	                System.out.println(sb2.toString());
		            if (res == 200)
		            {
		                
		                cb.onResponse(new JSONObject(sb2.toString()));
		            }else{
		            	cb.onErrorResponse(new VolleyError(sb2.toString()));
		            }
		            outStream.close();
		            conn.disconnect();
		        }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    	thread.start();
    }
    public void post(final String actionUrl, final Map<String, String> params, final File file, final MyHttpCallBack cb )
    {
    	Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
				String BOUNDARY = java.util.UUID.randomUUID().toString();
		        String PREFIX = "--", LINEND = "\r\n";
		        String MULTIPART_FROM_DATA = "multipart/form-data";
		        String CHARSET = "UTF-8";

		        URL uri = new URL(actionUrl);
		        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		        conn.setReadTimeout(5 * 1000*60); // 缓存的最长时间
		        conn.setDoInput(true);// 允许输入
		        conn.setDoOutput(true);// 允许输出
		        conn.setUseCaches(false); // 不允许使用缓存
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("connection", "keep-alive");
		        conn.setRequestProperty("Charsert", "UTF-8");
		        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

		        // 首先组拼文本类型的参数
		        StringBuilder sb = new StringBuilder();
		        for (Map.Entry<String, String> entry : params.entrySet())
		        {
		            sb.append(PREFIX);
		            sb.append(BOUNDARY);
		            sb.append(LINEND);
		            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
		            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
		            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
		            sb.append(LINEND);
		            sb.append(entry.getValue());
		            sb.append(LINEND);
		        }
		        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		        outStream.write(sb.toString().getBytes());
		        InputStream in = null;
		        // 发送文件数据
		        if (file != null)
		        {
		                StringBuilder sb1 = new StringBuilder();
		                sb1.append(PREFIX);
		                sb1.append(BOUNDARY);
		                sb1.append(LINEND);
		                // name是post中传参的键 filename是文件的名称
		                sb1.append("Content-Disposition: form-data; name=\"avatar\"; filename=\"avatar_img.jpg\"" + LINEND);
		                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		                sb1.append(LINEND);
		                outStream.write(sb1.toString().getBytes());

		                InputStream is = new FileInputStream(file);
		                byte[] buffer = new byte[1024];
		                int len = 0;
		                while ((len = is.read(buffer)) != -1)
		                {
		                    outStream.write(buffer, 0, len);
		                }

		                is.close();
		                outStream.write(LINEND.getBytes());

		            // 请求结束标志
		            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		            outStream.write(end_data);
		            outStream.flush();
		            // 得到响应码
		            int res = conn.getResponseCode();
		            in = conn.getInputStream();
	                int ch;
	                StringBuilder sb2 = new StringBuilder();
	                while ((ch = in.read()) != -1)
	                {
	                    sb2.append((char) ch);
	                }
	                String response=sb2.toString();
	                response=new String(response.getBytes("iso-8859-1"),"utf-8");
		            if (res == 200)
		            {
		                cb.onResponse(new JSONObject(response));
		            }else{
		            	cb.onErrorResponse(new VolleyError(response));
		            }
		            outStream.close();
		            conn.disconnect();
		        }
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    	thread.start();
    }
    
    public interface MyHttpCallBack{
		public void onResponse(JSONObject response);
		public void onErrorResponse(VolleyError error);
	}
}
