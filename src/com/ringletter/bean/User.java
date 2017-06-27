package com.ringletter.bean;

import java.io.File;
import java.util.List;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String nickname;
	private String password;
	private String gender;
	private String area;
	private String phone;
	private String introduce;
	private String imagePath;
	private Long lasttime;
	private Long createtime;
	
	private String yxpassword ;
	
	private double lat;
	private double lng;

	private File file;
	private String fileFileName;
	private String fileContentType;
	
	//保存用户相册
	private List<Album> listAlbum ;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String nickname, String password, String phone) {
		this.nickname = nickname;
		this.password = password;
		this.phone = phone;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getLasttime() {
		return lasttime;
	}

	public void setLasttime(Long lasttime) {
		this.lasttime = lasttime;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	
	
	public String getYxpassword() {
		return yxpassword;
	}

	public void setYxpassword(String yxpassword) {
		this.yxpassword = yxpassword;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
	
	

	public List<Album> getListAlbum() {
		return listAlbum;
	}

	public void setListAlbum(List<Album> listAlbum) {
		this.listAlbum = listAlbum;
	}

	public User(Integer userId, String nickname, String password,
			String gender, String area, String phone, String introduce,
			String imagePath, Long lasttime, Long createtime, File file,
			String fileFileName, String fileContentType,double lat,double lng,String yxpassword) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.password = password;
		this.gender = gender;
		this.area = area;
		this.phone = phone;
		this.introduce = introduce;
		this.imagePath = imagePath;
		this.lasttime = lasttime;
		this.createtime = createtime;
		this.file = file;
		this.fileFileName = fileFileName;
		this.fileContentType = fileContentType;
		this.lat = lat;
		this.lng = lng;
		this.yxpassword = yxpassword ;
	}

	


}