package com.thoughtworks.videorental.action;

import java.util.LinkedHashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.RentalRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.interceptor.CustomerAware;

public class RentMoviesAction extends ActionSupport implements CustomerAware {

	private final MovieRepository movieRepository;
	private final RentalRepository rentalRepository;
	private final TransactionRepository transactionRepository;

	private Customer customer;
	private String statement;
	private String[] movieNames;
	private int rentalDuration;

	public RentMoviesAction(final MovieRepository movieRepository, final RentalRepository rentalRepository,
			final TransactionRepository transactionRepository) {
		this.movieRepository = movieRepository;
		this.rentalRepository = rentalRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void setMovieNames(final String[] movieNames) {
		this.movieNames = movieNames;
	}

	public void setRentalDuration(final int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public String getStatement() {
		return statement;
	}

	@Override
	public String execute() throws Exception {
		final Set<Movie> movies = movieRepository.withTitles(movieNames);
		final Period rentalPeriod = Period.of(LocalDate.today(), Duration.ofDays(rentalDuration));

		final Set<Rental> rentals = new LinkedHashSet<Rental>();
		for (final Movie movie : movies) {
			final Rental rental = new Rental(customer, movie, rentalPeriod);
			rentals.add(rental);
		}

		rentalRepository.add(rentals);
		final Transaction transaction = new Transaction(LocalDateTime.now(), customer, rentals);
		transactionRepository.add(transaction);

		statement = customer.statement(transaction.getRentals());
		return SUCCESS;
	}

}
