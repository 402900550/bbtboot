package com.hfkj.bbt.repository;


import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;


/**
 * Created by Administrator on 2018-01-11.
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUserName(String userName);

    User findByIcardNo(String icardNo);

    List<User> findBySchool(School school);

    User findByOpenid(String openid);
}

