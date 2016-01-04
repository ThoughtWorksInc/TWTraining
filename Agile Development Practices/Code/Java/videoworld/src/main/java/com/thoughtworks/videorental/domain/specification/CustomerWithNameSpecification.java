package com.thoughtworks.videorental.domain.specification;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;

public class CustomerWithNameSpecification implements Specification<Customer> {

	private final String customerName;

	public CustomerWithNameSpecification(final String customerName) {
		this.customerName = customerName;
	}

	@Override
	public boolean isSatisfiedBy(final Customer customer) {
		return customer.getName().equals(customerName);
	}

	@Override
	public void populateCriteria(final Criteria criteria) {
		throw new UnsupportedOperationException("not implemented");
	}

}
