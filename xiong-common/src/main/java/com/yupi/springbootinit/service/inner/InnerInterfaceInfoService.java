package com.yupi.springbootinit.service.inner;

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
public interface InnerInterfaceInfoService {

     /**
      * 数据库中查询模拟接口是否存在
      */
     InterfaceInfo getInterfaceInfo(String path, String method);
}
