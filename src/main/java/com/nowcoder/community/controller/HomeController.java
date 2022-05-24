package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method= RequestMethod.GET)
    public String home(Model model, Page page){

        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list= discussPostService.findDiscussPosts(0, page.getCurrent(), page.getLimit());
        List<Map<String, Object>> res = new ArrayList<>();
        if(list != null){
            for(DiscussPost p : list){
                Map<String, Object> map = new HashMap<>();
                map.put("post", p);
                map.put("user", userService.findUserById(p.getUserId()));
                res.add(map);
            }

        }
        model.addAttribute("discussposts", res);
        return "/index";
    }


}
