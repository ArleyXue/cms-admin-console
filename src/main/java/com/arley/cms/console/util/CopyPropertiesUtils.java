package com.arley.cms.console.util;

import com.arley.cms.console.pojo.Do.LoginLogDO;
import com.arley.cms.console.pojo.Do.SysPermissionDO;
import com.arley.cms.console.pojo.Do.SysUserDO;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.pojo.vo.SysPermissionVO;
import com.arley.cms.console.pojo.vo.SysUserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XueXianlei
 * @Description: 转换工具类
 * @date 2018/9/17 18:24
 */
public class CopyPropertiesUtils {

    /**
     * 菜单转换 DO to VO
     * @param sysPermissionDOList
     * @return
     */
    public static List<SysPermissionVO> convertSysPermissionDOToVO(List<SysPermissionDO> sysPermissionDOList) {
        List<SysPermissionVO> sysPermissionVOList = new ArrayList<>();
        if (CommonUtils.isNotEmptyCollection(sysPermissionDOList)) {
            sysPermissionDOList.forEach(e -> {
                SysPermissionVO vo = new SysPermissionVO();
                BeanUtils.copyProperties(e, vo);
                // 利用递归层级转换
                if (CommonUtils.isNotEmptyCollection(e.getPermissionList())) {
                    vo.setPermissionList(convertSysPermissionDOToVO(e.getPermissionList()));
                }
                sysPermissionVOList.add(vo);
            });
        }
        return sysPermissionVOList;
    }

    /**
     * 系统用户转换 DO to VO
     * @param sysUserDOList
     * @return
     */
    public static List<SysUserVO> convertSysUserDOToVO(List<SysUserDO> sysUserDOList) {
        List<SysUserVO> sysUserVOList = new ArrayList<>();
        if (CommonUtils.isNotEmptyCollection(sysUserDOList)) {
            sysUserDOList.forEach(e -> {
                SysUserVO vo = new SysUserVO();
                BeanUtils.copyProperties(e, vo);
                sysUserVOList.add(vo);
            });
        }
        return sysUserVOList;
    }

    /**
     * 登录日志转换 DO to VO
     * @param loginLogDOList
     * @return
     */
    public static List<LoginLogVO> convertLoginLogDOToVO(List<LoginLogDO> loginLogDOList) {
        List<LoginLogVO> loginLogVOList = new ArrayList<>();
        if (CommonUtils.isNotEmptyCollection(loginLogDOList)) {
            loginLogDOList.forEach(e -> {
                LoginLogVO vo = new LoginLogVO();
                BeanUtils.copyProperties(e, vo);
                loginLogVOList.add(vo);
            });
        }
        return loginLogVOList;

    }
}
