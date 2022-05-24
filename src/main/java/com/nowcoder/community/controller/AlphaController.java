package com.nowcoder.community.controller;

import com.nowcoder.community.Util.ProjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getContextPath());
        System.out.println(request.getMethod());
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String a = e.nextElement();
            String b = request.getHeader(a);
            System.out.println("1: " + a + " 2: " + b);
        }
        System.out.println(request.getParameter("a"));

        //response.setContentType("");
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter p = response.getWriter();) {
            p.write("<h3>你好呀</h3>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String ipad(
            @RequestParam(name = "banana", required = false, defaultValue = "10") int apple,
            @RequestParam(name = "apple") int banana) {
        System.out.println(apple);
        System.out.println(banana);
        return "<h1>friends</h1>";
    }

    @RequestMapping(path = "/test2/{hi}/{gaga}", method = RequestMethod.GET)
    @ResponseBody
    public String h2(
            @PathVariable("hi") String id,
            @PathVariable("gaga") String iu) {
        System.out.println(id);
        System.out.println(iu);
        return "Merry";
    }

    @RequestMapping(path = "/m2", method = RequestMethod.POST)
    @ResponseBody
    public String h3(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return null;
    }

    @RequestMapping(path = "/insist", method= RequestMethod.GET)
    public ModelAndView h5(){
        ModelAndView mav =  new ModelAndView();
        mav.addObject("name","zhangsan");
        mav.addObject("tel","18201002836");
        mav.setViewName("/demo/test1");
        return mav;
    }


    @RequestMapping(path = "/eco", method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> h6(Model model){
        Map<String, Object> m = new HashMap<>();
        m.put("name","wangwu");
        m.put("tel","18018920341");
        return m;
    }

    @RequestMapping(path = "/cookie/set", method= RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("code", ProjectUtil.getRandom().substring(0,5));
        cookie.setPath("/community/alpha/cookie");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "success";
    }

    @RequestMapping(path = "/cookie/get", method= RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){

        System.out.println(code);
        return "test cookie";
    }

    @RequestMapping(path = "/session/set", method= RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("name","83");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method= RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}
