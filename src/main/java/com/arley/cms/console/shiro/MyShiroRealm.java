package com.arley.cms.console.shiro;

import com.arley.cms.console.component.RedisDao;
import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.exception.CustomException;
import com.arley.cms.console.pojo.vo.AdminTokenVO;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.SysUserService;
import com.arley.cms.console.util.FastJsonUtils;
import com.arley.cms.console.util.JJWTUtils;
import com.arley.cms.console.util.RedisKeyUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 自定义Realm
 * @author Wang926454
 * @date 2018/8/30 14:10
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisDao redisDao;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }



    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        Claims claims = JJWTUtils.parseJWT(token);
        AdminTokenVO adminTokenVO = FastJsonUtils.json2Bean(claims.getSubject(), AdminTokenVO.class);
        SysUserVO sysUserVO = sysUserService.getSysUserByUserName(adminTokenVO.getUserName());
        // 查询用户是否存在

        if (sysUserVO == null) {
            throw new CustomException(PublicCodeEnum.FAIL.getCode(), "账号不存在!", CustomException.LOGGER_WARN_TYPE);
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        String appUserTokenKey = RedisKeyUtils.getAppUserTokenKey(adminTokenVO.getUserName());
        String tokenVar1 = (String) redisDao.get(appUserTokenKey);
        if (StringUtils.isBlank(tokenVar1)) {
            // token过期
            throw new ExpiredCredentialsException("token过期");
        }
        if (!Objects.equals(tokenVar1, token)) {
            throw new IncorrectCredentialsException("token无效");
        }
        return new SimpleAuthenticationInfo(token, token, this.getClass().getName());
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
       /* String account = JwtUtil.getClaim(principals.toString(), Constant.ACCOUNT);
        UserDto userDto = new UserDto();
        userDto.setAccount(account);
        // 查询用户角色
        List<RoleDto> roleDtos = roleMapper.findRoleByUser(userDto);
        for (int i = 0, roleLen = roleDtos.size(); i < roleLen; i++) {
            RoleDto roleDto = roleDtos.get(i);
            // 添加角色
            simpleAuthorizationInfo.addRole(roleDto.getName());
            // 根据用户角色查询权限
            List<PermissionDto> permissionDtos = permissionMapper.findPermissionByRole(roleDto);
            for (int j = 0, perLen = permissionDtos.size(); j < perLen; j++) {
                PermissionDto permissionDto = permissionDtos.get(j);
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permissionDto.getPerCode());
            }
        }*/
        return simpleAuthorizationInfo;
    }

}