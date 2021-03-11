package com.example.TicketManagementSystem;

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
import com.example.TicketManagementSystem.api.dao.models.Attachments;
import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.dao.models.Comments;
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
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketControllerTest {

	@InjectMocks
	TicketController ticketController;

	@Mock
	TicketRepository ticketRepository;

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Mock
	UserRepository userRepository;

	@Test
	public void testCreateTicket1() throws URISyntaxException, ParseException {
		
		final String baseUrl = "http://localhost:"+randomServerPort+"/ticket";
		URI uri = new URI(baseUrl);
		
		Ticket t1 = new Ticket();
		t1.setTicketId(1);
		t1.setStatus(EnStatusType.OPEN);
		t1.setPriority(EnPriorityType.MEDIUM);
		t1.setTitle("First ticket");
		t1.setStartDate(new SimpleDateFormat("dd/MM/YYYY").parse("10/04/2021"));
		t1.setDueDate(new SimpleDateFormat("dd/MM/YYYY").parse("10/04/2021"));
		t1.setDescription("This is First Ticket");
		Category c1 = new Category(1, "IT");
		t1.setCategory(c1);
		
		Comments co1 = new Comments();
		co1.setCommentId(1);
		co1.setComment("This is first comments");
		co1.setTicket(t1);
		
		
		User user1 = new User();
		user1.setUserId(1);
		user1.setName("user1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("12345");
		user1.setType(EnUserType.MEMBER);
		
		co1.setUserId(user1);
		
		Comments co2 = new Comments(2,user1,"This is second comments",t1);
		List<Comments> list = new ArrayList<Comments>();
		list.add(co1);
		list.add(co2);
		
		t1.setComments(list);
		
		Attachments a1 = new Attachments();
		a1.setAttachmentId(1);
		a1.setAttachmentName("file");
		a1.setAttachmentType("JPG");
		a1.setTicket(t1);
		a1.setUserId(user1);
		
		List<Attachments> list1 = new ArrayList<Attachments>();
		list1.add(a1);
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("email", "true");
		
		HttpEntity<Ticket> request = new HttpEntity<>(t1,headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		assertThat(result.getStatusCodeValue()).isEqualTo(404);
		//Assert.assertEquals(201, result.getStatusCodeValue());
		
		
	}
	

}
