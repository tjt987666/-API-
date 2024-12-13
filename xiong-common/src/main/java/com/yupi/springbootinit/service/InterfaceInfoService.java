package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.vo.InterfaceInfoVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-10-21 14:27:10
*/
@Service
public interface InterfaceInfoService extends IService<InterfaceInfo> {
     void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

     InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);
}
