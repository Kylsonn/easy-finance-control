package com.kbsystems.finance.service;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kbsystems.finance.builder.UserBuilder;
import com.kbsystems.finance.domain.User;
import com.kbsystems.finance.repository.UserRepository;
import com.kbsystems.finance.service.exception.UserAlreadyExistsException;

public class UserServiceTest {
	
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userRepository);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void create_new_user_deny_duplicate() {
		UserBuilder userBuilder = new UserBuilder();
		userBuilder.setPassword("a").setUsername("Kylsonn");
		
		when(userRepository.findByUsername("Kylsonn")).thenReturn(Optional.of(userBuilder.build()));
		userService.create(userBuilder.build());
	}
	
	@Test
	public void create_new_user() {
		UserBuilder userBuilder = new UserBuilder();
		userBuilder.setPassword("a").setUsername("Kylsonn");
		User user = userBuilder.build();
		
		UserBuilder userBuilderSaved = new UserBuilder();
		userBuilderSaved.setPassword("a").setUsername("Kylsonn").setId("abcd1234");
		User userSaved = userBuilderSaved.build();
		
		when(userRepository.insert(user)).thenReturn(userSaved);
		
		User userCreated = userService.create(user);
		
		assertThat(userCreated.getId(), equalTo(userSaved.getId()));
	}
	
	

}
