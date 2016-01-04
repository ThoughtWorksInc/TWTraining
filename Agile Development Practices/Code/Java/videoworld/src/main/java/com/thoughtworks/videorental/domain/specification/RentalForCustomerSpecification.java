package com.thoughtworks.videorental.domain.specification;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;

public class RentalForCustomerSpecification implements Specification<Rental> {

	private final Customer customer;

	public RentalForCustomerSpecification(final Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean isSatisfiedBy(final Rental rental) {
		return customer.equals(rental.getCustomer());
	}

	@Override
	public void populateCriteria(final Criteria criteria) {
		throw new UnsupportedOperationException("not implemented");
	}

}
