package com.thoughtworks.videorental.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import com.thoughtworks.ddd.repository.SetBasedRepository;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.specification.MovieWithTitleSpecification;

public class SetBasedMovieRepository extends SetBasedRepository<Movie> implements MovieRepository {

	public SetBasedMovieRepository() {
		super();
	}

	public SetBasedMovieRepository(final Collection<Movie> entities) {
		super(entities);
	}

	@Override
	public Set<Movie> selectAll(OrderComparator<Movie> comparator) {
		return selectAll((Comparator<Movie>) comparator);
	}

	@Override
	public Set<Movie> selectSatisfying(final Specification<Movie> specification, final OrderComparator<Movie> comparator) {
		return selectSatisfying(specification, (Comparator<Movie>) comparator);
	}

	@Override
	public Set<Movie> withTitles(final String... titles) {
		return selectSatisfying(new MovieWithTitleSpecification(titles));
	}
}
