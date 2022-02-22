package cn.ghy.ps.project.controller;

import cn.ghy.ps.common.constant.CsEnum;
import cn.ghy.ps.common.exception.file.FileNameLengthException;
import cn.ghy.ps.common.exception.file.FileSizeException;
import cn.ghy.ps.common.utils.HttpHeaderUtil;
import cn.ghy.ps.common.utils.StringUtils;
import cn.ghy.ps.common.utils.file.UploadFile;
import cn.ghy.ps.common.utils.shiro.Encryption;
import cn.ghy.ps.common.utils.shiro.ShiroUtils;
import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.page.TableDataInfo;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Grop;
import cn.ghy.ps.project.po.Position;
import cn.ghy.ps.project.po.Role;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.project.service.grop.IGropService;
import cn.ghy.ps.project.service.position.IPositionService;
import cn.ghy.ps.project.service.role.IRoleService;
import cn.ghy.ps.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author 穹鏡
 */

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private String prefix = "system/user/";

    @Autowired
    IUserService iUserService;
    @Autowired
    IRoleService iRoleService;
    @Autowired
    IGropService iGropService;
    @Autowired
    IPositionService iPositionService;


    /**
     *
     * @描述 跳转到用户页面
     *
     * @date 2021/9/16 10:54
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("user:list")
    public String toUserList()
    {
        return prefix + "user";
    }


    /**
     * @描述 用户数据
     * @date 2021/9/15 12:30
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> users = iUserService.selectByUser(user);

        return getDataTable(users);
    }


    /**
     * 编辑用户 system/user/edit/20210914-1
     */
    @RequiresPermissions("user:update")
    @RequestMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, Model model)
    {
        // 个人信息
        User user = iUserService.selectByPrimaryKey(userId);

        Map<String, Object> role_post_grop = getRole_Post_Grop();
        model.addAttribute("grops", role_post_grop.get("grop"));
        model.addAttribute("roles", role_post_grop.get("role"));
        model.addAttribute("positions", role_post_grop.get("position"));
        model.addAttribute("user", user);
        return prefix + "edit";
    }

    /**
     *
     * @描述 保存用户
     *
     * @date 2021/9/15 18:53
     */
    @PostMapping("/editSave")
    @RequiresPermissions("user:update")
    @ResponseBody
    public AjaxResult save(User user)
    {
        if (StringUtils.isNotNull(user.getUid()) && User.isBoss(user.getUid()))
        {
            return error("不允许修改管理员用户");
        }
        return result(iUserService.updateByPrimaryKeySelective(user));
    }


    /**
     * @描述 添加用户页面
     * @date 2021/9/15 18:46
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("user:add")
    public String toaddUser(Model model)
    {
        Map<String, Object> role_post_grop = getRole_Post_Grop();
        model.addAttribute("grops", role_post_grop.get("grop"));
        model.addAttribute("roles", role_post_grop.get("role"));
        model.addAttribute("positions", role_post_grop.get("position"));
        return prefix + "add";
    }

    /**
     *
     * @描述 添加用户
     *
     * @date 2021/9/15 20:40
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("user:add")
    @ResponseBody
    public AjaxResult addUser(User user)
    {
        String cuid = createUID();
        user.setUid(cuid);
        user.setAvatar(CsEnum.avatar.USER_AVATAR.getValue());
        user.setCreateTime(new Date());
        user.setPwd(Encryption.getMD5(user.getPwd(), cuid).toString());
        return result(iUserService.insertSelective(user));
    }

    /**
     *
     * @描述 批量删除
     *
     * @date 2021/9/16 9:31
     */
    @RequestMapping("/del")
    @RequiresPermissions("user:del")
    @ResponseBody
    public AjaxResult delByUserIds(String[] ids)
    {
        try
        {
            int i = iUserService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     *
     * @描述 编辑密码修改页面
     *
     * @date 2021/9/16 10:25
     */
    @RequestMapping("/resetPwd/{userId}")
    @RequiresPermissions("user:update")
    public String editPwd(@PathVariable("userId") String id, Model model)
    {
        model.addAttribute("uid", id);
        return prefix + "resetPwd";
    }


    /**
     *
     * @描述 密码修改
     *
     * @date 2021/9/16 10:42
     */

    @RequestMapping("/resetPwd")
    @RequiresPermissions("user:update")
    @ResponseBody
    public AjaxResult resetPwd(User user)
    {
        return result(iUserService.resrtPwd(user));
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = iUserService.checkPhoneUnique(user);
        }
        return uniqueFlag;
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = iUserService.checkEmailUnique(user);
        }
        return uniqueFlag;
    }


    /**
     *
     * @描述: 校验登录名唯一性
     *
     * @params:
     * @return:
     * @date: 2021/10/2 17:06
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user)
    {
        String uniqueFlag = "0";
        if (user != null)
        {
            uniqueFlag = iUserService.checkLoginNameUnique(user);
        }
        return uniqueFlag;
    }


    public Map<String, Object> getRole_Post_Grop()
    {
        Map<String, Object> map = new HashMap<>();
//        角色
        List<Role> roles = iRoleService.selectRoleList(new Role());
//        分组信息
        List<Grop> grops = iGropService.selectGropList(new Grop());
//        权限
        List<Position> positions = iPositionService.selectPositionList(new Position());
        map.put("role", roles);
        map.put("grop", grops);
        map.put("position", positions);

        return map;
    }


    /**
     * 用户个人信息查看页面
     */
    @RequestMapping("/myMsg")
    public String ToMyMsg(Model model, HttpServletRequest request)
    {
        User user = iUserService.selectByPrimaryKey(getUserId());
        model.addAttribute("user", user);
        model.addAttribute("loginIp", HttpHeaderUtil.getIpAddr(request));
        return prefix + "profile/msg";
    }


    /**
     * 密码修改页面
     */
    @RequestMapping("/resetMyPwd")
    public String toResetPwd(Model model)
    {
        User user = iUserService.selectByPrimaryKey(getUserId());
        model.addAttribute("user", user);
        return prefix + "profile/resetPwd";
    }

    /**
     * 密码修改保存
     */
    @RequestMapping("/updateMyPwdSave")
    @ResponseBody
    @RequiresPermissions("user:update")
    public AjaxResult updateMyPwdSave(String password)
    {
        User user = new User();
        //将密码加密 用户id 作为盐
        user.setPwd(Encryption.getMD5(password, getUserId()).toString());
        user.setUid(getUserId());
        int i = iUserService.updateByPrimaryKeySelective(user);
        if (i > 0)
        {
            //更新shiro中的信息
            ShiroUtils.reloadUser(iUserService.selectByPrimaryKey(getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 编辑用户头像修改
     */
    @RequestMapping("/updateAvatar")
    public String toupdateAvatar(Model model)
    {
        model.addAttribute("user", getUser());
        return prefix + "profile/avatar";
    }

    /**
     * 修改保存用户头像
     */
    @RequestMapping("/updateAvatarSave")
    @RequiresPermissions("user:update")
    @ResponseBody
    public AjaxResult toupdateAvatar(MultipartFile file)
    {
        try
        {
            String imgPath = UploadFile.uploadUserImg(file);
            if (StringUtils.isEmpty(imgPath))
            {
                return error("图片上传失败，稍后再试！");
            }

            User user = new User();
            user.setUid(getUserId());
            user.setAvatar(imgPath);
            int i = iUserService.updateByPrimaryKeySelective(user);
            if (i > 0)
            {
                ShiroUtils.reloadUser(iUserService.selectByPrimaryKey(getUserId()));
            }
            return result(i);
        }
        catch (IOException e)
        {
            return error();
        }
        catch (FileSizeException e)
        {
            //文件过大
            return error(e.getMsg());
        }
        catch (FileNameLengthException e)
        {
            //文件名字超长
            return error(e.getMsg());
        }
    }


    /**
     * 校验密码和原来密码是否相同
     */
    @RequestMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password)
    {
        //加密后与数据库密码比较
        String md5 = Encryption.getMD5(password, getUserId()).toString();
        if (md5.equals(getPwd()))
        {
            return true;
        }
        return false;
    }


}
