package cn.lemongo97.wol.service.impl;

import cn.lemongo97.wol.model.User;
import cn.lemongo97.wol.repository.UserJpaRepository;
import cn.lemongo97.wol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author lemongo97
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
