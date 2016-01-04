package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Test;

public class ChildrensPriceTest {
	private static final Price CHILDRENS_PRICE = new ChildrensPrice();

	@Test
	public void shouldCalculateCorrectChargeForChildrensMovie() throws Exception {
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(1));
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(2));
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(3));
		assertEquals(3.0, CHILDRENS_PRICE.getCharge(4));
		assertEquals(4.5, CHILDRENS_PRICE.getCharge(5));
	}
}
