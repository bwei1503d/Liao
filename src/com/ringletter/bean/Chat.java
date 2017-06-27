package com.ringletter.bean;

import java.sql.Timestamp;


public class Chat implements java.io.Serializable {

	// Fields

	private Integer chatId;
	private Integer userId;
	private Integer touserId;
	private String messageType;
	private String filePath;
	private String message;
	private Long messageTime;

	// Constructors

	/** default constructor */
	public Chat() {
	}

	/** minimal constructor */
	public Chat(Integer userId, Integer touserId, String messageType,
			Long messageTime) {
		this.userId = userId;
		this.touserId = touserId;
		this.messageType = messageType;
		this.messageTime = messageTime;
	}

	/** full constructor */
	public Chat(Integer userId, Integer touserId, String messageType,
			String filePath, String message, Long messageTime) {
		this.userId = userId;
		this.touserId = touserId;
		this.messageType = messageType;
		this.filePath = filePath;
		this.message = message;
		this.messageTime = messageTime;
	}

	// Property accessors

	public Integer getChatId() {
		return this.chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTouserId() {
		return this.touserId;
	}

	public void setTouserId(Integer touserId) {
		this.touserId = touserId;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Long messageTime) {
		this.messageTime = messageTime;
	}

	

}