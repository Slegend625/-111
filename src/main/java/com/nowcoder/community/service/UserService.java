package com.nowcoder.community.service;

import com.nowcoder.community.Util.ConstUtil;
import com.nowcoder.community.Util.MailClient;
import com.nowcoder.community.Util.ProjectUtil;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements ConstUtil {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    MailClient mailClient;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    String domain;

    @Value("${server.servlet.context-path}")
    String context_path;

    public User findUserById(int userid){
        return userMapper.selectUserById(userid);
    }

    public Map<String, Object> insertAndSendMail(User user){

        Map<String, Object> map = new HashMap<>();

        if(user == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg", "邮件不能为空!");
            return map;
        }
        if(user.getPassword().length()<8){
            map.put("passwordMsg", "密码长度不能小于8位!");
            return map;
        }

        User u = userMapper.selectUserByUsername(user.getUsername());
        if(u != null){
            map.put("usernameMsg", "账号已存在!");
            return map;
        }

//        u = userMapper.selectUserByEmail(user.getEmail());
//        if(u != null){
//            map.put("emailMsg", "邮箱已存在!");
//            return map;
//        }
        user.setSalt(ProjectUtil.getRandom().substring(0,5));
        user.setPassword(ProjectUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(ProjectUtil.getRandom());
        user.setHeaderUrl("http://images.nowcoder.com/head/"+ new Random().nextInt(1000) +"t.png");
        user.setCreateTime(new Date());
        int rows = userMapper.insertUser(user);
        int id = user.getId();

        String activation_url = domain + "/" + context_path + "/" + "activation" + "/" + id + "/" + user.getActivationCode();

        Context context = new Context();
        context.setVariable("email",user.getEmail());
        context.setVariable("activation_url",activation_url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMessage(user.getEmail(), "牛客网账户注册激活邮件", content);
        return map;
    }

    public int activate(int id, String code){
        User u = userMapper.selectUserById(id);
        if(u == null){
            return ACTIVATION_FAILURE;
        }else{
            if(!u.getActivationCode().equals(code)){
                return ACTIVATION_FAILURE;
            }else if(u.getStatus()==1){
                return ACTIVATION_REPEAT;
            }else{
                userMapper.updateStatus(id, 1);
                return ACTIVATION_SUCCESS;
            }

        }

    }

    public Map<String, Object> decideAndEnter(User user){

        Map<String, Object> map = new HashMap<>();

        if(user == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if(user.getPassword().length()<8){
            map.put("passwordMsg", "密码长度不能小于8位!");
            return map;
        }

        User u = userMapper.selectUserByUsername(user.getUsername());
        if(u == null){
            map.put("usernameMsg", "账号不存在!");
            return map;
        }
        if(!ProjectUtil.md5(user.getPassword()+u.getSalt()).equals(u.getPassword())){
            map.put("passwordMsg", "密码错误!");
        }

        return map;


    }




}
