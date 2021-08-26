package by.itacademy.newsportal.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = -6287422086647275010L;

	int id;
	private String login;
	private Role role;

	public User(int id, String login, Role role) {
		super();
		this.id = id;
		this.login = login;
		this.role = role;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return id == other.id && Objects.equals(login, other.login) && role == other.role;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[id=" + id + ", login=" + login + ", role=" + role + "]";
	}
}
