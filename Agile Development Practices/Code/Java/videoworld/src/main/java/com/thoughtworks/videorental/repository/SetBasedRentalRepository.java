package com.thoughtworks.videorental.repository;

import java.util.Comparator;
import java.util.Set;

import com.thoughtworks.ddd.repository.SetBasedRepository;
import com.thoughtworks.ddd.specification.AndSpecification;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.repository.RentalRepository;
import com.thoughtworks.videorental.domain.specification.CurrentRentalSpecification;
import com.thoughtworks.videorental.domain.specification.RentalForCustomerSpecification;

public class SetBasedRentalRepository extends SetBasedRepository<Rental> implements RentalRepository {

	public SetBasedRentalRepository() {
		super();
	}

	@Override
	public Set<Rental> selectAll(OrderComparator<Rental> comparator) {
		return selectAll((Comparator<Rental>) comparator);
	}

	@Override
	public Set<Rental> selectSatisfying(final Specification<Rental> specification,
			final OrderComparator<Rental> comparator) {
		return selectSatisfying(specification, (Comparator<Rental>) comparator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Rental> currentRentalsFor(final Customer customer) {
		return selectSatisfying(new AndSpecification<Rental>(new RentalForCustomerSpecification(customer),
				new CurrentRentalSpecification()));
	}
}
