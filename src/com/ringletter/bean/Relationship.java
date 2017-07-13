package com.ringletter.bean;


public class Relationship implements java.io.Serializable {

	// Fields

	private Integer relationshipId;
	//自己
	private Integer userId;
	//对方
	private Integer friendId;
	private long timer ;
	// Constructors
	
	private User user ;
	

	/** default constructor */
	public Relationship() {
	}

	/** minimal constructor */
	public Relationship(Integer relationshipId) {
		this.relationshipId = relationshipId;
	}

	/** full constructor */
	public Relationship(Integer relationshipId, Integer userId,
			Integer friendId, long timer) {
		this.relationshipId = relationshipId;
		this.userId = userId;
		this.friendId = friendId;
		this.timer = timer;
	}

	// Property accessors

	public Integer getRelationshipId() {
		return this.relationshipId;
	}

	public void setRelationshipId(Integer relationshipId) {
		this.relationshipId = relationshipId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFriendId() {
		return this.friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	

}