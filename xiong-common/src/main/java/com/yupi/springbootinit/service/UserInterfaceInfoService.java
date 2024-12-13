package com.yupi.springbootinit.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoVO;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2024-11-01 09:24:53
*/
@Service
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {


    void validInterfaceInfo(UserInterfaceInfo UserInterfaceInfo, boolean add);

    UserInterfaceInfoVO getUserInterfaceInfoVO(UserInterfaceInfo UserInterfaceInfo, HttpServletRequest request);

    boolean invokeCount(long interfaceInfoId, long userId);

    List<UserInterfaceInfo> selectSumInfo(Integer limit);



}
