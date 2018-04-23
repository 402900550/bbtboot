package com.hfkj.bbt.base.security;

import com.hfkj.bbt.exception.MyUserNameNotFoundException;
import com.hfkj.bbt.repository.RoleRepository;
import com.hfkj.bbt.repository.UserRepository;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.util.ComUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName);
        if(null==user){
            throw new MyUserNameNotFoundException("notRegister");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        //获得该用户对应的所有角色
        Set<Role> userRoles = user.getRoles();
        SimpleGrantedAuthority authority;
        if(ComUtil.setNotNull(userRoles)){
            for(Role role:userRoles){
                authority = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(authority);
            }
        } else {
            //若没有角色 默认添加最低权限角色
            Role role = roleRepository.findOne(1L);
            userRoles.add(role);
            authority = new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(authority);
        }
        MyUserDetails myUserDetails = new MyUserDetails(user.getUserName(),user.getPassword(),authorities);
        myUserDetails.setUser(user);
        myUserDetails.setRoleList(userRoles);
        return myUserDetails;
    }
}
