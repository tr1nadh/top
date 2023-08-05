package com.example.top;

import com.example.top.repository.OrderRepository;
import com.example.top.service.RoleService;
import com.example.top.service.order.OrderCRUDService;
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
	private OrderCRUDService orderService;

	@Test
	void changeRolesNames() {
	}
}
