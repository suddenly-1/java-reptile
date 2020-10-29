package com.suddenly.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {


    /**
     * Get请求
     *
     * @param url 请求URL
     * @return
     */
    public  String doGet(String url) {
        CloseableHttpClient httpclient = null ;
        CloseableHttpResponse response = null ;
        String result = "" ;
        try {
            httpclient = HttpClients.createDefault() ;
            HttpGet httpGet = new HttpGet(url) ;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build() ;
            httpGet.setConfig(requestConfig) ;
            response = httpclient.execute(httpGet) ;
            HttpEntity entity = response.getEntity() ;
            result = EntityUtils.toString(entity) ;
        } catch (ClientProtocolException e) {
            e.printStackTrace() ;
        } catch (IOException e) {
            e.printStackTrace() ;
        } finally {
            try {
                if (null != response) {
                    response.close() ;
                }
                if (null != httpclient) {
                    httpclient.close() ;
                }
            } catch (IOException e) {
                e.printStackTrace() ;
            }
        }
        return result ;
    }

    /**
     * doGet版本2
     * @param url
     * @return
     */
    public String doGet2(String url) {
        String result="";
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity, "utf-8");
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != response) {
                    response.close() ;
                }
                if (null != httpClient) {
                    httpClient.close() ;
                }
            } catch (IOException e) {
                e.printStackTrace() ;
            }
        }
        return result;
    }


    /**
     * doGet请求 会返回携带的cookies
     *
     * @param url 请求URL,cookies
     * @return
     */
    public static HttpResultWithCookies doGet(String url,String charset,CookieStore cookies, Map<String, String> headerMap) {
        HttpResultWithCookies result = new HttpResultWithCookies();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null ;
        HttpGet httpGet = null;
        try {
            //cookies = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookies).build();
            httpGet = new HttpGet(url);
            if(headerMap != null) {
                Set<Map.Entry<String, String>> entryseSet = headerMap.entrySet();
                for (Map.Entry<String, String> entry : entryseSet) {
                    httpGet.addHeader(entry.getKey(),entry.getValue());
                }
            }
            response = httpClient.execute(httpGet) ;
            HttpEntity entity = response.getEntity() ;
            result.setResult(EntityUtils.toString(entity));
            result.setCookies(cookies);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 带参数的Post请求
     *
     * @param url 请求URL
     * @param paramMap 请求参数
     * @return
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        CloseableHttpClient httpclient = null ;
        CloseableHttpResponse response = null ;
        String result = "" ;
        try {
            httpclient = HttpClients.createDefault() ;
            HttpPost httpPost = new HttpPost(url) ;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build() ;
            httpPost.setConfig(requestConfig) ;
            if (null != paramMap && paramMap.size() > 0) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>() ;
                Set<Entry<String, String>> set = paramMap.entrySet() ;
                Iterator<Entry<String, String>> it = set.iterator() ;
                while (it.hasNext()) {
                    Entry<String, String> mapEntry = it.next() ;
                    nvps.add(new BasicNameValuePair(mapEntry.getKey(), new String(mapEntry.getValue().getBytes("UTF-8"), "iso8859-1"))) ;
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")) ;
            }
            response = httpclient.execute(httpPost) ;
            HttpEntity entity = response.getEntity() ;
            result = EntityUtils.toString(entity) ;
        } catch (ClientProtocolException e) {
            e.printStackTrace() ;
        } catch (IOException e) {
            e.printStackTrace() ;
        } finally {
            try {
                if (null != response) {
                    response.close() ;
                }
                if (null != httpclient) {
                    httpclient.close() ;
                }
            } catch (IOException e) {
                e.printStackTrace() ;
            }
        }
        return result ;
    }
    
    /**
     * Get请求 会返回携带的cookies
     *
     * @param url 请求URL
     * @return
     */
    public static HttpResultWithCookies doPost(String url,Map<String, String> map, String charset) {
    	HttpResultWithCookies result = new HttpResultWithCookies();
    	CloseableHttpClient httpClient = null;
    	CloseableHttpResponse response = null ;
        HttpPost httpPost = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost) ;
            HttpEntity entity = response.getEntity() ;
            result.setResult(EntityUtils.toString(entity));
            result.setCookies(cookieStore);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    /**
     * doPost请求 会返回携带的cookies
     *
     * @param url 请求URL,cookies
     * @return
     */
    public static HttpResultWithCookies doPost(String url,Map<String, String> map, String charset,CookieStore cookies) {
    	HttpResultWithCookies result = new HttpResultWithCookies();
    	CloseableHttpClient httpClient = null;
    	CloseableHttpResponse response = null ;
        HttpPost httpPost = null;
        try {
            //CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookies).build();
            httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost) ;
            HttpEntity entity = response.getEntity() ;
            result.setResult(EntityUtils.toString(entity));
            result.setCookies(cookies);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * httpclient post方法
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url,Map<String, String> headers,Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if(headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response!=null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
