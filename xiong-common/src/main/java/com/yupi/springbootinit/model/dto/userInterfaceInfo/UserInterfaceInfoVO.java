package com.yupi.springbootinit.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class UserInterfaceInfoVO {

    /**
     * id主键
     */
    @TableId
    private Long id;

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

    /**
     * 创建时间
     */
    private Date createtime;

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
