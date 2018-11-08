package com.arley.cms.console.controller;

import com.arley.cms.console.pojo.vo.SysPermissionVO;
import com.arley.cms.console.pojo.vo.TreeVO;
import com.arley.cms.console.service.SysPermissionService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.MenuUtils;
import com.arley.cms.console.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author XueXianlei
 * @Description: 菜单控制层
 * @date 2018/8/22 9:35
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 获取层级菜单
     * @return
     */
    @RequestMapping(value = "/listHaveHierarchyPermission")
    public AnswerBody listHaveHierarchyPermission() {
        List<SysPermissionVO> sysPermissionVOList = sysPermissionService.listHaveHierarchyPermission();
        return AnswerBody.buildAnswerBody(sysPermissionVOList);
    }

    /**
     * 修改菜单
     * @param sysPermissionVO
     * @return
     */
    @RequestMapping(value = "/editPermission")
    public AnswerBody editPermission(SysPermissionVO sysPermissionVO, @RequestHeader String loginUserName) {
        sysPermissionVO.setModifier(loginUserName);
        sysPermissionService.updatePermission(sysPermissionVO);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 添加菜单
     * @param sysPermissionVO
     * @return
     */
    @RequestMapping(value = "/addPermission")
    public AnswerBody addPermission(SysPermissionVO sysPermissionVO, @RequestHeader String loginUserName) {
        sysPermissionVO.setModifier(loginUserName);
        sysPermissionService.insertPermission(sysPermissionVO);
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 获取树状结构的菜单
     * @return
     */
    @RequestMapping(value = "/listTreePermission")
    public AnswerBody listTreePermission() {
        List<SysPermissionVO> menuList = sysPermissionService.listPermission();
        Set<TreeVO> treeVOS = MenuUtils.makeXTreeList(menuList);
        return AnswerBody.buildAnswerBody(treeVOS);
    }

    /**
     * 获取角色拥有的菜单
     * @return
     */
    @RequestMapping(value = "/listPermissionByRoleId")
    public AnswerBody listPermissionByRoleId(Integer roleId) {
        List<SysPermissionVO> menuList = sysPermissionService.listPermissionByRoleId(roleId);
        return AnswerBody.buildAnswerBody(menuList);
    }


}
