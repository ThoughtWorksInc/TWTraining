package com.thoughtworks.videorental.action;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class LoginActionTest {
	
	private CustomerRepository customerRepository;
	private LoginAction loginAction;
	
	@Before
	public void setUp() {
		customerRepository = mock(CustomerRepository.class);
		loginAction = new LoginAction(customerRepository);
	}

	@Test
	public void shouldReturnLoginWhenProvidedNoCustomerName() throws Exception {
		assertEquals(Action.LOGIN, loginAction.execute());
	}
}
