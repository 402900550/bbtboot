package com.hfkj.bbt.systemanage;



import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.Subject;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.vo.UserVo;


import java.util.List;

/**
 * Created by Administrator on 2017-10-26.
 */
public interface IUserService {

    List<Object[]> findAllUsers();

    List<Subject> findAllSubject();

    List<Role> finRolesByCurrentUser();

    User findCurrentUser();

    String doSaveUser(UserVo userVo);

    String deleteUserById(Long deleteId);

    User findUserByUserId(Long userId);

    String doModifyUser(UserVo userVo);

    String modifyPassword(String newPassword);

    User findUserByOpenid(String openid);

    User findByUserName(String userName);

    String saveOpenId(String userName,String openid);

    void wxLogout(String userName);

    /**
     * 微信修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    String wxUpdatePassword(Long userId,String oldPwd,String newPwd);

}

