package com.nowcoder.community;

import com.nowcoder.community.Util.MailClient;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class MailTests {

	@Autowired
	MailClient mailClient;

	@Autowired
	TemplateEngine templateEngine;

	@Test
	public void testLogger(){

		mailClient.sendMessage("harold625@126.com", "牛客项目邮件功能测试", "testtesttest");

	}

	@Test
	public void testLogger2(){

		Context context = new Context();
		context.setVariable("name","张三");
		//http://www.nowcoder.com/activation/abcdefg123456.html
		String content = templateEngine.process("/mail/htmlMail", context);

		System.out.println(content);

		mailClient.sendMessage("harold625@126.com", "牛客项目邮件功能测试", content);

	}

}
