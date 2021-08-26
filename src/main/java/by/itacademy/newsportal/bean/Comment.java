package by.itacademy.newsportal.bean;

import java.io.Serializable;
import java.util.Objects;

public class Comment implements Serializable {
	private static final long serialVersionUID = -5776347616678126396L;

	private int id;
	private int userId;
	private int newsId;
	private String userLogin;
	private String content;
	private String date;

	public Comment(int id, int userId, int newsId, String userLogin, String content, String date) {
		super();
		this.id = id;
		this.userId = userId;
		this.newsId = newsId;
		this.userLogin = userLogin;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String comment) {
		this.content = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, date, id, newsId, userId, userLogin);
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
		Comment other = (Comment) obj;
		return Objects.equals(content, other.content) && Objects.equals(date, other.date) && id == other.id
				&& newsId == other.newsId && userId == other.userId && Objects.equals(userLogin, other.userLogin);
	}

	@Override
	public String toString() {
		return getClass().getName() + "[id=" + id + ", userId=" + userId + ", newsId=" + newsId + ", userLogin="
				+ userLogin + ", comment=" + content + ", date=" + date + "]";
	}
}
