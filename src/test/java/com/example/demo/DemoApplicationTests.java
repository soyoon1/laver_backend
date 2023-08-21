
package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DemoApplicationTests {

	@Test
	void jwtText() {
		int userId = 1;
		String id = "loginId";
		String secretKey = "MzcwODIyN0I1ODA0OUI0MEEyMkJENDlEQ0YyRjJBQjJCNEU0RkRFMjA1QUUyMDUyQjMzRjk5NzM3OERGOUQ1NA==";

		String token = JwtUtil.createToken(userId, id, Role.USER,1000 * 60 * 60* 24L);
		System.out.println("token: " + token);

		boolean isExpired = JwtUtil.isExpired(token);

		System.out.println("isExpired: " + isExpired);
	}
}

