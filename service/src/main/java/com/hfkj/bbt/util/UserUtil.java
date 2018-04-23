package com.hfkj.bbt.util;

import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.entity.Subject;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.base.security.MyUserDetails;
import com.hfkj.bbt.vo.UserVo;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class UserUtil {

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getCurrentUser() {
        return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    /**
     * 获取当前用户的角色
     *
     * @return
     */
    public static Role getCurrentMostHighRole() {
        Set<Role> roles = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleList();
        return sortAthu(roles);
    }


    private static Role sortAthu(Set<Role> roles) {
        List<Role> sortList = new ArrayList<>(roles);
        //排序
        Comparator<Role> comparator = new Comparator<Role>() {
            @Override
            public int compare(Role o1, Role o2) {
                return o2.getRoleLevel() - o1.getRoleLevel();
            }
        };

        if (null != roles) {
            Collections.sort(sortList, comparator);
        }
        return sortList.get(0);
    }


    /**
     * 判断是否具有权限
     *
     * @param currentUser 当前用户
     * @param opUser      被操作的用户
     * @return
     */
    public static boolean hasAuthority(User currentUser, User opUser) {
        Role role = getCurrentMostHighRole();
        if (currentUser.getId().equals(opUser.getId())) {
            return true;
        }
        School opSchool = opUser.getSchool();
        School currentSchool = currentUser.getSchool();
        boolean flag = false;
        if (ComUtil.setNotNull(opUser.getRoles())) {
            Iterator<Role> iterator = opUser.getRoles().iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                String roleName = next.getRoleName();
                if (Constants.HUANFANGADMIN.equals(role.getRoleName())) {
                    return true;
                }

                if (Constants.DISTNCTADMIN.equals(role.getRoleName())) {
                    if (Constants.SCHOOLADMIN.equals(roleName) || Constants.DISTNCTUSER.equals(roleName)) {
                        flag = true;
                    }
                }

                if (Constants.SCHOOLADMIN.equals(role.getRoleName())) {
                    if (Constants.TEACHER.equals(roleName)) {
                        if (currentSchool != null && opSchool != null) {
                            if (opSchool.getSchoolCode() != null && currentSchool.getSchoolCode().equals(opSchool.getSchoolCode())) {
                                flag = true;
                            }
                        }
                    }
                }


            }
        }
        return flag;
    }


    public static UserVo UserToVo(User user) {
        UserVo userIn = new UserVo();
        userIn.setUserId(user.getId());
        userIn.setRealName(user.getRealName());
        userIn.setSex(user.getSex());
        Role role = sortAthu(user.getRoles());
        userIn.setRoleId(role.getId());
        userIn.setRoleName(role.getRoleName());

        School school = user.getSchool();
        if (school == null) {
            userIn.setSchoolCode("");
            userIn.setSchoolName("");
        } else {
            userIn.setSchoolCode(user.getSchool().getSchoolCode());
            userIn.setSchoolName(user.getSchool().getName());
        }
        userIn.setEmail(user.getEmail());
        userIn.setPhone(user.getPhone());
        Subject subject = user.getSubject();
        if (subject != null) {
            userIn.setSubjectName(subject.getSubjectName());
        }

        userIn.setIcardNo(user.getIcardNo());
        userIn.setUserName(user.getUserName());
        return userIn;
    }


}
