package cn.ghy.ps.framework.aspect;

import cn.ghy.ps.common.constant.Constants;
import cn.ghy.ps.common.utils.HttpHeaderUtil;
import cn.ghy.ps.common.utils.ServletUtils;
import cn.ghy.ps.common.utils.StringUtils;
import cn.ghy.ps.common.utils.shiro.ShiroUtils;
import cn.ghy.ps.framework.annotation.Operlog;
import cn.ghy.ps.project.po.OperLog;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.framework.aspect.enums.OperLogStatusEnum;
import cn.ghy.ps.framework.aspect.enums.OperLogTypeEnum;
import cn.ghy.ps.project.service.operlog.IOperLogService;
import cn.ghy.ps.project.service.user.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 系统日志切点类
 *
 * @author 穹鏡
 */
@Aspect
@Component
public class SystemLogAspect{
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);


    @Autowired
    IOperLogService logServerce;

    @Autowired
    IUserService userService;

    @Autowired
    private ThreadPoolExecutor threadPoolTaskExecutor;

    /**
     * 方法规则拦截
     */
    @Pointcut("execution(* cn.ghy.ps.project.controller..*(..))")
    public void controllerAspect()
    {
    }


    /**
     * 方法正常运行后 执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning("controllerAspect() && @annotation(operlog)")
    public void AfterReturning(JoinPoint joinPoint, Operlog operlog)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        this.HandelLog(joinPoint, request, null);
    }


    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "controllerAspect() && @annotation(operlog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e, Operlog operlog)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        this.HandelLog(joinPoint, request, e);
    }


    /**
     * 保存日志线程
     *
     * @author lin.r.x
     */
    private static class SaveLogThread extends Thread{

        private OperLog log;
        private IOperLogService logServerce;


        public SaveLogThread(OperLog log, IOperLogService logServerce)
        {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.logServerce = logServerce;
        }

        @Override
        public void run()
        {
            logServerce.insertSelective(log);
        }

    }


    /**
     *
     * @描述: 处理封装 OperLog
     *
     * @date: 2021/10/2 14:37
     */
    public void HandelLog(JoinPoint joinPoint, HttpServletRequest request, Throwable e)
    {

        //用户
        User user = ShiroUtils.getUser();

        // 获得注解
        Operlog operlog = getControllerMethodDescription2(joinPoint);
        //描述
        String descr = operlog.descr();
        //模块
        String modal = operlog.modal();

        OperLog operLog = new OperLog();
        operLog.setDescr(descr);
        operLog.setOperModal(modal);

        //设置 ip
        String ip = HttpHeaderUtil.getIpAddr(request);
        operLog.setHost(ip);
        //设置ip 所在地址
        operLog.setIpAddress(HttpHeaderUtil.getAddress(ip));

        //操作时间
        operLog.setTime(new Date());

        //url
        String requestURI = request.getRequestURI();
        operLog.setUrl(requestURI);


        System.out.println(user);

        //操作人
        if (!StringUtils.isNull(user))
        {
            operLog.setOperUser(user.getUid());
            Integer gropId = user.getGrop();
            if (gropId != null)
            {
                //用户分组
                operLog.setGropId(String.valueOf(gropId));
            }
        }

        //操作系统
        String userAgent = request.getHeader("User-Agent");
        String browser = HttpHeaderUtil.getBrowser(userAgent);
        operLog.setBrowser(browser);

        //浏览器
        String sys = HttpHeaderUtil.getSys(userAgent);
        operLog.setSys(sys);

        //操作方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String type = request.getMethod();
        operLog.setMethod(className + "/" + methodName + "()" + "/" + type);

        //请求参数
        Map<String, String[]> params = request.getParameterMap();

        operLog.setParams(operLog.getMapToParams(params));


        operLog.setStatus(OperLogStatusEnum.SUCCESS_STATUS.getValue());
        operLog.setType(OperLogTypeEnum.SUCCESS_TYPE.getValue());

        if (!StringUtils.isNull(e))
        {
            operLog.setStatus(OperLogStatusEnum.ERROR_STATUS.getValue());
            operLog.setType(OperLogTypeEnum.ERROR_TYPE.getValue());
            operLog.setErrorMsg(e.getMessage());
        }

        //是否登录操作
        if (isLoginOperate(joinPoint))
        {
            String loginName = getLoginName(joinPoint, params);
            if (!StringUtils.isEmpty(loginName))
            {
                //根据登录id获取用户名
                User u = userService.login(loginName);
                if (u!=null)
                {
                    operLog.setOperUser(u.getUid());
                }else{
                    operLog.setOperUser(loginName);
                }
            }

        }

        if (!ip.equals("127.0.0.1"))
        {
            HttpSession session = ServletUtils.getSession();
            String s = (String) session.getAttribute(Constants.LOGIN_ERROR);

            if (!StringUtils.isEmpty(s))
            {
                operLog.setStatus(OperLogStatusEnum.ERROR_STATUS.getValue());
                operLog.setType(OperLogTypeEnum.ERROR_TYPE.getValue());
                operLog.setErrorMsg(s);
            }
            threadPoolTaskExecutor.execute(new SaveLogThread(operLog, logServerce));
            session.removeAttribute(Constants.LOGIN_ERROR);
        }
    }


    /**
     * 判断是否登录操作
     */
    public static boolean isLoginOperate(JoinPoint joinPoint)
    {
        // 获得注解
        Operlog operlog = getControllerMethodDescription2(joinPoint);
        //描述
        String descr = operlog.descr();

        if (descr.indexOf("登录") >= 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 获取登录账号
     */
    public static String getLoginName(JoinPoint joinPoint, Map<String, String[]> params)
    {

        String[] strings = params.get("name");
        return strings[0];
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     *
     * @return 方法描述
     */
    public static Operlog getControllerMethodDescription2(JoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Operlog annotation = method.getAnnotation(Operlog.class);
        return annotation;
    }

}


