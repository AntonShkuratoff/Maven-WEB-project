package by.itacademy.newsportal.bean;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationInfo implements Serializable {
	private static final long serialVersionUID = 8807471572510945626L;

	private String surname;
	private String name;
	private String patronymic;
	private String birthday;
	private String login;
	private String password;
	private String email;
	private Role role;

	public RegistrationInfo(String surname, String name, String patronymic, String birthday, String login,
			String password, String email) {
		super();
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.birthday = birthday;
		this.login = login;
		this.password = password;
		this.email = email;
	}

	public RegistrationInfo(String surname, String name, String patronymic, String birthday, String login,
			String email) {
		super();
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.birthday = birthday;
		this.login = login;
		this.email = email;
	}

	public RegistrationInfo() {
		super();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, email, login, name, password, patronymic, role, surname);
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
		RegistrationInfo other = (RegistrationInfo) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(patronymic, other.patronymic)
				&& role == other.role && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return getClass().getName() + "[surname=" + surname + ", name=" + name + ", patronymic=" + patronymic + ", birthday="
				+ birthday + ", login=" + login + ", password=" + password + ", email=" + email + ", role=" + role
				+ "]";
	}

	
}
