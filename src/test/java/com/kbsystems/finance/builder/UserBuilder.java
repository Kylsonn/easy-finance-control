package com.kbsystems.finance.builder;

import com.kbsystems.finance.domain.User;

public class UserBuilder {
	private User user;
	public UserBuilder() {
		user = new User();
	}
	public UserBuilder setPassword(String password) {
		user.setPassword(password);
		return this;
	}
	public UserBuilder setUsername(String username) {
		user.setUsername(username);
		return this;
	}
	public UserBuilder setId(String id) {
		user.setId(id);
		return this;
	}
	public User build() {
		return user;
	}
}
