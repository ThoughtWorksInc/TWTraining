package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.specification.CustomerWithNameSpecification;
import com.thoughtworks.videorental.domain.specification.CustomersOrderedByNameComparator;

public class LoginAction extends ActionSupport {

	private final CustomerRepository customerRepository;
	private String customerName;
	private Customer loggedInCustomer;

	public LoginAction(final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void setCustomerName(final String customer) {
		this.customerName = customer;
	}

	public Set<Customer> getCustomers() {
		return customerRepository.selectAll(new CustomersOrderedByNameComparator());
	}

	public Customer getLoggedInCustomer() {
		return loggedInCustomer;
	}

	@Override
	public String execute() throws Exception {
		if (customerName == null) {
			return LOGIN;
		}

		loggedInCustomer = customerRepository.selectUnique(new CustomerWithNameSpecification(customerName));
		if (loggedInCustomer == null) {
			return LOGIN;
		}

		return SUCCESS;
	}
}
