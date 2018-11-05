package com.arley.cms.console.controller;

import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.constant.PublicConstants;
import com.arley.cms.console.pojo.query.SysUserQuery;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.SysUserService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author XueXianlei
 * @Description: 管理员控制层
 * @date 2018/8/19 10:10
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询管理员列表
     * @param sysUserQuery
     * @return
     */
    @RequestMapping(value = "/listAdminByPage")
    public AnswerBody listAdminByPage(SysUserQuery sysUserQuery) {
        Pagination pagination = sysUserService.listSysUserByPage(sysUserQuery);
        return AnswerBody.buildAnswerBody(pagination);
    }

    /**
     * 添加管理员
     * @param sysUserVO
     * @return
     */
    @RequestMapping(value = "/addAdmin")
    public AnswerBody addAdmin(SysUserVO sysUserVO, Integer roleId, @RequestHeader String loginUserName) {
        sysUserVO.setModifier(loginUserName);
        sysUserService.insertSysUser(sysUserVO, roleId);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 修改管理员
     * @param sysUserVO
     * @return
     */
    @RequestMapping(value = "/editAdmin")
    public AnswerBody editAdmin(SysUserVO sysUserVO, Integer roleId, Integer resetPassword, @RequestHeader String loginUserName) {
        sysUserVO.setModifier(loginUserName);
        SysUserVO sysUser = sysUserService.getSysUser(sysUserVO.getUserId());
        if (Objects.equals(PublicConstants.ADMIN_USER_NAME, sysUser.getUserName())) {
            return AnswerBody.buildAnswerBody(PublicCodeEnum.EDIT_ADMIN);
        }
        sysUserService.updateSysUser(sysUserVO, roleId, resetPassword);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 删除管理员
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteAdmin")
    public AnswerBody deleteAdmin(Integer userId) {
        SysUserVO sysUser = sysUserService.getSysUser(userId);
        if (Objects.equals(PublicConstants.ADMIN_USER_NAME, sysUser.getUserName())) {
            return AnswerBody.buildAnswerBody(PublicCodeEnum.EDIT_ADMIN);
        }
        sysUserService.deleteSysUser(userId);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    @RequestMapping(value = "/checkIsExistUserName")
    public AnswerBody checkIsExistUserName(String userName) {
        boolean isExist = sysUserService.checkIsExistUserName(userName);
        Map<String, Boolean> result = new HashMap<>(1);
        result.put("isExist", isExist);
        return AnswerBody.buildAnswerBody(result);
    }

    /**
     * 修改用户状态
     * @param userId
     * @return
     */
    @RequestMapping(value = "/updateUserState")
    public AnswerBody updateUserState(Integer userId, @RequestHeader String loginUserName) {
        SysUserVO sysUser = sysUserService.getSysUser(userId);
        if (Objects.equals(PublicConstants.ADMIN_USER_NAME, sysUser.getUserName())) {
            return AnswerBody.buildAnswerBody(PublicCodeEnum.EDIT_ADMIN);
        }
        // 修改用户状态
        sysUserService.updateSysUserState(userId, sysUser.getUserState() == 1 ? 0 : 1);

        // 如果禁用把此用户踢下线
       /* if (0 == sysUser.getUserState()) {
            Collection<Session> activeSessions = sessionDAO.getActiveSessions();
            activeSessions.forEach(s -> {
                String userName = String.valueOf(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
                if (Objects.equals(userName, sysUser.getUserName())) {
                    s.setTimeout(0);
                }
            });
        }*/
        Map<String, Integer> result = new HashMap<>();
        result.put("userState", sysUser.getUserState());
        return AnswerBody.buildAnswerBody(result);
    }


}
