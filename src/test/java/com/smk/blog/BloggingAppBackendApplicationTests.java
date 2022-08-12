package com.smk.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smk.blog.repositories.UserRepository;

@SpringBootTest
class BloggingAppBackendApplicationTests {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testRepo() {
		
		String classname = userRepository.getClass().getName();
		Package package1 = userRepository.getClass().getPackage();
		
		System.out.println(classname);
		System.out.println(package1);
		
		
	}

}
