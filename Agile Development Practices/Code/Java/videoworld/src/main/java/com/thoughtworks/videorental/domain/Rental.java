package com.thoughtworks.videorental.domain;

import com.thoughtworks.datetime.Period;

public class Rental {
	private final Movie movie;
	private final Customer customer;
	private final Period period;

	public Rental(Customer customer, Movie movie, Period period) {
		this.movie = movie;
		this.customer = customer;
		this.period = period;
	}

	public Movie getMovie() {
		return movie;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Period getPeriod() {
		return period;
	}
}