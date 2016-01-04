package com.thoughtworks.videorental.domain;

import javax.persistence.Entity;

@Entity
public class Movie {
	public static final Price CHILDRENS = new ChildrensPrice();
	public static final Price REGULAR = new RegularPrice();
	public static final Price NEW_RELEASE = new NewReleasePrice();

	private String title;
	private Price price;

	public Movie(String title, Price price) {
		this.title = title;
		setPrice(price);
	}

	public String getTitle() {
		return title;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}
}
