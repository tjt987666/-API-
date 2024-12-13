package com.xiongda.xiongclientsdk;


import com.xiongda.xiongclientsdk.cilent.XiongdaClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("xiong.client")
@Data
@ComponentScan
public class XiongdaClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public XiongdaClient xiongApiClient(){
       return new XiongdaClient(accessKey,secretKey);

    }


}


