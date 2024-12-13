package com.yupi.springbootinit.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.vo.InterfaceInfoVO;
import com.yupi.springbootinit.model.vo.InterfaceInfototalVO;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
public class AnalysisContrcoller {

    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;


    @Autowired
    private InterfaceInfoService interfaceInfoService;


    /**
     * 获取调用次数最多的接口信息列表
     * 通过用户接口信息表查询调用参数最多的接口ID，在管关联查询接口详细信息
     * @return
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> getTopInvokeInterfaceInfo(){

        // 查询调用接口次数最多的接口信息列表
        List<UserInterfaceInfo> userInterfaceInfoslist = userInterfaceInfoService.selectSumInfo(3);
        // 将接口信息按照接口ID分组，便于关联查询
        Map<Long, List<UserInterfaceInfo>> collect = userInterfaceInfoslist.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceinfoid));
        // 创建查询接口信息的条件包装器
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        // 设置查询条件，使用接口信息ID在接口信息映射中的键集合进行条件匹配
        queryWrapper.in("id", collect.keySet());
        // 调用接口信息服务的list方法，传入条件包装器，获取符合条件的接口信息列表
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        // 判断是否为空
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // todo 我觉得应该是InterfaceInfoVO不是InterfaceInfototalVO

        //  构建接口信息VO列表，使用流式处理将接口信息映射为接口信息VO对象，并加入列表中
        List<InterfaceInfoVO> interfaceInfoList = list.stream().map(interfaceInfo -> {
            // 创建一个新的接口信息VO对象
            InterfaceInfoVO intfaceinfototalVO = new InterfaceInfoVO();
            // 将接口信息复制到接口信息VO对象中
            BeanUtil.copyProperties(interfaceInfo, intfaceinfototalVO);
            // 从接口信息ID对应的映射中获取调用次数
            Integer totalnum = collect.get(interfaceInfo.getId()).get(0).getTotalnum();
            // 将调用次数设置到接口信息VO对象中
            intfaceinfototalVO.setTotalNum(totalnum);
            // 返回构建好的接口信息VO对象
            return intfaceinfototalVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(interfaceInfoList);
    }


}
