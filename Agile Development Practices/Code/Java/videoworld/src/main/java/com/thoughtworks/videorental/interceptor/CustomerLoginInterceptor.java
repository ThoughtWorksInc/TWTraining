package com.thoughtworks.videorental.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.thoughtworks.videorental.action.LoginAction;
import com.thoughtworks.videorental.domain.Customer;

public class CustomerLoginInterceptor extends AbstractInterceptor {

	private static final String USER_KEY = "user";

	@SuppressWarnings("unchecked")
	public String intercept(final ActionInvocation invocation) throws Exception {
		final Map session = invocation.getInvocationContext().getSession();
		final Customer customer = (Customer) session.get(USER_KEY);

		final Object action = invocation.getAction();
		if (customer == null && !(action instanceof LoginAction)) {
			return Action.LOGIN;
		}
		if (action instanceof CustomerAware) {
			((CustomerAware) action).setCustomer(customer);
		}
		final String result = invocation.invoke();
		if (action instanceof LoginAction) {
			session.put(USER_KEY, ((LoginAction) action).getLoggedInCustomer());
		}
		return result;
	}

}
