package com.app.todo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Todo {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String tag;
	private String priority;
	private String status;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "YYYY-MM-DD")
	private Date duedate;

	public Todo() {
		super();
	}

	public Todo(String name, String tag, String priority, String status, Date duedate) {
		super();
		this.name = name;
		this.tag = tag;
		this.priority = priority;
		this.status = status;
		this.duedate = duedate;
	}

	public Long getid() {
		return this.id;
	}

	public void setid(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	@Override
	public String toString() {
		return "TodoDTO [Id=" + id + ", name=" + name + ", tag=" + tag + ", priority=" + priority + ", status=" + status
				+ ", duedate=" + duedate + "]";
	}

}
