package com.example.TicketManagementSystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.TicketManagementSystem.api.controller.CategoryController;
import com.example.TicketManagementSystem.api.controller.UserController;
import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.dao.models.Groups;
import com.example.TicketManagementSystem.api.dao.models.User;
import com.example.TicketManagementSystem.api.repository.CategoryRepository;
import com.example.TicketManagementSystem.api.repository.EnUserType;
import com.example.TicketManagementSystem.api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserRepository userRepository;

	@Test
	public void testAddUser() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(userRepository.save(any(User.class))).thenReturn(null);
		User user = new User();
		user.setUserId(1);
		user.setName("user1");
		user.setEmail("user1@gmail.com");
		user.setPassword("12345");
		user.setType(EnUserType.ADMIN);

		ResponseEntity<User> responseEntity = userController.registerUser(user);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void testloginNotFound() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		//when(userRepository.save(any(User.class))).thenReturn(null);

		User user3 = new User();
		user3.setUserId(2);
		user3.setName("user3");
		user3.setEmail("user3@gmail.com");
		user3.setPassword("12345");
		user3.setType(EnUserType.MEMBER);

		ResponseEntity<User> responseEntity = userController.loginUser(user3);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);

	}

	@Test
	public void testloginFound() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		//when(userRepository.save(any(User.class))).thenReturn(null);

		User user3 = new User();
		user3.setUserId(2);
		user3.setName("user3");
		user3.setEmail("user3@gmail.com");
		user3.setPassword("12345");
		user3.setType(EnUserType.MEMBER);

		List<User> list1 = new ArrayList<User>();
		list1.add(user3);

		when(userRepository.findAll()).thenReturn(list1);
		List<User> list2 = userController.getAllMembers();

		ResponseEntity<User> responseEntity = userController.loginUser(list2.get(0));
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	public void testFindAll() {

		User user1 = new User();
		user1.setUserId(1);
		user1.setName("user1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("12345");
		user1.setType(EnUserType.ADMIN);

		User user2 = new User();
		user2.setUserId(2);
		user2.setName("user2");
		user2.setEmail("user2@gmail.com");
		user2.setPassword("12345");
		user2.setType(EnUserType.USER);

		User user3 = new User();
		user3.setUserId(2);
		user3.setName("user3");
		user3.setEmail("user3@gmail.com");
		user3.setPassword("12345");
		user3.setType(EnUserType.MEMBER);

		List<User> list1 = new ArrayList<User>();
		list1.add(user1);
		list1.add(user2);
		list1.add(user3);

		when(userRepository.findAll()).thenReturn(list1);
		List<User> list2 = userController.getAll();

		assertThat(list2.size()).isEqualTo(3);
		assertThat(list2.get(0).getName()).isEqualTo(user1.getName());
		assertThat(list2.get(0).getUserId()).isEqualTo(user1.getUserId());
		assertThat(list2.get(0).getEmail()).isEqualTo(user1.getEmail());
		assertThat(list2.get(0).getPassword()).isEqualTo(user1.getPassword());
		assertThat(list2.get(0).getType()).isEqualTo(user1.getType());

	}

	@Test
	public void testFindAllMember() {

		User user3 = new User();
		user3.setUserId(2);
		user3.setName("user3");
		user3.setEmail("user3@gmail.com");
		user3.setPassword("12345");
		user3.setType(EnUserType.MEMBER);

		List<User> list1 = new ArrayList<User>();
		list1.add(user3);

		when(userRepository.findAll()).thenReturn(list1);
		List<User> list2 = userController.getAllMembers();

		assertThat(list2.size()).isEqualTo(1);
		assertThat(list2.get(0).getName()).isEqualTo(user3.getName());
		assertThat(list2.get(0).getUserId()).isEqualTo(user3.getUserId());
		assertThat(list2.get(0).getEmail()).isEqualTo(user3.getEmail());
		assertThat(list2.get(0).getPassword()).isEqualTo(user3.getPassword());
		assertThat(list2.get(0).getType()).isEqualTo(user3.getType());

	}

}
