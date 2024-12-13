package com.yupi.springbootinit.model.dto.InterfaceInfo;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

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
     * 接口状态 0-关闭 1-开启
     */
    private Integer status;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 请求参数
     */
    private String requestParams;


}
