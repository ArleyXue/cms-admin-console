package com.arley.cms.console.service.impl;

import com.arley.cms.console.mapper.SysPermissionMapper;
import com.arley.cms.console.pojo.Do.SysPermissionDO;
import com.arley.cms.console.pojo.vo.SysPermissionVO;
import com.arley.cms.console.service.SysPermissionService;
import com.arley.cms.console.util.CopyPropertiesUtils;
import com.arley.cms.console.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
@Service("sysPermissionService")
@Transactional(rollbackFor = Exception.class)
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermissionVO> listHavePermission(Integer userId) {
        List<SysPermissionDO> sysPermissionDOList = sysPermissionMapper.listHavePermission(userId);
        return CopyPropertiesUtils.convertSysPermissionDOToVO(sysPermissionDOList);
    }

    @Override
    public List<SysPermissionVO> listHaveHierarchyPermission() {
        List<SysPermissionDO> sysPermissionDOList = sysPermissionMapper.listHaveHierarchyPermission();
        return CopyPropertiesUtils.convertSysPermissionDOToVO(sysPermissionDOList);
    }

    @Override
    public List<SysPermissionVO> listPermissionByRoleId(Integer roleId) {
        List<SysPermissionDO> sysPermissionDOList = sysPermissionMapper.listPermissionByRoleId(roleId);
        return CopyPropertiesUtils.convertSysPermissionDOToVO(sysPermissionDOList);
    }

    @Override
    public List<SysPermissionVO> listPermission() {
        List<SysPermissionDO> sysPermissionDOList = sysPermissionMapper.selectList(new QueryWrapper<SysPermissionDO>().lambda().orderByAsc(SysPermissionDO::getMenuPriority));
        return CopyPropertiesUtils.convertSysPermissionDOToVO(sysPermissionDOList);
    }

    @Override
    public void insertPermission(SysPermissionVO sysPermissionVO) {
        SysPermissionDO sysPermissionDO = new SysPermissionDO();
        BeanUtils.copyProperties(sysPermissionVO, sysPermissionDO);
        sysPermissionDO.setMenuState(1);
        sysPermissionDO.setGmtCreate(DateUtils.getLocalDateTime());
        sysPermissionDO.setGmtModified(DateUtils.getLocalDateTime());
        sysPermissionMapper.insert(sysPermissionDO);
    }

    @Override
    public void updatePermission(SysPermissionVO sysPermissionVO) {
        SysPermissionDO permissionDO = sysPermissionMapper.selectById(sysPermissionVO.getPermissionId());
        permissionDO.setMenuName(sysPermissionVO.getMenuName());
        permissionDO.setMenuCode(sysPermissionVO.getMenuCode());
        permissionDO.setMenuIcon(sysPermissionVO.getMenuIcon());
        permissionDO.setMenuPriority(sysPermissionVO.getMenuPriority());
        permissionDO.setMenuUrl(sysPermissionVO.getMenuUrl());
        permissionDO.setMenuType(sysPermissionVO.getMenuType());
        permissionDO.setGmtModified(DateUtils.getLocalDateTime());
        sysPermissionMapper.updateById(permissionDO);
    }

}
