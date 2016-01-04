package com.thoughtworks.videorental.acceptance;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class Login {

	private Browser browser;

	public Login(Browser browser) {
		this.browser = browser;
	}

	public void loginAs(String userName) throws Exception {
		browser.navigateTo("http://localhost:8081/login");
		browser.select("customerName").choose(userName);
		browser.submit("login").click();
	}

}
