package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xiongda.xiongclientsdk.cilent.XiongdaClient;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoAddRequest;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoUpdateRequest;
import com.yupi.springbootinit.model.dto.userInterfaceInfo.UserInterfaceInfoVO;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.UserInterfaceInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 帖子接口
 *d
 */
@RestController
@RequestMapping("/userInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    @Autowired
    private XiongdaClient xiongdaClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param UserInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest UserInterfaceInfoAddRequest, HttpServletRequest request) {
        // 检查请求参数是否为空
        if (UserInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println("请求数据："+UserInterfaceInfoAddRequest);


        // 创建新的帖子对象
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        // 将请求参数复制到帖子对象中
        BeanUtils.copyProperties(UserInterfaceInfoAddRequest, userInterfaceInfo);

        // 验证帖子对象的有效性
        userInterfaceInfoService.validInterfaceInfo(userInterfaceInfo, true);

        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);

        // 添加到数据库
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);

        // 如果保存失败，抛出操作错误异常
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回成功响应，包含新创建的帖子ID
        return ResultUtils.success(result);
    }


    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @Transactional
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println("id数据："+deleteRequest);
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();


        // 判断是否存在
        boolean b = userInterfaceInfoService.removeById(id);
        // 提交事物


        System.out.println("是否已经删除:"+b);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param UserInterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest UserInterfaceInfoUpdateRequest) {
        System.out.println("请求数据："+UserInterfaceInfoUpdateRequest);
        if (UserInterfaceInfoUpdateRequest == null || UserInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        // 更新时间为当前时间
//        userInterfaceInfo.getUpdatetime(new Date());

        BeanUtils.copyProperties(UserInterfaceInfoUpdateRequest, userInterfaceInfo);
        // 参数校验
        userInterfaceInfoService.validInterfaceInfo(userInterfaceInfo, false);
        long id = UserInterfaceInfoUpdateRequest.getId();
        System.out.println("id:"+id);
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        System.out.println("old"+oldUserInterfaceInfo);

        ThrowUtils.throwIf(oldUserInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfoVO> getUserInterfaceInfoVOById(long id, HttpServletRequest request) {
        System.out.println("id:"+id);
        // 检查ID是否有效
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 根据ID获取帖子对象
        UserInterfaceInfo UserInterfaceInfo = userInterfaceInfoService.getById(id);
        System.out.println("old"+UserInterfaceInfo);

        // 如果帖子不存在，抛出未找到错误
        if (UserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 获取帖子的详细视图对象
        UserInterfaceInfoVO UserInterfaceInfoVO = userInterfaceInfoService.getUserInterfaceInfoVO(UserInterfaceInfo, request);

        // 返回成功响应，包含帖子的详细视图对象
        return ResultUtils.success(UserInterfaceInfoVO);
    }


    /**
     * 分页获取列表
     *
     * @param UserInterfaceInfoQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(@RequestBody UserInterfaceInfoQueryRequest UserInterfaceInfoQueryRequest) {
        long current = UserInterfaceInfoQueryRequest.getCurrent();
        long size = UserInterfaceInfoQueryRequest.getPageSize();


        Page<UserInterfaceInfo> UserInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size));
        List<UserInterfaceInfo> records = UserInterfaceInfoPage.getRecords();
        for (UserInterfaceInfo info : records) {
            System.out.println("数据: " + info);
        }

        return ResultUtils.success(UserInterfaceInfoPage);
    }






}
