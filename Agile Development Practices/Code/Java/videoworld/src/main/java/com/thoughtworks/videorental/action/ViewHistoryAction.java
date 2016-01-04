package com.thoughtworks.videorental.action;

import java.util.Collection;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.interceptor.CustomerAware;

public class ViewHistoryAction extends ActionSupport implements CustomerAware {

	private final TransactionRepository transactionRepository;
	private Collection<Transaction> transactions;
	private Customer customer;

	public ViewHistoryAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Collection<Transaction> getTransactions() {
		return transactions;
	}

	@Override
	public String execute() throws Exception {
		transactions = transactionRepository.transactionsBy(customer);
		return SUCCESS;
	}
}
