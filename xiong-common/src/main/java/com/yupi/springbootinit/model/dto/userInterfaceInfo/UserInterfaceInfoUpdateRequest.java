package com.yupi.springbootinit.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新请求
 *
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    /**
     * id主键
     */
    @TableId
    private Long id;


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


    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除0 - 正常，1-删除
     */
    //逻辑删除
    @TableLogic
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}