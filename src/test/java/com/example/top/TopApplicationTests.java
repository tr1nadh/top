package com.example.top;

import com.example.top.entity.employee.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.entity.employee.Role;
import com.example.top.repository.EmployeeRepository;
import com.example.top.repository.OrderRepository;
import com.example.top.service.EmployeeService;
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

	@Autowired
	private EmployeeService employeeService;

	@Test
	void createAdmin() {
		var role = new Role();
		role.setName("ADMIN");

		service.saveRole(role);

		var account = new Account();
		account.setUsername("admin");
		account.setPassword("pass");

		var employee = Employee.builder()
				.name("admin")
				.account(account)
				.role(role)
				.gender("Male")
				.emailAddress("admin@email.com")
				.phoneNo("8833939393")
				.hasAccount(true)
				.build();

		employeeService.saveEmployee(employee);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	void nowShow() {
		employeeRepository.findAll().forEach(System.out::println);
	}
}
