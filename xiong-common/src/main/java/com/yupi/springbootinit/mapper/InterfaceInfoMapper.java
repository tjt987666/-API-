package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.springbootinit.model.dto.InterfaceInfo.InterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @author Administrator
* @description 针对表【interface_info(接口信息表)】的数据库操作Mapper
* @createDate 2024-10-21 14:27:10
* @Entity com.yupi.springbootinit.model.entity.InterfaceInfo
*/
@Component
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {
    List<InterfaceInfo> selectAll(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

}





