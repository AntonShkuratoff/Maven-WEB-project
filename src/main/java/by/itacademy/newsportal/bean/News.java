package by.itacademy.newsportal.bean;

import java.io.Serializable;
import java.util.Objects;

public class News implements Serializable {
	private static final long serialVersionUID = 7150795115824349935L;

	private int id;
	private int userId;
	private String title;
	private String brief;
	private String content;
	private String date;

	public News(String title, String brief) {
		super();
		this.title = title;
		this.brief = brief;
	}

	public News(String title, String brief, String content) {
		super();
		this.title = title;
		this.brief = brief;
		this.content = content;
	}

	public News(int userId, String title, String brief, String content) {
		super();
		this.userId = userId;
		this.title = title;
		this.brief = brief;
		this.content = content;
	}

	public News(int id, String title, String brief, String content, String date) {
		super();
		this.id = id;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.date = date;
	}

	public News(int id, int userId, String title, String brief, String content) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.brief = brief;
		this.content = content;
	}

	public News(int id, int userId, String title, String brief, String content, String date) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.date = date;
	}

	public News() {
		super();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brief, content, date, id, title, userId);
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
		News other = (News) obj;
		return Objects.equals(brief, other.brief) && Objects.equals(content, other.content)
				&& Objects.equals(date, other.date) && id == other.id && Objects.equals(title, other.title)
				&& userId == other.userId;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[id=" + id + ", userId=" + userId + ", title=" + title + ", brief=" + brief
				+ ", content=" + content + ", date=" + date + "]";
	}
}
