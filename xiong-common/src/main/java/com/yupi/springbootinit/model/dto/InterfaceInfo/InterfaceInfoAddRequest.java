package com.yupi.springbootinit.model.dto.InterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {
    /**
     * 接口名称
     */

    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求类型 (GET, POST, PUT, DELETE)
     */
    private String method;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;



    /**
     * 请求参数
     */
    private String requestParams;

}