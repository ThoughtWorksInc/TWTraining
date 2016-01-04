package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;
import java.util.Set;

import com.thoughtworks.ddd.repository.NonUniqueObjectSelectedException;
import com.thoughtworks.ddd.repository.NullObjectAddedException;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Transaction;

public interface TransactionRepository {
	void add(Transaction entity) throws NullObjectAddedException;

	void add(Collection<Transaction> entities) throws NullObjectAddedException;

	Set<Transaction> selectAll();

	Set<Transaction> selectAll(OrderComparator<Transaction> comparator);

	Set<Transaction> selectSatisfying(Specification<Transaction> specification);

	Set<Transaction> selectSatisfying(Specification<Transaction> specification, OrderComparator<Transaction> comparator);

	Transaction selectUnique(Specification<Transaction> specification) throws NonUniqueObjectSelectedException;

	Collection<Transaction> transactionsBy(Customer customer);
}
