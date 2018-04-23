package com.hfkj.bbt.weixin;

import com.alibaba.fastjson.JSON;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.systemanage.IUserService;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.UserVo;
import com.hfkj.bbt.vo.WxVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "wxapi")
public class WxController {
    private static String appid ="wxd0322c44022739f7";
    private static String secret = "eacd7abad3511aac456b5663eeb14a0f";

    @Autowired
    private IUserService userService;


    @RequestMapping(value = "wxLogin")
    @ResponseBody
    public List  weixinTest2(String code) {
        System.out.println(code);
//第一次交互
        RestTemplate client = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + appid +
                "&secret=" +secret +
                "&js_code=" +code +
                "&grant_type=authorization_code";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        System.out.println("第一步code:"+code+",url1:"+url);
        ResponseEntity<String> response1 = client.exchange(url, HttpMethod.GET, null, String.class);

        String body = response1.getBody();
        WxVo wxVo = JSON.parseObject(body, WxVo.class);
        List list = new ArrayList();
        if(wxVo.getOpenid()==null||"".equals(wxVo.getOpenid())){
            list.add("0");
            list.add("自动登录失败");
            return list;
        }
        User user = userService.findUserByOpenid(wxVo.getOpenid());
        if(user!=null){
            UserVo userIn= UserUtil.UserToVo(user);
            list.add("1");
            list.add(userIn);
            return list;
        }else {
            list.add("0");
            list.add("自动登录失败");
            return list;
        }
    }

    /**
     * 微信登陆接口
     * @param userName
     * @return
     */
    @RequestMapping(value = "wxUserLogin")
    @ResponseBody
    public List restLogin(String userName,String password,String code){

        User user =  userService.findByUserName(userName);
        List list = new ArrayList();
        if(user==null){
            list.add("0");
            list.add("用户名错误");
            return list;
        }
        if (!user.getPassword().equals(password)){
            list.add("2");
            list.add("密码错误");
            return list;
        }else {
//第一次交互
            RestTemplate client = new RestTemplate();
            String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                    "appid=" + appid +
                    "&secret=" +secret +
                    "&js_code=" +code +
                    "&grant_type=authorization_code";
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            ResponseEntity<String> response1 = client.exchange(url, HttpMethod.GET, null, String.class);

            String body = response1.getBody();
            WxVo wxVo = JSON.parseObject(body, WxVo.class);

            if(wxVo.getOpenid()==null||"".equals(wxVo.getOpenid())){
                list.add("0");
                list.add("网络错误");
                return list;
            }

            //保存openid //检查数据库里是否有此openid的User,有的话删除此User的openid
            String r =userService.saveOpenId(userName,wxVo.getOpenid());
            if(r.equals("1")){
                list.add(r);
                list.add(UserUtil.UserToVo(user));
            }else {
                list.add(r);
                list.add("网络错误");
            }

            return list;
        }

    }

    /**微信退出
     *
     */
    @RequestMapping(value = "wxLogout")
    public void wxLogout(String userName){
        if(userName==null){
            return ;
        }
        //退出
        userService.wxLogout(userName);
    }

    /**
     * 根据userId修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "wxUpdatePassword")
    @ResponseBody
    public String updatePassword(Long userId,String oldPwd,String newPwd){
        return  userService.wxUpdatePassword(userId,oldPwd,newPwd);
    }





}
