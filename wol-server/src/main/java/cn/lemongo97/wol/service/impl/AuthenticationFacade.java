package cn.lemongo97.wol.service.impl;

import cn.lemongo97.wol.service.IAuthenticationFacade;
import cn.lemongo97.wol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails getSessionUser() {

        return userService.loadUserByUsername((String) getAuthentication().getPrincipal());
    }
}