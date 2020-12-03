package com.suddenly.util.address;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressUtils {

    /**
     * 解析地址
     *
     * @param address 地址
     * @return ；
     */
    public static List<Map<String, String>> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)" +
                "(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?区|.*?县)" +
                "(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province, city, county, town, village;
        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> row;
        while (m.find()) {
            row = new LinkedHashMap<>();
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

    /**
     * 判断是否是直辖市特别行政区
     *
     * @param province 省
     * @return ；
     */

    public static boolean isSpecialAreas(String province) {
        return !ProvinceEnum.SHANGHAI.getMessage().equals(province) && !ProvinceEnum.BEIJING.getMessage().equals(province)
                && !ProvinceEnum.TIANJIN.getMessage().equals(province) && !ProvinceEnum.CHONGQING.getMessage().equals(province)
                && !ProvinceEnum.HONG_KONG.getMessage().equals(province) && !ProvinceEnum.MACAO.getMessage().equals(province);
    }

}
