package com.yupi.springbootinit.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class InterfaceInfoVO {
    /**
     * 主键ID
     */
    private Integer id;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Integer userId;

    /**
     * 接口调用次数
     */
    //声明不是数据库字段
    @TableField(exist = false)
    private Integer totalNum;

}

