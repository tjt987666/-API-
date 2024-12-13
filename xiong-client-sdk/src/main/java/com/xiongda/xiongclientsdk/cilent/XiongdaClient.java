package com.xiongda.xiongclientsdk.cilent;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xiongda.xiongclientsdk.model.ApiKey;
import com.xiongda.xiongclientsdk.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import static com.xiongda.xiongclientsdk.utils.SignUtils.getSign;



/**
 * 使用hutool模拟接口，给用户服务调用，返回名字
 */
public class XiongdaClient {

    private static final String GTEWAY_URL = "http://localhost:8001";
    private String accessKey;

    private String secretKey;

    public XiongdaClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }






    public String getName(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result3 = HttpUtil.get(GTEWAY_URL+"/api/name/get", paramMap);
        System.out.println("hutool:" + result3);

        return result3;
    }

    public String postName(@RequestParam String name) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post(GTEWAY_URL+"/api/name/post", paramMap);
        System.out.println("hutool:" + result);


        return result;
    }



    private Map<String,String> getHeaders(String body){
        System.out.println("getHeaders:"+body);

        Map<String,String> headers = new HashMap<>();

        headers.put("accessKey",accessKey); // 用户发送的ak,sk
        // secretKey不明文显示
//        headers.put("secretKey",secretKey);
        // nonce随机数,只能用一次
        headers.put("nonce", RandomUtil.randomNumbers(6));
        // timestamp时间戳
        headers.put("timestamp",String.valueOf(System.currentTimeMillis()));
        // body
        headers.put("body",body); //username
        // sign签名
        headers.put("sign",getSign(body, secretKey));

        return headers;
    }


    public String getUserNmae(@RequestBody User user) {
        String json = JSONUtil.toJsonStr(user);
        System.out.println("Sending request body: " + json); // 打印请求体
        String result2 = HttpRequest.post(GTEWAY_URL+"/api/name/user")
                .header("Content-Type", "application/json; charset=UTF-8")
                .addHeaders(getHeaders(json)) //username
                .body(json)
                .charset("UTF-8") // 明确指定字符编码
                .execute().body();
        System.out.println("hutool:" + result2);

        return result2;
    }


    /**
     * 获取随机天气
     * @param name
     * @return
     */
    public String getWeather(String name) {
        try {
            // 对 name 参数进行 URL 解码
            String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8.toString());

            // 接入接入的天气查询
            ApiKey apiKey = new ApiKey();

            HashMap<String, Object> map = new HashMap<>();
            map.put("key", apiKey.getApiKey());
            map.put("city", apiKey.getApiUrl());
            map.put("name", decodedName);

            String requestBody = JSONUtil.toJsonStr(map);

            Map<String, String> headers = getHeaders(requestBody);

            String weather = HttpRequest.post(GTEWAY_URL + "/api/apiInterface/weather")
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .addHeaders(headers) // ak/sk头部信息
                    .body(requestBody)
                    .charset("UTF-8") // 明确指定字符编码
                    .execute().body(); //头信息，多个头信息多次调用此方法即可

            return weather;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error decoding name parameter: " + e.getMessage();
        }
    }


}
