package com.suddenly.util.address;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhan
 * @date 2020/04/08 17:19:14
 * @description 地址解析工具类
 */
public class AddressUtil {

    /**
     * 解析地址
     * @param address
     * @return
     */
    public static Map<String,String> getAddressInfo(String address) {
        //1级 省 自治区  2级 市 自治州 地区 3级：区县市旗(镇？)
        String province = null, city = null, provinceAndCity = null, town = null ;
        Map<String, String> row = new LinkedHashMap<>();
        List<Map<String, String>> table = new ArrayList<>();
        Map<String,String> resultMap = new HashMap<>(4);

        if (address.startsWith("香港特别行政区")) {
            resultMap.put("province","香港");
            return resultMap;
        } else if (address.contains("澳门特别行政区")) {
            resultMap.put("province","澳门");
            return resultMap;
        } else if (address.contains("台湾")) {
            resultMap.put("province","台湾");
            return resultMap;
        } else {
            //普通地址
            String regex = "((?<provinceAndCity>[^市]+市|.*?自治州|.*?区|.*县)(?<town>[^区]+区|.*?市|.*?县|.*?路|.*?街|.*?道|.*?镇|.*?旗)(?<detailAddress>.*))";
            Matcher m = Pattern.compile(regex).matcher(address);
            while (m.find()) {
                provinceAndCity = m.group("provinceAndCity");
                String regex2 = "((?<province>[^省]+省|.+自治区|上海市|北京市|天津市|重庆市|上海|北京|天津|重庆)(?<city>.*))";
                Matcher m2 = Pattern.compile(regex2).matcher(provinceAndCity);
                while (m2.find()) {
                    province = m2.group("province");
                    row.put("province", province == null ? "" : province.trim());
                    city = m2.group("city");
                    row.put("city", city == null ? "" : city.trim());
                }
                town = m.group("town");
                row.put("town", town == null ? "" : town.trim());
                table.add(row);
            }
        }
        if (table != null && table.size() > 0) {
            if (StringUtils.isNotBlank(table.get(0).get("province"))) {
                province = table.get(0).get("province");
                //对自治区进行处理
                if (province.contains("自治区")) {
                    if (province.contains("内蒙古")) {
                        province = province.substring(0,4);
                    }  else {
                        province = province.substring(0,3);
                    }

                }
            }
            if (StringUtils.isNotBlank(province)) {
                if (StringUtils.isNotBlank(table.get(0).get("city"))) {
                    city = table.get(0).get("city");
                    if (city.equals("上海市") || city.equals("重庆市") || city.equals("北京市") || city.equals("天津市")) {
                        province = table.get(0).get("city");
                    }
                }

                else if (province.equals("上海市") || province.equals("重庆市") || province.equals("北京市") || province.equals("天津市")) {
                    city = province;
                }
                if (StringUtils.isNotBlank(table.get(0).get("town"))) {
                    town = table.get(0).get("town");
                }
                province = province.substring(0,province.length() - 1);

            }

        } else {
            return resultMap;
        }
        resultMap.put("province",province);
        resultMap.put("city",city);
        resultMap.put("district",town);

        return resultMap;
    }

    public static void main(String[] args) {
//        Map<String, String> map = getAddressInfo("广东省深圳市南山区东滨路205号");
//        Map<String, String > map = getAddressInfo("上海市虹口区飞虹路518号");
//        Map<String, String > map = getAddressInfo("河北省廊坊市三河市燕顺路1140号");
//        Map<String, String> map = getAddressInfo("香港特别行政区油尖旺区广华街58号");
//        Map<String, String> map = getAddressInfo("黑龙江省大兴安岭地区呼玛县合兴街");
//        Map<String, String> map = getAddressInfo("江苏省南京市江宁区202县道");
//        Map<String, String> map = getAddressInfo("海南省陵水黎族自治县提蒙大道215号");

//        Map<String, String> map = getAddressInfo("山东省烟台市龙口市062县道");
//        Map<String, String> map = getAddressInfo("新疆维吾尔自治区乌鲁木齐市沙依巴克区阿里山街");
//        Map<String, String> map = getAddressInfo("内蒙古自治区呼伦贝尔市鄂温克族自治旗");


        Map<String, String> map = getAddressInfo("黑龙江省大兴安岭地区呼玛县合兴街");
        System.out.println(map);
        map.entrySet().stream().forEach(item -> {
            System.out.println(item.getKey()+":"+item.getValue());
        });


        List<Map<String, String>> list = addressResolution("黑龙江省大兴安岭地区呼玛县合兴街");
        System.out.println(list);

    }



    /**
     * 解析地址
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        // java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher Pattern
        // 一个Pattern是一个正则表达式经编译后的表现模式。 Matcher
        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        // 首先一个Pattern实例订制了一个所用语法与PERL的类似的正则表达式经编译后的模式，然后一个Matcher实例在这个给定的Pattern实例的模式控制下进行   *	字符串的匹配工作。

        // (?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?表示一个模块 最后的问号表示可以为空

        List<Map<String, String>> table = new ArrayList<Map<String, String>>();
        String province = null, city = null, county = null, town = null, village = null;
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)" +
                "(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)" +
                "(?<county>[^区]+区|.*?市|.*?县|.+旗|.+海域|.+岛)?" +
                "(?<town>[^区]+区|.+镇|.*?路|.*?街|.*?道|)?" +
                "(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        while(m.find()){
            Map<String,String> row = new LinkedHashMap<String, String>();
            province = m.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            row.put("village", village == null ? "" : village.trim());
            table.add(row);
        }
        return table;
    }



}
