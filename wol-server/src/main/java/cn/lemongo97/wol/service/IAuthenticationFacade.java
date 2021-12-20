package cn.lemongo97.wol.service;

import cn.lemongo97.wol.model.User;
import org.springframework.security.core.Authentication;

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
    User getSessionUser();
}
