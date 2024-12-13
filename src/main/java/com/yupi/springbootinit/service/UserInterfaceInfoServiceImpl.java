package com.yupi.springbootinit.service;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.mapper.UserInterfaceInfoMapper;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoVO;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2024-11-01 09:24:53
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
implements UserInterfaceInfoService {


    @Override
    public void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long interfaceinfoid = userInterfaceInfo.getInterfaceinfoid();
        // 创建时，参数不能为空
        if (interfaceinfoid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口id不能为空");
        }
        // 更新时，id必须为正
        if (!add && interfaceinfoid <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

    }

    @Override
    public UserInterfaceInfoVO getUserInterfaceInfoVO(UserInterfaceInfo UserInterfaceInfo, HttpServletRequest request) {
        UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
        BeanUtil.copyProperties(UserInterfaceInfo, userInterfaceInfoVO);

        return userInterfaceInfoVO;
    }

    @Override
    @Transactional
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 检查传入的参数是否有效
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 查询当前用户的 totalNum
        UserInterfaceInfo userInterfaceInfo = this.getOne(new QueryWrapper<UserInterfaceInfo>()
                .eq("interfaceInfoId", interfaceInfoId)
                .eq("userId", userId));

        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户接口信息不存在");
        }


        // 检查 totalNum 是否足够
        if (userInterfaceInfo.getTotalnum() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "调用次数不足");
        }

        // 更新 invokeNum 和 totalNum
        userInterfaceInfo.setInvokenum(userInterfaceInfo.getInvokenum() - 1);
        userInterfaceInfo.setTotalnum(userInterfaceInfo.getTotalnum() + 1);

        // 执行更新操作
        boolean update = this.updateById(userInterfaceInfo);

        // 返回更新是否成功的结果
        return update;
    }

    @Override
    public List<UserInterfaceInfo> selectSumInfo(Integer limit) {
        return this.baseMapper.selectSumInfo(limit);
    }
}
