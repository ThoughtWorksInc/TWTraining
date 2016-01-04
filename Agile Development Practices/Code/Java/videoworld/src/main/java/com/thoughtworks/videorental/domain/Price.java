package com.thoughtworks.videorental.domain;

public interface Price {
	double getCharge(int daysRented);

	int getFrequentRenterPoints(int daysRented);
}
