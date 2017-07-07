package com.ringletter.bean;



public class Album implements java.io.Serializable {

	// Fields
	
	

	private Integer albumId;
	private Integer userId;
//	private String albumName;
	private String imagePath;

	// Constructors

	/** default constructor */
	public Album() {
	}

	/** minimal constructor */
	public Album(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public Album(Integer userId,  String imagePath) {
		this.userId = userId;
//		this.albumName = albumName;
		this.imagePath = imagePath;
	}

	// Property accessors

	public Integer getAlbumId() {
		return this.albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
//
//	public String getAlbumName() {
//		return this.albumName;
//	}
//
//	public void setAlbumName(String albumName) {
//		this.albumName = albumName;
//	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}