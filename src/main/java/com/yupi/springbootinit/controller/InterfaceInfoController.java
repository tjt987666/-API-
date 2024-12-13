package com.yupi.springbootinit.controller;

import cn.hutool.json.JSONUtil;
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
import com.yupi.springbootinit.model.dto.InterfaceInfo.*;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.InterfaceInfoVO;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.service.UserService;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 帖子接口
 *
 */
@RestController
@RequestMapping("/InterfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService InterfaceInfoService;

    @Resource
    private UserService userService;

    @Autowired
    private XiongdaClient xiongdaClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param InterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest InterfaceInfoAddRequest, HttpServletRequest request) {
        // 检查请求参数是否为空
        if (InterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println("请求数据："+InterfaceInfoAddRequest);


        // 创建新的帖子对象
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        // 将请求参数复制到帖子对象中
        BeanUtils.copyProperties(InterfaceInfoAddRequest, InterfaceInfo);

        // 验证帖子对象的有效性
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, true);

        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);

        // 设置帖子的用户ID
        InterfaceInfo.setUserId(loginUser.getId());

        System.out.println("userId"+loginUser.getId());

        System.out.println("userIdInfo"+InterfaceInfo.getUserId());



        // 保存帖子到数据库
        boolean result = InterfaceInfoService.save(InterfaceInfo);

        // 如果保存失败，抛出操作错误异常
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);

        // 获取新创建的帖子ID
        long newInterfaceInfoId = InterfaceInfo.getId();

        // 返回成功响应，包含新创建的帖子ID
        return ResultUtils.success(newInterfaceInfoId);
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
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println("id数据："+deleteRequest);
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
//        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
//        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean b = InterfaceInfoService.removeById(id);
        // 提交事物






        System.out.println("是否已经删除:"+b);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param InterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest InterfaceInfoUpdateRequest) {
        System.out.println("请求数据："+InterfaceInfoUpdateRequest);
        if (InterfaceInfoUpdateRequest == null || InterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        // 更新时间为当前时间
        InterfaceInfo.setUpdateTime(new Date());
        BeanUtils.copyProperties(InterfaceInfoUpdateRequest, InterfaceInfo);
        // 参数校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        long id = InterfaceInfoUpdateRequest.getId();
        System.out.println("id:"+id);
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        System.out.println("old"+oldInterfaceInfo);

        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = InterfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        System.out.println("id:"+id);
        // 检查ID是否有效
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 根据ID获取帖子对象
        InterfaceInfo InterfaceInfo = InterfaceInfoService.getById(id);
        System.out.println("old"+InterfaceInfo);

        // 如果帖子不存在，抛出未找到错误
        if (InterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 获取帖子的详细视图对象
        InterfaceInfoVO InterfaceInfoVO = InterfaceInfoService.getInterfaceInfoVO(InterfaceInfo, request);

        // 返回成功响应，包含帖子的详细视图对象
        return ResultUtils.success(InterfaceInfoVO);
    }


    /**
     * 分页获取列表
     *
     * @param InterfaceInfoQueryRequest
     * @return
     */
    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest InterfaceInfoQueryRequest) {
        long current = InterfaceInfoQueryRequest.getCurrent();
        long size = InterfaceInfoQueryRequest.getPageSize();


        Page<InterfaceInfo> InterfaceInfoPage = InterfaceInfoService.page(new Page<>(current, size));
        List<InterfaceInfo> records = InterfaceInfoPage.getRecords();
        for (InterfaceInfo info : records) {
            System.out.println("数据: " + info);
        }

        return ResultUtils.success(InterfaceInfoPage);
    }


    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,HttpServletRequest request) {

        Long id = idRequest.getId();

        System.out.println("请求数据："+idRequest);
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断改接口是否可以引用
        com.xiongda.xiongclientsdk.model.User user  = new com.xiongda.xiongclientsdk.model.User();
        user.setUsername("xiongda");
        String userNmae = xiongdaClient.getUserNmae(user);
        if (userNmae == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证失败");
        }

        InterfaceInfo info = new InterfaceInfo();
        // 赋值ID
        info.setId(id);
        // 赋值状态上线
        info.setStatus(1);


        boolean result = InterfaceInfoService.updateById(info);
        return ResultUtils.success(result);
    }


        @PostMapping("/Offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> OfflineInterfaceInfo(@RequestBody  IdRequest idRequest,HttpServletRequest request) {
        Long id = idRequest.getId();

        System.out.println("请求数据："+idRequest);
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断改接口是否可以引用
        com.xiongda.xiongclientsdk.model.User user  = new com.xiongda.xiongclientsdk.model.User();
        user.setUsername("xiongda");
        String userNmae = xiongdaClient.getUserNmae(user);
        if (userNmae == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证失败");
        }

        InterfaceInfo info = new InterfaceInfo();
        // 赋值ID
        info.setId(id);
        // 赋值状态下线
        info.setStatus(0);


        boolean result = InterfaceInfoService.updateById(info);
        return ResultUtils.success(result);
    }


    @PostMapping("/invoke")
    public BaseResponse<Object> InvokeInterfaceInfo(@RequestBody InterfaceInfoInvokerRequest invokerRequest, HttpServletRequest request) {
        Long id = invokerRequest.getId();

        System.out.println("请求数据："+invokerRequest);
        if (invokerRequest == null || invokerRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 判断接口是否存在
        InterfaceInfo info = InterfaceInfoService.getById(id);


        if (info == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }

        // 判断改接口是否可以引用
        if (info.getStatus() != 1 ){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口已关闭");
        }

        // 获取到登录用户
        User loginUser = userService.getLoginUser(request);

        // 判断用户是否有权限可以使用和是否被封号和拉黑
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        String userStatus = loginUser.getUserStatus();

        if (accessKey == null || secretKey == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户未登录");
        }

        if (userStatus.equals("1")){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户被拉黑");
        }
        if (userStatus.equals("2")){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户被封号");
        }

        // 将用户的信息传入到客户端
        XiongdaClient client = new XiongdaClient(accessKey,secretKey);
        Gson gson = new Gson();
        // 获取到请求参数信息
        String useRequestParams = invokerRequest.getUseRequestParams();
        com.xiongda.xiongclientsdk.model.User user = gson.fromJson(useRequestParams, com.xiongda.xiongclientsdk.model.User.class);

        // 调用客户端
        String userNmae = client.getUserNmae(user);

        return ResultUtils.success(userNmae);
    }






}
