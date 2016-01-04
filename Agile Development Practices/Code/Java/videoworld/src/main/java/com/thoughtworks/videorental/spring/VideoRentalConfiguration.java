package com.thoughtworks.videorental.spring;

import java.util.Arrays;

import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;

import com.thoughtworks.videorental.action.LoginAction;
import com.thoughtworks.videorental.action.LogoutAction;
import com.thoughtworks.videorental.action.RentMoviesAction;
import com.thoughtworks.videorental.action.ViewHistoryAction;
import com.thoughtworks.videorental.action.ViewAdminAction;
import com.thoughtworks.videorental.action.ViewHomeAction;
import com.thoughtworks.videorental.action.ViewCurrentRentalsAction;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.RentalRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.interceptor.CustomerLoginInterceptor;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.repository.SetBasedRentalRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;

@Configuration
public class VideoRentalConfiguration {
	@Bean(scope = "prototype")
	public LoginAction loginAction() {
		return new LoginAction(customerRepository());
	}

	@Bean(scope = "prototype")
	public LogoutAction logoutAction() {
		return new LogoutAction();
	}

	@Bean(scope = "prototype")
	public ViewHomeAction viewHomeAction() {
		return new ViewHomeAction(movieRepository());
	}

	@Bean(scope = "prototype")
	public RentMoviesAction rentMoviesAction() {
		return new RentMoviesAction(movieRepository(), rentalRepository(), transactionRepository());
	}

	@Bean(scope = "prototype")
	public ViewHistoryAction viewHistoryAction() {
		return new ViewHistoryAction(transactionRepository());
	}

	@Bean(scope = "prototype")
	public ViewAdminAction viewAdminAction() {
		return new ViewAdminAction(customerRepository());
	}
	
	@Bean(scope = "prototype")
	public ViewCurrentRentalsAction viewCurrentRentalsAction() {
		return new ViewCurrentRentalsAction(rentalRepository());
	}

	@Bean(scope = "singleton")
	public MovieRepository movieRepository() {
		final Movie avatar = new Movie("Avatar", Movie.NEW_RELEASE);
		final Movie upInTheAir = new Movie("Up In The Air", Movie.REGULAR);
		final Movie findingNemo = new Movie("Finding Nemo", Movie.CHILDRENS);
		return new SetBasedMovieRepository(Arrays.asList(avatar, upInTheAir, findingNemo));
	}

	@Bean(scope = "singleton")
	public CustomerRepository customerRepository() {
		final Customer customer1 = new Customer("James Madison");
		final Customer customer2 = new Customer("Zackery Taylor");
		final Customer customer3 = new Customer("Benjamin Harrison");
		return new SetBasedCustomerRepository(Arrays.asList(customer1, customer2, customer3));
	}

	@Bean(scope = "singleton")
	public RentalRepository rentalRepository() {
		return new SetBasedRentalRepository();
	}

	@Bean(scope = "singleton")
	public TransactionRepository transactionRepository() {
		return new SetBasedTransactionRepository();
	}

	@Bean(scope = "singleton")
	public CustomerLoginInterceptor customerLoginInterceptor() {
		return new CustomerLoginInterceptor();
	}
}
