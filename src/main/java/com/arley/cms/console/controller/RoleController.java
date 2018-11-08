package com.arley.cms.console.controller;

import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.constant.PublicConstants;
import com.arley.cms.console.exception.CustomException;
import com.arley.cms.console.pojo.query.SysRoleQuery;
import com.arley.cms.console.pojo.vo.SysRoleVO;
import com.arley.cms.console.service.SysRoleService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author XueXianlei
 * @Description: 角色控制层
 * @date 2018/8/21 17:07
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping(value = "/listRole")
    public AnswerBody listRole() {
        List<SysRoleVO> sysRoleVOList = sysRoleService.listRole();
        return AnswerBody.buildAnswerBody(sysRoleVOList);
    }

    /**
     * 获取管理员拥有的角色
     * @return
     */
    @RequestMapping(value = "/getRoleBySysUserId")
    public AnswerBody getRoleBySysUserId(Integer userId) {
        SysRoleVO role = sysRoleService.getRoleBySysUserId(userId);
        return AnswerBody.buildAnswerBody(role);
    }

    /**
     * 分页查询角色列表
     * @param roleQuery
     * @return
     */
    @RequestMapping(value = "/listRoleByPage")
    public AnswerBody listRoleByPage(SysRoleQuery roleQuery) {
        Pagination pagination = sysRoleService.listRoleByPage(roleQuery);
        return AnswerBody.buildAnswerBody(pagination);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/deleteRole")
    public AnswerBody deleteRole(Integer roleId, @RequestHeader String loginUserName) {
        // 如果不是admin 不能删除
        if (Objects.equals(PublicConstants.SUPER_ROLE_ID, roleId)) {
            if (!Objects.equals(loginUserName, PublicConstants.ADMIN_USER_NAME)) {
                throw new CustomException(PublicCodeEnum.EDIT_ADMIN.getCode(), "仅超级管理员用户可操作此记录!", CustomException.LOGGER_WARN_TYPE);
            }
        }
        sysRoleService.deleteRoleById(roleId);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 修改角色
     * @param sysRoleVO
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "/editRole")
    public AnswerBody editRole(SysRoleVO sysRoleVO, String permissionIds, @RequestHeader String loginUserName) {
        // 如果不是admin 不能操作
        if (Objects.equals(PublicConstants.SUPER_ROLE_ID, sysRoleVO.getRoleId())) {
            if (!Objects.equals(loginUserName, PublicConstants.ADMIN_USER_NAME)) {
                throw new CustomException(PublicCodeEnum.EDIT_ADMIN.getCode(), "仅超级管理员用户可操作此记录!", CustomException.LOGGER_WARN_TYPE);
            }
        }
        sysRoleVO.setModifier(loginUserName);
        sysRoleService.updateRole(sysRoleVO, permissionIds);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 添加角色
     * @param sysRoleVO
     * @param permissionIds
     * @return
     */
    @RequestMapping(value = "/addRole")
    public AnswerBody addRole(SysRoleVO sysRoleVO, String permissionIds, @RequestHeader String loginUserName) {
        sysRoleVO.setModifier(loginUserName);
        sysRoleService.insertRole(sysRoleVO, permissionIds);
        return AnswerBody.buildAnswerBody();
    }


}
