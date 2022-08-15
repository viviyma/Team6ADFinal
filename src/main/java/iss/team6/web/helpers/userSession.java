package iss.team6.web.helpers;

import iss.team6.web.models.LoginDTO;

public class userSession {
	
	private LoginDTO user;

	public LoginDTO getUser() {
		return user;
	}

	public void setUser(LoginDTO user) {
		this.user = user;
	}

	public userSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public userSession(LoginDTO user) {
		super();
		this.user = user;
	}

}
