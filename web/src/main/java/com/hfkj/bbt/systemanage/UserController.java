package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.Subject;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/15 0015.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 保存用户
     * @return
     */
    @PostMapping(value = "addUser")
    public String doAddUser(UserVo userVo){
        return userService.doSaveUser(userVo);
    }

    /**
     * 加载用户表格
     * @return
     */
    @GetMapping(value = "loadUserList")
    public Map<String,Object> selectUserList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",userService.findAllUsers());
        return map;
    }
    /**
     * 查询要修改的用户根据userID
     */
    @PostMapping(value = "findUserByUserId")
    public UserVo findUserByUserId(Long userId){
        UserVo userVo=new UserVo();
        User user=userService.findUserByUserId(userId);
        userVo.setRealName(user.getRealName());
        userVo.setSex(user.getSex());
        userVo.setPhone(user.getPhone());
        userVo.setEmail(user.getEmail());
        userVo.setIcardNo(user.getIcardNo());
        userVo.setUserId(userId);
        Subject subject = user.getSubject();
        if (subject!=null){
            userVo.setSubjectId(subject.getId());
        }
        return userVo;
    }


    /**
     * 执行修改用户
     * @param userVo
     * @return
     */
    @PostMapping(value = "modifyUser")
    public String doModifyUser(UserVo userVo){
        return userService.doModifyUser(userVo);
    }



    /**
     *
     * @param deleteId 被删除的用户ID
     * @return
     */
    @PostMapping(value = "deleteUser")
    public String deleteUser(Long deleteId){

        return userService.deleteUserById(deleteId);
    }



    @PostMapping(value = "modifyPassword")
    public String modifyPassword(String oldPwd,String newPwd,String comNewPwd){
        if(ComUtil.stringIsNotNull(oldPwd,newPwd,comNewPwd)){
            User currentUser = userService.findCurrentUser();
            String password = currentUser.getPassword();
            if(newPwd.equals(comNewPwd)){
                if(oldPwd.equals(password)){
                   return userService.modifyPassword(newPwd);
                }else if(newPwd.equals(oldPwd)){
                    return "新密码不能与旧密码一样!";
                }else {

                    return "旧密码错误!";
                }

            }else {
                return "两个新密码不一致!";
            }

        }
        return "任何一个密码不能为空!";
    }
}
