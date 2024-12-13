package com.zking.xiongda.controller;

import com.zking.xiongda.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AI响应服务接口测试
 */
@RestController
@RequestMapping("/my2")
public class My2Controller {

    @Autowired
    private AIService aiService;

    @RequestMapping("/ai")
    public String getAIData(@RequestParam("input") String input) {
        String tq = "获取" + input + "天气";
        return aiService.getAIData(tq);
    }

    @RequestMapping("/city/coordinates")
    public String getCityCoordinates(@RequestParam("city") String city) {
        String input = "获取" + city + "的坐标";
        return aiService.getAIData(input);
    }

    @RequestMapping("/nickname/generate")
    public String generateNickname(@RequestBody Map<String, String> request) {
        String personality = request.get("personality");
        String input = "根据性格" + personality + "生成随机网名";
        return aiService.getAIData(input);
    }

    @RequestMapping("/zodiac/daily")
    public String getZodiacDaily(@RequestParam("zodiac") String zodiac) {
        String input = "计算" + zodiac + "星座的当天运势";
        return aiService.getAIData(input);
    }

    @RequestMapping("/stock/quote")
    public String getStockQuote(@RequestParam("symbol") String symbol) {
        String input = "获取" + symbol + "股票的最新行情";
        return aiService.getAIData(input);
    }

    @RequestMapping("/news/hotspots")
    public String getNewsHotspots() {
        String input = "获取当前的新闻热点";
        return aiService.getAIData(input);
    }

    @RequestMapping("/company/info")
    public String getCompanyInfo(@RequestBody Map<String, String> request) {
        String companyName = request.get("companyName");
        String input = "查询公司" + companyName + "的信息";
        return aiService.getAIData(input);
    }
}
