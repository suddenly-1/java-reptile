package com.suddenly.util;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class MyUtil {

    private static CookieStore cookieStore = new BasicCookieStore();

    public static String getCompanyPath(String companyName){
        cookieStore = new BasicCookieStore();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(":authority: ", "www.qcc.com");
        headerMap.put(":method: ", "GET");
        headerMap.put(":path: ", "/gongsi_mindlist?type=mind&searchKey=%E6%B2%88%E9%98%B3%E4%B8%87%E9%91%AB%E6%BA%90%E5%95%86%E8%B4%B8%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&searchType=0&suggest=1");
        headerMap.put(":scheme: ", "https");
        headerMap.put("accept", "*/*");
        headerMap.put("accept-encoding", "gzip, deflate, br");
        headerMap.put("accept-language", "zh-CN,zh;q=0.9");
        headerMap.put("cookie", "zg_did=%7B%22did%22%3A%20%22172a65d4b187e-021e3dbd07fff6-67e1b3f-144000-172a65d4b1987d%22%7D; UM_distinctid=172a65d4e00362-04c1d1f0c5ee99-67e1b3f-144000-172a65d4e01382; _uab_collina=159192903336791768825203; QCCSESSID=7d4cc4keusjkpja062drnhh0r4; CNZZDATA1254842228=963176721-1596184444-https%253A%252F%252Fwww.qcc.com%252F%7C1596184444; Hm_lvt_78f134d5a9ac3f92524914d0247e70cb=1596185373; hasShow=1; acw_tc=65e21c1b15961872352861936ec6a29e89ea22ff6c6a8cb9cdbadc3bd9; Hm_lpvt_78f134d5a9ac3f92524914d0247e70cb=1596187313; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201596185362117%2C%22updated%22%3A%201596187313562%2C%22info%22%3A%201596093432306%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%2C%22cuid%22%3A%20%226c5023cac9ef59ed7c715c1c60ce65ab%22%7D");
        headerMap.put("referer", "https://www.qcc.com/firm/423F7IS.shtml");
        headerMap.put("sec-fetch-mode", "cors");
        headerMap.put("sec-fetch-site", "same-origin");
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        headerMap.put("x-requested-with", "XMLHttpRequest");
        HttpResultWithCookies httpResultWithCookies = httpClientUtils.doGet("https://www.qcc.com/gongsi_mindlist?type=mind&searchKey=" + companyName + "&searchType=0&suggest=1", "UTF-8", cookieStore, headerMap);
        String s = httpResultWithCookies.getResult();

        Document document = Jsoup.parse(s);
        Elements elementsByClass = document.getElementsByClass("list-group-item keyMoveItem");
        return elementsByClass.attr("href");
    }

    public static String getIndustry(String companyPath){
        cookieStore = new BasicCookieStore();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(":authority", "www.qcc.com");
        headerMap.put(":method", "GET");
        headerMap.put(":path", "/firm/423F7IS.shtml");
        headerMap.put(":scheme", "https");
        headerMap.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        headerMap.put("accept-encoding", "gzip, deflate, br");
        headerMap.put("accept-language", "zh-CN,zh;q=0.9");
        headerMap.put("cache-control", "max-age=0");
        headerMap.put("cookie", "zg_did=%7B%22did%22%3A%20%22172a65d4b187e-021e3dbd07fff6-67e1b3f-144000-172a65d4b1987d%22%7D; UM_distinctid=172a65d4e00362-04c1d1f0c5ee99-67e1b3f-144000-172a65d4e01382; _uab_collina=159192903336791768825203; QCCSESSID=7d4cc4keusjkpja062drnhh0r4; CNZZDATA1254842228=963176721-1596184444-https%253A%252F%252Fwww.qcc.com%252F%7C1596184444; Hm_lvt_78f134d5a9ac3f92524914d0247e70cb=1596185373; hasShow=1; acw_tc=65e21c1b15961872352861936ec6a29e89ea22ff6c6a8cb9cdbadc3bd9; Hm_lpvt_78f134d5a9ac3f92524914d0247e70cb=1596187313; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201596185362117%2C%22updated%22%3A%201596187313562%2C%22info%22%3A%201596093432306%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%2C%22cuid%22%3A%20%226c5023cac9ef59ed7c715c1c60ce65ab%22%7D");
        headerMap.put("sec-fetch-mode", "navigate");
        headerMap.put("sec-fetch-site", "none");
        headerMap.put("sec-fetch-user", "?1");
        headerMap.put("upgrade-insecure-requests", "1");
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        HttpResultWithCookies httpResultWithCookies2 = httpClientUtils.doGet("https://www.qcc.com" + companyPath, "UTF-8", cookieStore, headerMap);
        String s = httpResultWithCookies2.getResult();

        Document document = Jsoup.parse(s);
        Elements elementsByClass = document.getElementsByClass("tb");
        String industry = "";
        for(Element e : elementsByClass){
            if ("所属行业".equals(e.text())) {
                System.out.println("方法：" + e.text());
                industry = e.nextElementSibling().text();
            }
        }
        return industry;
    }



}
