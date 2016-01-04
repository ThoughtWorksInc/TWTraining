package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;
import java.util.Set;

import com.thoughtworks.ddd.repository.NonUniqueObjectSelectedException;
import com.thoughtworks.ddd.repository.NullObjectAddedException;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Movie;

public interface MovieRepository {
	void add(Movie entity) throws NullObjectAddedException;

	void add(Collection<Movie> entities) throws NullObjectAddedException;

	Set<Movie> selectAll();

	Set<Movie> selectAll(OrderComparator<Movie> comparator);

	Set<Movie> selectSatisfying(Specification<Movie> specification);

	Set<Movie> selectSatisfying(Specification<Movie> specification, OrderComparator<Movie> comparator);

	Movie selectUnique(Specification<Movie> specification) throws NonUniqueObjectSelectedException;

	Set<Movie> withTitles(String... titles);
}