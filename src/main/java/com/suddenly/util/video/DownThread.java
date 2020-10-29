package com.suddenly.util.video;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownThread extends Thread {

    private String filePath;
    private String urlPath;
    private String threadName;
    private int startSize;
    private int endSize;

    public DownThread(String filePath, String urlPath, String threadName, int startSize, int endSize) {
        this.endSize = endSize;
        this.startSize = startSize;
        this.filePath = filePath;
        this.urlPath = urlPath;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            coon.setRequestProperty("range", "bytes=" + startSize + "-" + endSize); //设置获取下载资源的开始位置和结束位置
            coon.setConnectTimeout(5000);
            if (coon.getResponseCode() == 206) {//响应码   因为上面设置了range 所有响应码是206不再是200
                BufferedInputStream bi = new BufferedInputStream(coon.getInputStream());
                RandomAccessFile raf = new RandomAccessFile(filePath, "rwd"); //断点续传的关键
                raf.seek(startSize); //将写入点移动到当前线程写入开始位置
                byte b[] = new byte[1024];
                int len = 0;
                while ((len = bi.read(b)) > -1) { //循环写入
                    raf.write(b, 0, len);
                    synchronized (DownUtil.class) {//此处涉及到变量同步
                        DownUtil.downLength = DownUtil.downLength + len; //计算当前下载了多少
                    }
                }
                raf.close();
                bi.close();
                System.out.println("thread" + threadName + "下载完成,开始位置" + startSize + ",结束位置" + endSize);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}