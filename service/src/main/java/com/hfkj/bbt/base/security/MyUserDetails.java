package com.hfkj.bbt.base.security;


import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class MyUserDetails extends org.springframework.security.core.userdetails.User {


    private User user;

    private Set<Role> roleList;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }
}
