package com.heytusar.cbg.core.models;

import java.sql.SQLType;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "session_id", nullable = false)
	private String sessionId;
	
	@Column(name = "last_hit", nullable = true, columnDefinition="DATETIME")
	private Date lastHit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLastHit() {
		return lastHit;
	}

	public void setLastHit(Date lastHit) {
		this.lastHit = lastHit;
	}
		
}
