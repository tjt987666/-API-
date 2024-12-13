package com.zking.xiongda.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Data
@Component
public class API {
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

}
