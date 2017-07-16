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
	private String age;
	private String introduce;
	private String imagePath;
	private Long lasttime;
	private Long createtime;
	private Long relationtime;
	
	//  0 非好友  1好友
	private int relation;
	
	//
	private String sign ;
	//rsa 加密的后key
	private String secretkey ;
	
	//
	
	
	private String yxpassword ;
	
	private double lat;
	private double lng;

	private File file;
	//时间蹉  验签
	private String currenttimer ;
	
	private String type ;
	
	//图片的宽高
	private int picWidth;
	private int picHeight;

	
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

	
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	
	
	
	

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCurrenttimer() {
		return currenttimer;
	}

	public void setCurrenttimer(String currenttimer) {
		this.currenttimer = currenttimer;
	}
	
	
	
	

	public int getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(int picWidth) {
		this.picWidth = picWidth;
	}

	public int getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(int picHeight) {
		this.picHeight = picHeight;
	}

	public User(Integer userId, String nickname, String password,
			String gender, String area, String phone, String introduce,
			String imagePath, Long lasttime, Long createtime, File file,
			String fileFileName, String fileContentType,double lat,double lng,String yxpassword,String sign
			, String secretkey) {
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
		this.sign = sign ;
		this.secretkey = secretkey ;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", nickname=" + nickname + ", password=" + password + ", gender=" + gender
				+ ", area=" + area + ", phone=" + phone + ", introduce=" + introduce + ", imagePath=" + imagePath
				+ ", lasttime=" + lasttime + ", createtime=" + createtime + ", sign=" + sign + ", secretkey="
				+ secretkey + ", currenttimer=" + currenttimer + ", yxpassword=" + yxpassword + ", lat=" + lat
				+ ", lng=" + lng + ", file=" + file + ", fileFileName=" + fileFileName + ", fileContentType="
				+ fileContentType + ", listAlbum=" + listAlbum + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public Long getRelationtime() {
		return relationtime;
	}

	public void setRelationtime(Long relationtime) {
		this.relationtime = relationtime;
	}

	
	
	


}