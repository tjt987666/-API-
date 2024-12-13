package com.yupi.springbootinit.model.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import lombok.Data;

import java.util.Date;

@Data
public class InterfaceInfototalVO extends InterfaceInfo {
    private Integer totalNum;

}

