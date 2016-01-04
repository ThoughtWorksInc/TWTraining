package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class ViewAdminAction extends ActionSupport {

	private final CustomerRepository customerRepository;

	public ViewAdminAction(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Set<Customer> getUsers() {
		return customerRepository.selectAll();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
