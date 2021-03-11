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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.TicketManagementSystem.api.controller.CategoryController;
import com.example.TicketManagementSystem.api.controller.GroupController;
import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.dao.models.Groups;
import com.example.TicketManagementSystem.api.dao.models.User;
import com.example.TicketManagementSystem.api.repository.CategoryRepository;
import com.example.TicketManagementSystem.api.repository.EnUserType;
import com.example.TicketManagementSystem.api.repository.GroupRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.TicketManagementSystem.api.controller.CategoryController;
import com.example.TicketManagementSystem.api.controller.TicketController;
import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.dao.models.Ticket;
import com.example.TicketManagementSystem.api.dao.models.User;
import com.example.TicketManagementSystem.api.repository.CategoryRepository;
import com.example.TicketManagementSystem.api.repository.EnPriorityType;
import com.example.TicketManagementSystem.api.repository.EnStatusType;
import com.example.TicketManagementSystem.api.repository.EnUserType;
import com.example.TicketManagementSystem.api.repository.TicketRepository;
import com.example.TicketManagementSystem.api.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
//import org.junit.Assert;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class GroupControllerTest {

	@InjectMocks
	GroupController groupController;

	@Mock
	GroupRepository groupRepository;
	
	@Autowired
    private TestRestTemplate restTemplate;
     
    @LocalServerPort
    int randomServerPort;

	@Test
	public void testFindAll() {

		Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");

		Category c1 = new Category(1, "IT");
		g1.setCategory(c1);

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

		g1.setUser(list1);

		Groups g2 = new Groups(2, "group2", c1, list1);

		List<Groups> glist = new ArrayList<Groups>();
		glist.add(g1);
		glist.add(g2);

		when(groupRepository.findAll()).thenReturn(glist);

		List<Groups> list2 = groupController.getAllGroups();
		assertThat(list2.size()).isEqualTo(2);
		assertThat(list2.get(0).getGroupId()).isEqualTo(g1.getGroupId());
		assertThat(list2.get(0).getGroupName()).isEqualTo(g1.getGroupName());
		assertThat(list2.get(0).getCategory().getCategoryId()).isEqualTo(g1.getCategory().getCategoryId());
		assertThat(list2.get(0).getUser().get(0).getUserId()).isEqualTo(g1.getUser().get(0).getUserId());

	}

	@Test
	public void testCreatGroup() throws URISyntaxException {
		
		final String baseUrl = "http://localhost:"+randomServerPort+"/group";
        URI uri = new URI(baseUrl);
        
        Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");

		Category c1 = new Category(1, "IT");
		g1.setCategory(c1);
		
		HttpHeaders headers = new HttpHeaders();
        headers.set("email", "true"); 
        
        HttpEntity<Groups> request = new HttpEntity<>(g1, headers);
        
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        assertThat(result.getStatusCodeValue()).isEqualTo(404);
		

		/*MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(groupRepository.save(any(Groups.class))).thenReturn(null);

		Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");

		Category c1 = new Category(1, "IT");
		g1.setCategory(c1);

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
		user2.setType(EnUserType.MEMBER);

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

		g1.setUser(list1);
		Groups g2 = new Groups(2, "group2", c1, list1);
		List<Groups> glist = new ArrayList<Groups>();
		glist.add(g1);
		glist.add(g2);
		when(groupRepository.findAll()).thenReturn(glist);
		
		String email = glist.get(0).getUser().get(0).getEmail();

		ResponseEntity<Groups> responseEntity = groupController.createGroup(email, g1);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);*/

	}
	
	@Test
	public void testGetGroupById() {
		
		Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");
		
		Groups g2 = new Groups();
		g2.setGroupId(2);
		g2.setGroupName("group2");
		
		List<Groups> glist = new ArrayList<Groups>();
		glist.add(g1);
		glist.add(g2);

		when(groupRepository.findAll()).thenReturn(glist);
		
		ResponseEntity<Groups> responseEntity = groupController.getGroupByID(g1.getGroupId());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	public void testGetGroupByIdNotFound() {
		
		Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");
		
		Groups g2 = new Groups();
		g2.setGroupId(2);
		g2.setGroupName("group2");
		
		List<Groups> glist = new ArrayList<Groups>();
		glist.add(g1);
		glist.add(g2);

		when(groupRepository.findAll()).thenReturn(glist);
		
		ResponseEntity<Groups> responseEntity = groupController.getGroupByID(3);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
		
	}
	@Test
	public void testGetMember() {

		Groups g1 = new Groups();
		g1.setGroupId(1);
		g1.setGroupName("group1");

		Category c1 = new Category(1, "IT");
		g1.setCategory(c1);

		User user1 = new User();
		user1.setUserId(1);
		user1.setName("user1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("12345");
		user1.setType(EnUserType.MEMBER);

		User user2 = new User();
		user2.setUserId(2);
		user2.setName("user2");
		user2.setEmail("user2@gmail.com");
		user2.setPassword("12345");
		user2.setType(EnUserType.MEMBER);

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

		g1.setUser(list1);
		Groups g2 = new Groups(2, "group2", c1, list1);
		
		List<Groups> glist = new ArrayList<Groups>();
		glist.add(g1);
		glist.add(g2);
		
		when(groupRepository.findAll()).thenReturn(glist);
		
		List<User> list2 = groupController.getMembers(1);
		
		assertThat(list2.size()).isEqualTo(3);
		
	}

}
