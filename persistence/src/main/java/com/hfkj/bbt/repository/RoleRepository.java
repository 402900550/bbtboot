package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends BaseRepository<Role, Long> {

    Role findByRoleName(String roleName);
}
