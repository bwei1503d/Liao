package com.ringletter.bean;

/**
 * @action���ѹ�ϵʵ����
 */

public class Relationship implements java.io.Serializable {

	// Fields

	private Integer relationshipId;
	private Integer userId;
	private Integer friendId;
	private String groupName;

	// Constructors

	/** default constructor */
	public Relationship() {
	}

	/** minimal constructor */
	public Relationship(Integer relationshipId) {
		this.relationshipId = relationshipId;
	}

	/** full constructor */
	public Relationship(Integer relationshipId, Integer userId,
			Integer friendId, String groupName) {
		this.relationshipId = relationshipId;
		this.userId = userId;
		this.friendId = friendId;
		this.groupName = groupName;
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

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}