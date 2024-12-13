package com.xiongda.xiongclientsdk.model;

import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ApiKey {

    /**
     * 获取随机天气
     */
    String apiKey = "a46d04877caae634827464a0ffa8a867";
    String apiUrl = "http://apis.juhe.cn/simpleWeather/query";




}
