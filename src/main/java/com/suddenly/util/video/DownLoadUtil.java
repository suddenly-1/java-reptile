package com.suddenly.util.video;

import com.suddenly.util.date.MyDateUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class DownLoadUtil {
    public static String filePath = "D:\\MyFolder\\111\\DownLoadTest\\demo.mp4"; //文件保存地址
    public static String fileUrl = "https://ugcws.video.gtimg.com/uwMROfz2r57AoaQXGdGnCmddJ6n7RsTqDrcQApy_tdLk6-3k/shg_93386895_50001_0191a03a3eca33b192a4bb3910ed0136.f20611100.1.mp4?sdtfrom=v1010&guid=f8bded9098376dc20ff78c9b8022245c&vkey=1CF82ED51945DD7E564D4D305F132D778876CC1ADBD7FCC032D985A48D99D9C9678E2E9C282E46ED1B47C2E60F115FB2BC4DC7A84C1E661CA393E0644780FA2837713B906C960EBBAF277C5D70F8269C579E031DE6CDBA7A09233751BC06ADA9F514F84B32D12137B7BAC52B08438C76D138557759C74A898601846C3668200B";//文件地址
    public static int threadCount = 5; //线程数量
    public static int fileLength = 0; //文件大小
    public static Thread[] threadList = new Thread[threadCount];

    public DownLoadUtil() {
    }

    public DownLoadUtil(int threadCount) {//有参构造
        this.threadCount = threadCount;
    }

    public static void main(String[] args) throws Exception {
        String startDate = MyDateUtil.dateConvertString(new Date());
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //创建连接对象
        conn.setConnectTimeout(5000);//请求超时时间

        int code = conn.getResponseCode();
        System.out.println("服务器响应码" + code);
        if (code == 200) {//响应正常
            fileLength = conn.getContentLength(); //获取文件大小
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(filePath, "rwd"); //断点续传的关键
            raf.setLength(fileLength);

            int blockSize = fileLength / threadCount; //计算每个线程需要下的长度
            for (int i = 0; i < threadCount; i++) {
                int startSize = i * blockSize; //当前线程需要下载的开始位置
                int endSize = (i + 1) * blockSize - 1;//当前线程需要下载的结束位置
                if (1 + i == threadCount) { //最后一个线程的结尾赋值文件大小
                    endSize = fileLength;
                }
                threadList[i] = new DownThread(filePath, fileUrl, "线程" + i, startSize, endSize);
                threadList[i].start();
            }

            while (DownUtil.downOver) {
                Thread.sleep(500); //间隔0.5秒计算一下
                if (DownUtil.downLength == fileLength) {
                    DownUtil.downOver = false;
                    System.out.println("下载完成：100%");
                } else {
                    System.out.println("已经下载了：" + ((int) (float) DownUtil.downLength / (float) fileLength * 100) + "%");
                }
            }

        } else {
            System.out.println("服务器响应失败" + code);
        }

        String endDate = MyDateUtil.dateConvertString(new Date());
        String timeDifference = MyDateUtil.getTimeDifference(startDate, endDate);
        System.out.println(timeDifference);
    }
}