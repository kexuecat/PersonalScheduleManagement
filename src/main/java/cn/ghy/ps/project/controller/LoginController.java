package cn.ghy.ps.project.controller;


import cn.ghy.ps.common.constant.Constants;
import cn.ghy.ps.common.utils.ServletUtils;
import cn.ghy.ps.framework.shiro.LoginService;
import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.project.service.register.BaseService;
import cn.ghy.ps.project.service.user.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@org.springframework.stereotype.Controller
@RequestMapping("/oa")
public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private String prefix = "system/user/";

    @Autowired
    LoginService loginService;

    @Autowired
    IUserService userService;

    @Autowired
    BaseService baseService;


    @RequestMapping(value = "/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping("/toRegister")
    public String doRegister(@RequestParam("loginName") String loginName,
                             @RequestParam("pwd") String pwd) {

        boolean result = baseService.registerData(loginName,pwd);
        if(result){
            return "/login";
        }
        return "/register";
    }

    /**
     *
     * @描述: 执行登录操作
     *
     * @params: user:用户登录信息；
     *          validateCode：验证码
     * @return:
     * @date: 2021/9/29 21:20
     */
    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult Logining(User user, String validateCode, Boolean rememberMe, HttpServletRequest request)
    {
        HttpSession session = ServletUtils.getSession();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPwd());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();

        //验证用户名和密码 验证码的问题
        try
        {
            loginService.checkLogin(user.getName(), user.getPwd(), validateCode);
        }
        catch (Exception e)
        {
            session.setAttribute(Constants.LOGIN_ERROR, e.getMessage());
            return error(e.getMessage());
        }

        try
        {
            if (!subject.isAuthenticated())
            {
                subject.login(token);
            }

        }
        catch (IncorrectCredentialsException e)
        {
            session.setAttribute(Constants.LOGIN_ERROR,"密码错误");
            return error("密码错误！");
        }
        catch (UnknownAccountException e)
        {
            session.setAttribute(Constants.LOGIN_ERROR,e.getMessage());
            return error(e.getMessage());
        }
        catch (LockedAccountException e)
        {
            session.setAttribute("login",e.getMessage());
            return error(e.getMessage());
        }
        catch (AuthenticationException e)
        {

            session.setAttribute(Constants.LOGIN_ERROR,e.getMessage());
            return error("系统异常！");
        }

        return success();
    }


    /**
     *
     * @描述: 登录页面
     *
     * @params:
     * @return:
     * @date: 2021/9/29 21:20
     */
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "login";
    }


}
