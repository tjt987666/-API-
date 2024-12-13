package com.yupi.springbootinit.model.dto.InterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
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
     * 是否删除 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDelete;


    /**
     * 请求参数
     */
    private String requestParams;



}