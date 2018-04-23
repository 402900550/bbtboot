package com.hfkj.bbt.systemanage.impl;


import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.Subject;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IUserService;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017-10-26.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Object[]> findAllUsers() {
        String jpql = "SELECT " +
                " CONCAT(IFNULL(s.name,''),'',IFNULL(comp.name,''))," +
                " u.userName," +
                " u.realName," +
                " r.description," +
                " u.sex," +
                " sub.subjectName," +
                " u.icardNo," +
                " u.email," +
                " u.phone," +
                " u.id  " +
                " FROM User u LEFT JOIN u.school s LEFT JOIN u.subject sub LEFT JOIN u.roles r LEFT JOIN u.company comp ";
        return userRepository.findListByJpql(jpql);
    }

    @Override
    public List<Subject> findAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Role> finRolesByCurrentUser() {
        Role role = UserUtil.getCurrentMostHighRole();
        List<Role> roles = new ArrayList<>();
        if (Constants.SCHOOLADMIN.equals(role.getRoleName())) {
            roles.add(roleRepository.findByRoleName(Constants.TEACHER));
        } else if (Constants.DISTNCTADMIN.equals(role.getRoleName())) {
            roles.add(roleRepository.findByRoleName(Constants.SCHOOLADMIN));
        } else if (Constants.HUANFANGADMIN.equals(role.getRoleName())) {
            roles.addAll(roleRepository.findAll());
        }
        return roles;
    }

    @Override
    public User findCurrentUser() {
        User currentUser = UserUtil.getCurrentUser();
        return userRepository.findOne(currentUser.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public String doSaveUser(UserVo userVo) {
        User user = new User();
        user.setCreateDate(new Timestamp(new Date().getTime()));
        user.setPassword(Constants.PWD);
        user.setEmail(userVo.getEmail());
        user.setIcardNo(userVo.getIcardNo());
        user.setPhone(userVo.getPhone());
        user.setRealName(userVo.getRealName());
        user.setSex(userVo.getSex());
        if (!ComUtil.stringIsNotNull(userVo.getUserName())){
            return "用户名不能为空!";
        }
        user.setUserName(userVo.getUserName());
        user.setStatus(1);
        if (userVo.getSubjectId()!=null){
            user.setSubject(subjectRepository.findOne(userVo.getSubjectId()));
        }
        if (userVo.getCompanyId()!=null){
            user.setCompany(companyRepository.findOne(userVo.getCompanyId()));
        }

        Set<Role> roles=new HashSet<>();
        Long roleId = userVo.getRoleId();
        if (roleId==null){
            return "请选择角色!";
        }
        roles.add(roleRepository.findOne(userVo.getRoleId()));
        user.setRoles(roles);

        String schoolCode = userVo.getSchoolCode();
        if (ComUtil.stringIsNotNull(schoolCode)){
            user.setSchool(schoolRepository.findOne(userVo.getSchoolCode()));
        }


        userRepository.save(user);
        return "新增成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteUserById(Long deleteId) {
        String sql="DELETE FROM tab_user_to_role WHERE user_id=:userId";
        Map<String,Object> param=new HashMap<>();
        param.put("userId",deleteId);

        User deleteOne = userRepository.findOne(deleteId);
        User currentUser=userRepository.findOne(UserUtil.getCurrentUser().getId());

        if (deleteId.equals(currentUser.getId())){
            return "不能删除自己!";
        }

        boolean flag=UserUtil.hasAuthority(currentUser,deleteOne);
        if (flag){
            userRepository.executeUpdateBySql(sql,param);
            userRepository.delete(deleteId);
            return "删除成功!";
        }
        return "没有权限!";
    }

    @Override
    public User findUserByUserId(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public String doModifyUser(UserVo userVo) {
        Long userId = userVo.getUserId();

        User modifyOne = userRepository.findOne(userId);
        User currentUser=userRepository.findOne(UserUtil.getCurrentUser().getId());

        boolean flag =UserUtil.hasAuthority(currentUser,modifyOne);
        if (!flag){
            return "没有权限!";
        }
        modifyOne.setRealName(userVo.getRealName());
        modifyOne.setSex(userVo.getSex());
        modifyOne.setPhone(userVo.getPhone());
        modifyOne.setEmail(userVo.getEmail());
        modifyOne.setIcardNo(userVo.getIcardNo());
        if (userVo.getSubjectId()!=null){
            modifyOne.setSubject(subjectRepository.findOne(userVo.getSubjectId()));
        }
        try {
            userRepository.save(modifyOne);
        }catch (Exception e){
            return "保存失败!";
        }
        return "保存成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String modifyPassword(String newPassword) {
        User currentUser = UserUtil.getCurrentUser();
        currentUser.setPassword(newPassword);
        userRepository.save(currentUser);
        return "success";
    }

    @Override
    public User findUserByOpenid(String openid){
        return userRepository.findByOpenid(openid);
    }
    @Override
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = false)
    public String saveOpenId(String userName,String openid){
        try {
            if(userRepository.findByOpenid(openid)!=null) {
                User user0 =userRepository.findByOpenid(openid);
                user0.setOpenid(null);
                userRepository.save(user0);
            }
            User user = findByUserName(userName);
            user.setOpenid(openid);
            userRepository.save(user);
            return "1";
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void wxLogout(String userName){
        User user =userRepository.findByUserName(userName);
        user.setOpenid(null);
        userRepository.save(user);
    }

    /**
     * 微信修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @Transactional(readOnly = false)
    public String wxUpdatePassword(Long userId,String oldPwd,String newPwd){
        User user = userRepository.findOne(userId);
        if(user==null){
            return "0";//数据为空
        }else if(!user.getPassword().equals(oldPwd)){
            return "2";//原密码错误
        }else if(user.getPassword().equals(oldPwd)){
            user.setPassword(newPwd);
            userRepository.save(user);
            return "1";//修改成功
        }else {
            return "3";//错误未知
        }
    }


}







