package com.thoughtworks.videorental.domain.specification;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Transaction;

public class TransactionsOrderedByDateTimeComparator implements OrderComparator<Transaction> {

	@Override
	public int compare(final Transaction transaction1, final Transaction transaction2) {
		return transaction1.getDateTime().compareTo(transaction2.getDateTime());
	}

	@Override
	public void populateCriteria(final Criteria criteria) {
		throw new UnsupportedOperationException("not implemented");
	}

}
