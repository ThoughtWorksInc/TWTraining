package com.thoughtworks.videorental.action;

import java.util.Collection;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.repository.RentalRepository;
import com.thoughtworks.videorental.interceptor.CustomerAware;

public class ViewCurrentRentalsAction extends ActionSupport implements CustomerAware {

	private final RentalRepository rentalRepository;

	private Collection<Rental> rentals;
	private Customer customer;

	public ViewCurrentRentalsAction(final RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}

	@Override
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Collection<Rental> getRentals() {
		return rentals;
	}

	@Override
	public String execute() throws Exception {
		rentals = rentalRepository.currentRentalsFor(customer);

		return SUCCESS;
	}

}
