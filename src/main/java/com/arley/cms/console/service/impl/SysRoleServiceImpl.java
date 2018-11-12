package com.arley.cms.console.service.impl;

import com.arley.cms.console.mapper.SysRoleMapper;
import com.arley.cms.console.mapper.SysUserMapper;
import com.arley.cms.console.pojo.Do.SysRoleDO;
import com.arley.cms.console.pojo.Do.SysRolePermissionDO;
import com.arley.cms.console.pojo.Do.SysUserRoleDO;
import com.arley.cms.console.pojo.query.SysRoleQuery;
import com.arley.cms.console.pojo.vo.SysRoleVO;
import com.arley.cms.console.service.SysRoleService;
import com.arley.cms.console.util.CopyPropertiesUtils;
import com.arley.cms.console.util.DateUtils;
import com.arley.cms.console.util.PageUtils;
import com.arley.cms.console.util.Pagination;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
@Service("sysRoleService")
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public void insertRole(SysRoleVO sysRoleVO, String permissionIds) {
        SysRoleDO sysRoleDO = new SysRoleDO();
        BeanUtils.copyProperties(sysRoleVO, sysRoleDO);
        sysRoleDO.setGmtCreate(DateUtils.getLocalDateTime());
        sysRoleDO.setGmtModified(DateUtils.getLocalDateTime());
        sysRoleMapper.insert(sysRoleDO);

        // 保存新的权限
        List<SysRolePermissionDO> rolePermissionList = putSysRolePermission(sysRoleDO.getRoleId(), permissionIds);
        if (rolePermissionList.size() > 0) {
            sysRoleMapper.saveRolePermission(rolePermissionList);
        }
    }

    @Override
    public Pagination listRoleByPage(SysRoleQuery roleQuery) {
        LambdaQueryWrapper<SysRoleDO> qw = new QueryWrapper<SysRoleDO>().lambda();
        if (null != roleQuery.getRoleId()) {
            qw.eq(SysRoleDO::getRoleId, roleQuery.getRoleId());
        }

        Page<SysRoleDO> page = new Page<>();
        // 分页加排序处理
        if (!PageUtils.pageAndOrderBy(page, roleQuery, SysRoleDO.class)) {
            // 设置默认排序
            qw.orderByAsc(SysRoleDO::getRoleId);
        }

        IPage<SysRoleDO> iPage = sysRoleMapper.selectPage(page, qw);
        Pagination<SysRoleVO> pagination = new Pagination<>();
        pagination.setData( CopyPropertiesUtils.convertSysRoleDOToVO(iPage.getRecords()));
        pagination.setTotal(iPage.getTotal());
        return pagination;
    }

    @Override
    public void updateRole(SysRoleVO sysRoleVO, String permissionIds) {
        // 修改角色
        SysRoleDO sysRoleDO = sysRoleMapper.selectById(sysRoleVO.getRoleId());
        sysRoleDO.setRoleName(sysRoleVO.getRoleName());
        sysRoleDO.setRemark(sysRoleVO.getRemark());
        sysRoleDO.setRoleState(sysRoleVO.getRoleState());
        sysRoleDO.setGmtModified(DateUtils.getLocalDateTime());
        sysRoleMapper.updateById(sysRoleDO);

        // 删除角色权限
        sysRoleMapper.deleteRolePermissionByRoleId(sysRoleDO.getRoleId());

        // 赋予角色新权限
        List<SysRolePermissionDO> rolePermissionList = putSysRolePermission(sysRoleDO.getRoleId(), permissionIds);
        if (rolePermissionList.size() > 0) {
            sysRoleMapper.saveRolePermission(rolePermissionList);
        }

    }

    @Override
    public void deleteRoleById(Integer roleId) {
        sysRoleMapper.deleteById(roleId);

        // 删除角色权限
        sysRoleMapper.deleteRolePermissionByRoleId(roleId);

        // 删除用户角色
        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setRoleId(roleId);
        sysUserMapper.deleteSysUserRole(sysUserRoleDO);

    }

    @Override
    public List<SysRoleVO> listRole() {
        LambdaQueryWrapper<SysRoleDO> lambda = new QueryWrapper<SysRoleDO>().lambda()
                .eq(SysRoleDO::getRoleState, 1)
                .orderByAsc(SysRoleDO::getRoleId);
        List<SysRoleDO> sysRoleDOList = sysRoleMapper.selectList(lambda);
        return CopyPropertiesUtils.convertSysRoleDOToVO(sysRoleDOList);
    }

    @Override
    public SysRoleVO getRoleBySysUserId(Integer userId) {
        SysRoleDO sysRoleDO = sysRoleMapper.getRoleBySysUserId(userId);
        SysRoleVO sysRoleVO = new SysRoleVO();
        if (null != sysRoleDO) {
            BeanUtils.copyProperties(sysRoleDO, sysRoleVO);
        }
        return sysRoleVO;
    }

    public static List<SysRolePermissionDO> putSysRolePermission(Integer roleId, String permissionIds) {
        List<SysRolePermissionDO> result = new ArrayList<>();
        if (StringUtils.isNotBlank(permissionIds)) {
            String[] menuIds = permissionIds.split(",");
            if (menuIds.length > 0) {
                for (String menuId : menuIds) {
                    SysRolePermissionDO rolePermission = new SysRolePermissionDO();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(Integer.parseInt(menuId));
                    result.add(rolePermission);
                }
            }
        }

        return result;
    }
}
