package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Test;

public class RegularPriceTest {
	private static final RegularPrice REGULAR_PRICE = new RegularPrice();

	@Test
    public void shouldCalculateCorrectChargeForRegularMovie() throws Exception {
    	assertEquals(2.0, REGULAR_PRICE.getCharge(1));
    	assertEquals(2.0, REGULAR_PRICE.getCharge(2));
    	assertEquals(3.5, REGULAR_PRICE.getCharge(3));
    	assertEquals(5.0, REGULAR_PRICE.getCharge(4));
    }
}
