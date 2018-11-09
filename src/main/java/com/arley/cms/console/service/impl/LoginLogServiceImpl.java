package com.arley.cms.console.service.impl;

import com.arley.cms.console.mapper.LoginLogMapper;
import com.arley.cms.console.pojo.Do.LoginLogDO;
import com.arley.cms.console.pojo.query.LoginLogQuery;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.service.LoginLogService;
import com.arley.cms.console.util.CopyPropertiesUtils;
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

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
@Service("loginLogService")
@Transactional(rollbackFor = Exception.class)
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginLogVO loginLog) {
        LoginLogDO loginLogDO = new LoginLogDO();
        BeanUtils.copyProperties(loginLog, loginLogDO);
        loginLogMapper.insert(loginLogDO);
    }

    @Override
    public Pagination listLoginLogByPage(LoginLogQuery loginLogQuery) {
        LambdaQueryWrapper<LoginLogDO> qw = new QueryWrapper<LoginLogDO>().lambda();
        if (StringUtils.isNotBlank(loginLogQuery.getUserName())) {
            qw.like(LoginLogDO::getUserName, loginLogQuery.getUserName());
        }
        Page<LoginLogDO> page = new Page<>();
        // 分页加排序处理
        if (!PageUtils.pageAndOrderBy(page, loginLogQuery, LoginLogDO.class)) {
            // 设置默认排序
            qw.orderByDesc(LoginLogDO::getLoginTime);
        }
        IPage<LoginLogDO> iPage = loginLogMapper.selectPage(page, qw);
        Pagination<LoginLogVO> pagination = new Pagination<>();
        pagination.setData(CopyPropertiesUtils.convertLoginLogDOToVO(iPage.getRecords()));
        pagination.setTotal(iPage.getTotal());
        return pagination;
    }

}
