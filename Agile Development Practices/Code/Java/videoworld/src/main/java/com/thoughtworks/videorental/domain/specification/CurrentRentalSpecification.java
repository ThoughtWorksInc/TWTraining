package com.thoughtworks.videorental.domain.specification;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Rental;

public class CurrentRentalSpecification implements Specification<Rental> {

	@Override
	public boolean isSatisfiedBy(final Rental rental) {
		return !rental.getPeriod().getEndDate().isBeforeNow();
	}

	@Override
	public void populateCriteria(final Criteria criteria) {
		throw new UnsupportedOperationException("not implemented");
	}

}
