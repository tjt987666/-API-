package com.yupi.springbootinit.service.inner;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 */
@Service
public interface InnerUserService {

    /**
     * 查看用户是否已经分配给用户ak
     *
     */
    User getInvokerUser(String accessKey);


}
