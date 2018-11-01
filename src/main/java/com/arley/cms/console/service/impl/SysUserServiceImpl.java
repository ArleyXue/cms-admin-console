package com.arley.cms.console.service.impl;

import com.arley.cms.console.mapper.SysUserMapper;
import com.arley.cms.console.pojo.Do.SysUserDO;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SysUserVO getSysUserByUserName(String userName) {
        SysUserDO sysUserDO = sysUserMapper.selectOne(new QueryWrapper<SysUserDO>().lambda().eq(SysUserDO::getUserName, userName));
        if (null == sysUserDO) {
            return null;
        }
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUserDO, sysUserVO);
        return sysUserVO;
    }
}
