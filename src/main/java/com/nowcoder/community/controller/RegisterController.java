package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.Util.ConstUtil;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RegisterController implements ConstUtil {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model){
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model){
        return "/site/login";
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String mail(Model model, User user){

        Map<String, Object> map = userService.insertAndSendMail(user);
        if(map == null || map.isEmpty()){
            model.addAttribute("text", "激活邮件已发到填写邮箱");
            model.addAttribute("href","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }

    @RequestMapping(path = "/activation/{id}/{activationCode}", method = RequestMethod.GET)
    public String mail(
            Model model,
            @PathVariable("id") int id,
            @PathVariable("activationCode") String code){
        int msg = userService.activate(id, code);
        if(msg == ACTIVATION_SUCCESS){
            model.addAttribute("text", "账号已激活成功");
            model.addAttribute("href","/login");
        }else if(msg == ACTIVATION_REPEAT){
            model.addAttribute("text", "账号重复激活");
            model.addAttribute("href","/login");
        }else{
            model.addAttribute("text", "账号激活失败");
            model.addAttribute("href","/index");
        }

        return "/site/operate-result";
    }


    @RequestMapping(path = "/kaptcha", method= RequestMethod.GET)
    public void kaptcha(HttpServletResponse response, HttpSession session){
        String text = kaptchaProducer.createText();
        BufferedImage image =  kaptchaProducer.createImage(text);

        session.setAttribute("kaptchatext", text);

        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png", os);
        } catch (IOException e) {
            logger.error("响应失败");
        }
    }



}
