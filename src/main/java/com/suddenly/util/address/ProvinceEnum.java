package com.suddenly.util.address;

import lombok.Getter;

/**
 * @author zqy
 */
@Getter
public enum ProvinceEnum {
    /**
     * 特別地區
     */
    SHANGHAI(0, "上海市"),
    BEIJING(1, "北京市"),
    TIANJIN(2, "天津市"),
    CHONGQING(3, "重庆市"),
    HONG_KONG(4, "香港特别行政区"),
    MACAO(5, "澳门特别行政区");

    private final Integer code;
    private final String message;

    ProvinceEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
