package com.example.testlogin;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class HttpUtil
{
    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://192.168.0.110:8080/";
    static CookieStore cookieStore = new BasicCookieStore();
    static HttpContext localContext = new BasicHttpContext();
    
    public static void setContext()
    {
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public void destoyHttp()
    {
    	httpClient.getConnectionManager().shutdown();
    }
    
    public static String getRequest(String url)
        throws Exception
    {
        HttpGet get = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(get);
        if (httpResponse.getStatusLine()
            .getStatusCode() == 200)
        {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return null;
    }

   
    public static String postRequest(String url, Map<String ,String> rawParams)throws Exception
    {

        setContext();
    	HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for(String key : rawParams.keySet())
        {
            params.add(new BasicNameValuePair(key , rawParams.get(key)));
        }
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(post, localContext);
        Log.d("ret code", (new Integer(httpResponse.getStatusLine().getStatusCode())).toString());
        if (httpResponse.getStatusLine().getStatusCode() == 200)
        {

            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("Local cookie: " + cookies.get(i));
            }

        	return "0";
        }
        else
        {
        	
        }
        return null;
    }
}