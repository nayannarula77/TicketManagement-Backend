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
import com.example.TicketManagementSystem.api.dao.models.Category;
import com.example.TicketManagementSystem.api.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	
	@InjectMocks
	CategoryController categoryController;
	
	@Mock
	CategoryRepository categoryRepository;

	@Test
	public void testFindAll() {
		
		Category category1 = new Category(1,"IT");
		Category category2 = new Category(2,"Sales");
		Category category3 = new Category();
		category3.setCategoryId(3);
		category3.setName("Marketing");
		
		List<Category> list1= new ArrayList<Category>();
		list1.add(category1);
		list1.add(category2);
		list1.add(category3);
		
		when(categoryRepository.findAll()).thenReturn(list1);
		
		List<Category> list2 = categoryController.getCategory();
		
		assertThat(list2.size()).isEqualTo(3);
		assertThat(list2.get(0).getName()).isEqualTo(category1.getName());
		assertThat(list2.get(1).getName()).isEqualTo(category2.getName());
		assertThat(list2.get(0).getCategoryId()).isEqualTo(category1.getCategoryId());
		assertThat(list2.get(1).getCategoryId()).isEqualTo(category2.getCategoryId());
	}
	
	@Test
	public void testAddCategory() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        when(categoryRepository.save(any(Category.class))).thenReturn(null);
        Category category1 = new Category(1,"IT");
        ResponseEntity<Category> responseEntity = categoryController.createCategory(category1);
        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
}
