package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;

public class ViewHomeAction extends ActionSupport {

	private final MovieRepository movieRepository;

	public ViewHomeAction(final MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Set<Movie> getMovies() {
		return movieRepository.selectAll();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
