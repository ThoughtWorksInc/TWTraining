package com.thoughtworks.videorental.domain.specification;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Customer;

public class CustomersOrderedByNameComparator implements OrderComparator<Customer> {

	@Override
	public int compare(final Customer customer1, final Customer customer2) {
		return (customer1 == customer2) ? 0 : customer1.getName().compareTo(customer2.getName());
	}

	@Override
	public void populateCriteria(Criteria arg0) {
		throw new UnsupportedOperationException("not implemented");
	}

}
