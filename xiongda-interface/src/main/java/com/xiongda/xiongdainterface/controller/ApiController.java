package com.xiongda.xiongdainterface.controller;

import com.xiongda.xiongclientsdk.config.APIConfig;
import com.xiongda.xiongclientsdk.model.ApiKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import static com.xiongda.xiongclientsdk.config.APIConfig.params;

@RestController
@RequestMapping("/apiInterface")
@Slf4j
public class ApiController {

    @RequestMapping("/weather")
    public String getWeather(@RequestBody ApiKey apiKey, HttpServletRequest request) {

        log.info("获取天气key:"+apiKey);

        // 获取头部信息
        System.out.println(request);
        String input = request.getHeader("body");


        // 响体中获取到用户信息
//        String input = request.getParameter("name");

        HashMap<String, String> map = new HashMap<>();
        map.put("key", apiKey.getApiKey());
        map.put("city", input);


        try {
            URL url = new URL(String.format("%s?%s", apiKey.getApiKey(), params(map)));
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
