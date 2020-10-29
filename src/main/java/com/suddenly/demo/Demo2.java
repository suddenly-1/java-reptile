package com.suddenly.demo;

import com.suddenly.util.HttpClientUtils;
import com.suddenly.util.HttpResultWithCookies;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Demo2 {
    private static CookieStore cookieStore = new BasicCookieStore();
    public static void main(String[] args) throws IOException {
        Document document = null;
        try {
            document = Jsoup.connect("https://so.csdn.net/so/search/s.do?p=1&q=jsoup&t=&viparticle=&domain=&o=&s=&u=&l=&f=").get();
            System.out.println(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements select = document.select(".flag_icon");
        List<String> hrefs = new ArrayList<String>();
        for(Element e : select){
            String href = e.nextElementSibling().attr("href");
            hrefs.add(href);
        }
        List<String> names = new ArrayList<String>();

        hrefs.stream().forEach(href -> {
            String url = href.substring(0,href.indexOf("?"));
            String opsRequestMisc = href.substring(href.indexOf("ops_request_misc=") + "ops_request_misc=".length());
            String opsRequestMiscResult = opsRequestMisc.substring(0, opsRequestMisc.indexOf("&request_id="));
            String requestId = href.substring(href.indexOf("request_id=") + "request_id=".length());
            String requestIdResult = requestId.substring(0, requestId.indexOf("&"));
            String utmMedium = href.substring(href.indexOf("utm_medium=") + "utm_medium=".length());
            String utmMediumResult = utmMedium.substring(0, utmMedium.indexOf("&utm_term"));
            if (href.indexOf("download") == -1) {
                String name = test2(url, opsRequestMiscResult, requestIdResult, utmMediumResult, "jsoup");
                names.add(name);
            } else {
                String name = null;
                try {
                    name = test3(url, opsRequestMiscResult, requestIdResult, utmMediumResult, "jsoup");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                names.add(name);
            }
        });
        System.out.println(hrefs.size());
        names.stream().forEach(System.out::println);


//        String a = "https://download.csdn.net/download/nangongyanya/9458997";
//        String b = "%257B%2522request%255Fid%2522%253A%2522159678025919725211945947%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D";
//        String c = "159678025919725211945947";
//        String d = "distribute.pc_search_result.none-task-download-2~all~first_rank_ecpm_v3~pc_rank_v2-20-9458997.first_rank_ecpm_v3_pc_rank_v2";
//        String name = test3(a, b, c, d, "Jsoup");
//        System.out.println(name);



    }

    public static List<String> test1(Integer page, String name){
        cookieStore = new BasicCookieStore();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        Map<String, String> headerMap = new HashMap<>();
        HttpResultWithCookies httpResultWithCookies2 = httpClientUtils.doGet("https://so.csdn.net/so/search/s.do?p=1&q=jsoup&t=&viparticle=&domain=&o=&s=&u=&l=&f=", "UTF-8", cookieStore, headerMap);
        String s = httpResultWithCookies2.getResult();

        Document parse = Jsoup.parse(s);
        Elements select = parse.select(".flag_icon");
        List<String> hrefs = new ArrayList<String>();
        for(Element e : select){
            String href = e.nextElementSibling().attr("href");
            hrefs.add(href);
        }
        return hrefs;
    }

    public static String test2(String http, String opsRequestMisc, String request_id, String utmMedium, String utmTerm){
        cookieStore = new BasicCookieStore();
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("ops_request_misc", opsRequestMisc);
        headerMap.put("request_id", request_id);
        headerMap.put("biz_id", "0");
        headerMap.put("utm_medium", utmMedium);
        headerMap.put("utm_term", utmTerm);
        HttpResultWithCookies httpResultWithCookies2 = httpClientUtils.doGet(http, "UTF-8", cookieStore, headerMap);
        String s = httpResultWithCookies2.getResult();

        Document parse = Jsoup.parse(s);
        Elements elementsByClass = parse.getElementsByClass("follow-nickName");
        return elementsByClass.text();
    }

    public static String test3(String http, String opsRequestMisc, String requestId, String utmMedium, String utmTerm) throws IOException {
        Document document = Jsoup.connect(http + "?" + opsRequestMisc + "&" + requestId + "&biz_id=1&" + utmMedium + "&" + utmTerm + "&spm=1018.2118.3001.4187").get();
        Elements select = document.select(".name");
        return select.text();
    }



}
