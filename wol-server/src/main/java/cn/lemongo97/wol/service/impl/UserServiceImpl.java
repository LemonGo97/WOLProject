package cn.lemongo97.wol.service.impl;

import cn.lemongo97.wol.mapper.RoleMapper;
import cn.lemongo97.wol.mapper.UserMapper;
import cn.lemongo97.wol.model.Role;
import cn.lemongo97.wol.model.User;
import cn.lemongo97.wol.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lemongo97
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().select("*").eq("username", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roles = roleMapper.getByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }
}
