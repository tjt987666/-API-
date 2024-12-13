package com.yupi.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2024-11-01 09:24:53
* @Entity generatorpas.domain.UserInterfaceInfo
*/
@Component
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> selectSumInfo(Integer limit);


}
