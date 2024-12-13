package com.yupi.springbootinit.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    /**
     * 调用用户id
     */
    private Long userid;

    /**
     * 接口id
     */
    private Long interfaceinfoid;

    /**
     * 总调用次数
     */
    private Integer totalnum;

    /**
     * 调用次数
     */
    private Integer invokenum;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}