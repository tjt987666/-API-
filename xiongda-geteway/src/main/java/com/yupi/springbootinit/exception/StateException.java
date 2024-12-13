package com.yupi.springbootinit.exception;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Component
public class StateException {

    /**
     * 处理异常404和接口不存在等信息
     *
     * @param response 响应对象
     * @return Mono<Void> 表示异步操作完成的信号
     */
    public Mono<Void> apiNotFound(ServerHttpResponse response,String msg) {
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        DataBuffer dataBuffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }




    // 写一个方法用于抛出403的状态码
    public Mono<Void> forbidden(ServerHttpResponse response) {
        // 设置响应状态码位403
        response.setStatusCode(HttpStatus.FORBIDDEN);
        // 返回处理完成的响应
        return response.setComplete();
    }

}
