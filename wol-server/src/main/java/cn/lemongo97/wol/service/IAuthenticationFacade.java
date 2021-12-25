package cn.lemongo97.wol.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationFacade {

    /**
     * 获取当前登录用户信息
     * @return
     */
    Authentication getAuthentication();

    /**
     * 获取当前登录用户名
     * @return
     */
    UserDetails getSessionUser();
}
