package com.frnmz.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="notification")
public class Notification{
	@Id
	private String id;
	private String date;
	private List<String> likes;
	private String subject;
	private String body;
	
	public Notification() {}	
	public Notification(String subject, String body) {
		super();
		this.id = String.valueOf(System.currentTimeMillis());
		this.date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		this.likes = new ArrayList<String>();
		this.subject = subject;
		this.body = body;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getLikes() {
		return likes;
	}
	public void setLikes(List<String> likes) {
		this.likes = likes;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}