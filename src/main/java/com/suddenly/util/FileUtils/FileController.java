package com.suddenly.util.FileUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    private Logger logger = LogManager.getLogger(getClass());


    /**
     * 上传多个文件
     * @param files 文件数组
     * http://localhost:8081/file/fileUploads
     */
    @PostMapping(value = "/fileUploads")
    @ResponseBody
    public String fileUploads(@RequestParam(value = "files") MultipartFile[] files) {
        try {
            ArrayList<String> fileNames = new ArrayList<>();
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    long fileSize = file.getSize();//上传文件的大小, 单位为字节.
                    if (fileSize > 50 * 1024 * 1024) {
                        logger.error("文件过大");
                        return "文件过大";
                    }
                    String fileName = file.getOriginalFilename();  // 文件名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
                    String rand = UUID.randomUUID().toString().replaceAll("-", "");
                    fileName = rand + suffixName; // 新文件名
                    File direction = new File("D:\\DownLoadTest");
                    File savedFile = new File(direction, fileName);
                    if (!savedFile.getParentFile().exists()) {
                        savedFile.getParentFile().mkdirs();
                    }
                    savedFile.createNewFile();
                    file.transferTo(savedFile);
                    fileNames.add(fileName);
                }
            }
            return fileNames.toString();
        } catch (Exception e) {
            logger.error("上传文件失败");
            return "失败";
        }
    }


    /**
     * 上传单个文件
     * @param file 文件
     */
    @PostMapping(value = "/fileUpload")
    @ResponseBody
        public String fileUpload(@RequestParam(value = "file") MultipartFile file) {
        try {
            long fileSize = file.getSize();//上传文件的大小, 单位为字节.
            if (fileSize > 50 * 1024 * 1024) {
                logger.error("文件过大");
                return "文件过大";
            }
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String rand = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = rand + suffixName; // 新文件名
            File direction = new File("D:\\DownLoadTest");
            File savedFile = new File(direction, fileName);
            if (!savedFile.getParentFile().exists()) {
                savedFile.getParentFile().mkdirs();
            }
            savedFile.createNewFile();
            file.transferTo(savedFile);
            return fileName;
        } catch (Exception e) {
            logger.error("上传文件失败");
            return "失败";
        }
    }






//    前端 vue
//    downloadUrl(payload) {
//        return axios({
//                responseType: 'blob',
//                url: `${baseUrl}/download/file?url=${payload}`,
//        method: 'get'
//    })
//    }



    /**
     * 下载文件
     * @param url 文件名
     */
    @GetMapping("/fileDownload")
    @ResponseBody
    public String fileDownload(HttpServletResponse response, @RequestParam String url) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(url, "UTF-8"));
            //4.获取要下载的文件输入流

            in = new FileInputStream("D:\\DownLoadTest" + "/" + url);

            int len = 0;
            //5.创建数据缓冲区
            byte[] buffer = new byte[1024];
            //6.通过response对象获取OutputStream流
            out = response.getOutputStream();
            //7.将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            return "成功";
        } catch (Exception e) {
            logger.error("下载文件失败");
            return "失败";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件流失败");
            }
        }
    }










    /**
     * https中获取文件流下载
     * @param url 文件地址
     */
    @GetMapping("/file/download")
    @ResponseBody
    public String fileDownloadHttpsURL(HttpServletResponse response, @RequestParam String url) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            //4.获取要下载的文件输入流

            in = getInputStreamByUrlHttps(url);

            int len = 0;
            //5.创建数据缓冲区
            byte[] buffer = new byte[1024];
            //6.通过response对象获取OutputStream流
            out = response.getOutputStream();
            //7.将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            return "成功";
        } catch (Exception e) {
            logger.error("下载文件失败");
            return "失败";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件流失败");
            }
        }
    }


    /**
     * http中获取文件流
     */
    public InputStream getInputStreamByUrlHttp(String strUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            logger.error("getInputStreamByUrl 异常,exception is {}", e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * https中获取文件流
     */
    public InputStream getInputStreamByUrlHttps(String urlStr) {
        HttpsURLConnection conn = null;
        try {
            // 创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = { new MyX509TrustManager() };
            // 初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            // url对象
            URL url = new URL(urlStr);
            // 打开连接
            conn = (HttpsURLConnection) url.openConnection();
            /**
             * 这一步的原因: 当访问HTTPS的网址。您可能已经安装了服务器证书到您的JRE的keystore
             * 但是服务器的名称与证书实际域名不相等。这通常发生在你使用的是非标准网上签发的证书。
             *
             * 解决方法：让JRE相信所有的证书和对系统的域名和证书域名。
             *
             * 如果少了这一步会报错:java.io.IOException: HTTPS hostname wrong: should be <localhost>
             */
            conn.setHostnameVerifier(new MyX509TrustManager().new TrustAnyHostnameVerifier());
            // 设置一些参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();

            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            logger.error("getInputStreamByUrl 异常,exception is {}", e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }






}
