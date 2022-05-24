package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/enter", method= RequestMethod.POST)
    public String enter(Model model, User user, String verifycode, HttpSession session) {
        //查库
        //用户名/密码/验证码
        //存状态
        Map<String, Object> map = userService.decideAndEnter(user);


        if(!verifycode.equalsIgnoreCase((String) session.getAttribute("kaptchatext"))){
            model.addAttribute("vericode", "验证码输入错误");
            model.addAttribute("verifycode", "");
            return "/site/login";
        }

        if(map == null || map.isEmpty()){
            //model.addAttribute("text", "激活邮件已发到填写邮箱");
            //model.addAttribute("href","/index");
            session.setAttribute("status","login");
            System.out.println("login");
            model.addAttribute("text", "已登录");
            model.addAttribute("href","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }



}
