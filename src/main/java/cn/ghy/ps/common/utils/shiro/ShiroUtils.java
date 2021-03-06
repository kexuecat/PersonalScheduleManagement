package cn.ghy.ps.common.utils.shiro;

import cn.ghy.ps.common.utils.DateUtils;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.framework.shiro.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Date;

/**
 * shiro 工具类
 * 
 * @author ruoyi
 */
public class ShiroUtils
{

    public static Subject getSubjct()
    {
        return SecurityUtils.getSubject();
    }

    public static Session getSession()
    {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout()
    {
        getSubjct().logout();
    }

    public static User getUser()
    {
        return (User) getSubjct().getPrincipal();
    }

    /**
     *  修改用户信息后刷新用户消息
     */
    public static void reloadUser(User user)
    {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /** 清空权限缓存*/
    public static void clearCachedAuthorizationInfo()
    {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static String getUserId()
    {
        return getUser().getUid();
    }

    public static String getLoginName()
    {
        return getUser().getName();
    }

    public static String getIp()
    {
        return getSubjct().getSession().getHost();
    }

    public static String getSessionId()
    {
        return String.valueOf(getSubjct().getSession().getId());
    }

    /**
     *
     * @描述 根据日期生成用户Id
     *
     * @date 2021/9/15 20:41
     */
    public static String createUID()
    {
        String s = DateUtils.DateToSTr2(new Date());
        String UID = s.replace("-", "").replace(":", "").replace(" ", "").trim();
        return UID;
    }
}
