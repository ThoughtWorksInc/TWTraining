package com.thoughtworks.videorental.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import com.thoughtworks.ddd.repository.SetBasedRepository;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class SetBasedCustomerRepository extends SetBasedRepository<Customer> implements CustomerRepository {

	public SetBasedCustomerRepository(final Collection<Customer> entities) {
		super(entities);
	}

	@Override
	public Set<Customer> selectAll(OrderComparator<Customer> comparator) {
		return selectAll((Comparator<Customer>) comparator);
	}

	@Override
	public Set<Customer> selectSatisfying(Specification<Customer> specification, OrderComparator<Customer> comparator) {
		return selectSatisfying(specification, (Comparator<Customer>) comparator);
	}

}
