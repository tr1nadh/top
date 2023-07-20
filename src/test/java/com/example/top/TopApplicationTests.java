package com.example.top;

import com.example.top.repository.OrderRepository;
import com.example.top.service.RoleService;
import com.example.top.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TopApplicationTests {

	@Autowired
	private RoleService service;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderService orderService;

	@Test
	void changeRolesNames() {
		for (var role : service.findAllRoles()) {
			var roleName = role.getName();
			var capitalRoleName = roleName.toUpperCase();
			var prefixedRole = capitalRoleName.substring(0, 1).toUpperCase() + capitalRoleName.substring(1).toLowerCase();
			System.out.println(prefixedRole);
			role.setName(prefixedRole);
			service.saveRole(role);
		}
	}
}
