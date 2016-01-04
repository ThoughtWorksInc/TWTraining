package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Test;

public class MovieTest {
	
	private static final Movie REGULAR_MOVIE = new Movie("Regular", Movie.REGULAR);
	private static final Movie NEW_RELEASE_MOVIE = new Movie("NewRelease", Movie.NEW_RELEASE);
	private static final Movie CHILDRENS_MOVIE = new Movie("Childrens", Movie.CHILDRENS);
	
	@Test
	public void shouldCalculateCorrentFrequentRenterPointsForNonNewReleaseMovie() throws Exception {
    	assertEquals(1, REGULAR_MOVIE.getPrice().getFrequentRenterPoints(1));
    	assertEquals(1, REGULAR_MOVIE.getPrice().getFrequentRenterPoints(4));
    	assertEquals(1, CHILDRENS_MOVIE.getPrice().getFrequentRenterPoints(1));
    	assertEquals(1, CHILDRENS_MOVIE.getPrice().getFrequentRenterPoints(4));
	}
	
	@Test
	public void shouldCalculateCorrentFrequentRenterPointsForNewReleaseMovie() throws Exception {
    	assertEquals(1, NEW_RELEASE_MOVIE.getPrice().getFrequentRenterPoints(1));
    	assertEquals(2, NEW_RELEASE_MOVIE.getPrice().getFrequentRenterPoints(2));
    	assertEquals(2, NEW_RELEASE_MOVIE.getPrice().getFrequentRenterPoints(3));
	}
}
