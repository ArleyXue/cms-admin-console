package com.arley.cms.console.service.impl;

import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.exception.CustomException;
import com.arley.cms.console.mapper.SysUserMapper;
import com.arley.cms.console.pojo.Do.SysUserDO;
import com.arley.cms.console.pojo.Do.SysUserRoleDO;
import com.arley.cms.console.pojo.query.SysUserQuery;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.SysUserService;
import com.arley.cms.console.shiro.Encrypt;
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

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
@Service("sysUserService")
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Pagination listSysUserByPage(SysUserQuery sysUserQuery) {
        LambdaQueryWrapper<SysUserDO> qw = new QueryWrapper<SysUserDO>().lambda();
        if (StringUtils.isNotBlank(sysUserQuery.getUserName())) {
            qw.like(SysUserDO::getUserName, sysUserQuery.getUserName());
        }

        Page<SysUserDO> page = new Page<>();
        // 分页加排序处理
        if (!PageUtils.pageAndOrderBy(page, sysUserQuery, SysUserDO.class)) {
            // 设置默认排序
            qw.orderByAsc(SysUserDO::getUserId);
        }

        IPage<SysUserDO> iPage = sysUserMapper.selectPage(page, qw);
        Pagination<SysUserVO> pagination = new Pagination<>();
        pagination.setData(CopyPropertiesUtils.convertSysUserDOToVO(iPage.getRecords()));
        pagination.setTotal(iPage.getTotal());
        return pagination;
    }

    @Override
    public void insertSysUser(SysUserVO sysUserVO, Integer roleId) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(sysUserVO, sysUserDO);
        sysUserDO.setGmtModified(DateUtils.getLocalDateTime());
        sysUserDO.setGmtCreate(DateUtils.getLocalDateTime());
        sysUserDO.setLoginNum(0);
        // 密码加密
        String md5Pwd = Encrypt.md5(sysUserVO.getPassword(), sysUserVO.getUserName());
        sysUserDO.setPassword(md5Pwd);
        sysUserMapper.insert(sysUserDO);
        if (null != roleId) {
            // 添加管理员权限
            SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
            sysUserRoleDO.setUserId(sysUserDO.getUserId());
            sysUserRoleDO.setRoleId(roleId);
            sysUserMapper.insertSysUserRole(sysUserRoleDO);
        }
    }

    @Override
    public SysUserVO getSysUser(Integer userId) {
        SysUserDO sysUserDO = sysUserMapper.selectById(userId);
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUserDO, sysUserVO);
        return sysUserVO;
    }

    @Override
    public void deleteSysUser(Integer userId) {
        // 删除管理员
        sysUserMapper.deleteById(userId);
        // 删除管理员角色
        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setUserId(userId);
        sysUserMapper.deleteSysUserRole(sysUserRoleDO);
    }

    @Override
    public boolean checkIsExistUserName(String userName) {
        SysUserDO user = sysUserMapper.selectOne(new QueryWrapper<SysUserDO>().lambda().eq(SysUserDO::getUserName, userName));
        return null != user;
    }

    @Override
    public void updateSysUser(SysUserVO sysUserVO, Integer roleId, Integer resetPassword) {
        SysUserDO sysUserDO = sysUserMapper.selectById(sysUserVO.getUserId());
        sysUserDO.setGmtModified(DateUtils.getLocalDateTime());
        sysUserDO.setName(sysUserVO.getName());
        sysUserDO.setPhone(sysUserVO.getPhone());
        sysUserDO.setEmail(sysUserVO.getEmail());
        sysUserDO.setUserState(sysUserVO.getUserState());
        sysUserDO.setRemark(sysUserVO.getRemark());
        sysUserDO.setAvatar(sysUserVO.getAvatar());
        // 是否修改密码
        if (null != resetPassword) {
            // 密码加密
            String md5Pwd = Encrypt.md5(sysUserVO.getPassword(), sysUserDO.getUserName());
            sysUserDO.setPassword(md5Pwd);
        }
        // 修改
        sysUserMapper.updateById(sysUserDO);

        // 删除管理员角色
        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setUserId(sysUserDO.getUserId());
        sysUserMapper.deleteSysUserRole(sysUserRoleDO);

        // 添加新角色
        if (null != roleId) {
            sysUserRoleDO.setRoleId(roleId);
            sysUserMapper.insertSysUserRole(sysUserRoleDO);
        }
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword, Integer userId) {
        SysUserDO sysUserDO = sysUserMapper.selectById(userId);
        // 检查旧密码
        String md5Pwd = Encrypt.md5(oldPassword, sysUserDO.getUserName());
        if (!Objects.equals(md5Pwd, sysUserDO.getPassword())) {
            throw new CustomException(PublicCodeEnum.FAIL.getCode(), "原密码输入错误!", CustomException.LOGGER_WARN_TYPE);
        }
        // 修改密码
        md5Pwd = Encrypt.md5(newPassword, sysUserDO.getUserName());
        sysUserDO.setPassword(md5Pwd);
        sysUserDO.setGmtModified(DateUtils.getLocalDateTime());
        sysUserMapper.updateById(sysUserDO);
    }

    @Override
    public void updateUserInfo(SysUserVO sysUserVO) {
        SysUserDO sysUserDO = sysUserMapper.selectById(sysUserVO.getUserId());
        sysUserDO.setAvatar(sysUserVO.getAvatar());
        sysUserDO.setName(sysUserVO.getName());
        sysUserDO.setPhone(sysUserVO.getPhone());
        sysUserDO.setEmail(sysUserVO.getEmail());
        sysUserDO.setGmtModified(DateUtils.getLocalDateTime());
        sysUserMapper.updateById(sysUserDO);
    }

    @Override
    public SysUserVO getSysUserByUserName(String username) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(new QueryWrapper<SysUserDO>().lambda().eq(SysUserDO::getUserName, username));
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUserDO, sysUserVO);
        return sysUserVO;
    }

    @Override
    public void updateForLogin(Integer userId) {
        SysUserDO user = sysUserMapper.selectById(userId);
        user.setLoginTime(LocalDateTime.now());
        user.setLoginNum(user.getLoginNum() + 1);
        user.setGmtModified(LocalDateTime.now());
        sysUserMapper.updateById(user);
    }

    @Override
    public void updateSysUserState(Integer userId, Integer userState) {
        SysUserDO user = sysUserMapper.selectById(userId);
        user.setUserState(userState);
        user.setGmtModified(DateUtils.getLocalDateTime());
        sysUserMapper.updateById(user);
    }

    @Override
    public Integer countSysUser() {
        return sysUserMapper.selectCount(null);
    }

}
