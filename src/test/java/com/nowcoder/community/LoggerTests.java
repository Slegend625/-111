package com.nowcoder.community;

import com.nowcoder.community.entity.Page;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class LoggerTests{

	private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);


	@Test
	public void testLogger(){

		System.out.println(logger.getName());

		logger.debug("debug!");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");

	}

}
