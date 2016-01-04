package com.thoughtworks.videorental.action;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;


public class ViewAdminActionTest {

	@Test
	public void shouldShowAllUsers() {
		Set<Customer> users = new LinkedHashSet<Customer>();
		users.add(new Customer("John Doe"));
		
		SetBasedCustomerRepository customerRepository = new SetBasedCustomerRepository(users);
		
		ViewAdminAction action = new ViewAdminAction(customerRepository);
		assertThat(action.getUsers(), is(users));
	}
	
}
