package com.yupi.springbootinit.service.inner;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoVO;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
*/
@Service
public interface InnerUserInterfaceInfoService  {

    /**
     * 统计接口调用次数
     */
    boolean InvokeCount(long interfaceInfoId, long userId);


    /**
     *获取到用户ID，根据用户ID判断该用户是否还有调用次数
     */
    boolean checkInterfaceInfo(long userId);




}
