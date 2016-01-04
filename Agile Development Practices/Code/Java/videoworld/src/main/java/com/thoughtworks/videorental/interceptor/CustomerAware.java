package com.thoughtworks.videorental.interceptor;

import com.thoughtworks.videorental.domain.Customer;

public interface CustomerAware {
	void setCustomer(Customer customer);
}
