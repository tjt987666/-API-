package com.yupi.springbootinit.model.dto.InterfaceInfo;

import lombok.Data;

/**
 * 创建请求
 *
 */
@Data
public class InterfaceInfoInvokerRequest{

    private Long id;

    /**
     * 请求参数
     */
    private String useRequestParams;

}