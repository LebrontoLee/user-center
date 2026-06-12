package com.lee.usercenterbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.usercenterbackend.common.BaseResponse;
import com.lee.usercenterbackend.common.ErrorCode;
import com.lee.usercenterbackend.common.ResultUtils;
import com.lee.usercenterbackend.exception.BusinessException;
import com.lee.usercenterbackend.model.domain.User;
import com.lee.usercenterbackend.model.domain.request.UserLoginRequest;
import com.lee.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.lee.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.lee.usercenterbackend.constant.UserConstant.ADMIN_ROLE;
import static com.lee.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        long res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(res);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        int res =  userService.userLogout(request);
        return ResultUtils.success(res);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object user_obj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) user_obj;
        if(currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userID = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userID);
        User safetyuser =  userService.getSafetyUser(user);
        return ResultUtils.success(safetyuser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        // 仅管理员可查询
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"非管理员用户无法访问");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user->userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody long id, HttpServletRequest request) {
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"非管理员用户无法访问");
        }
        if(id <= 0){
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     * @param request Http请求
     * @return true - 是管理员 false - 不是管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object user_obj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)user_obj;
        return user!= null && user.getUserRole() == ADMIN_ROLE;
    }
}
