package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserMapperTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DiscussPostMapper discussPostMapper;

    @Test
    public void testDPM(){
        List<DiscussPost> res =  discussPostMapper.selectDiscussPosts(0,0,10);
        System.out.println(res);
        System.out.println(res.size());
    }

    @Test
    public void testDPM2(){
        int res =  discussPostMapper.selectDiscussPostRows(149);
        System.out.println(res);
    }

    @Test
    public void testSelect(){
        User user = userMapper.selectUserById(149);
        System.out.println(user);
        user = userMapper.selectUserByUsername("niuke");
        System.out.println(user);
        user = userMapper.selectUserByEmail("nowcoder149@sina.com");
        System.out.println(user);
    }


    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("shc");
        user.setPassword("8850");
        user.setSalt("ABC");
        user.setEmail("nowcoder625@sina.com");
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(null);
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdate(){
        User user = userMapper.selectUserById(150);
        System.out.println(user);
        userMapper.updatePassword(150, "6666");
        userMapper.updateStatus(150, 1);
        userMapper.updateHeader(150, "http://www.nowcoder.com/666.png");
    }

}
