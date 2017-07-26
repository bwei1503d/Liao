package com.ringletter.service;

import java.util.List;

import com.ringletter.bean.Album;
import com.ringletter.bean.Chat;
import com.ringletter.bean.DynamicInfor;
import com.ringletter.bean.Relationship;
import com.ringletter.bean.User;

public interface UserService {

	Boolean selectByPhone(User user);// 验证手机号

	boolean register(User user);// 注册用户

	void uploadHeadPortrait(User user);// 上传头像

	User login(User user);// 登录

	void uploadImageToAlbum(Album album);// 添加图片到相册

	User updatePassword(String newPassword);// 修改密码 传入原本的对象，以及新的密码

	User updateUser(User user);// 修改用户基本信息

	List<User> selectAllUser(long currenttimer);// 查询出来所有的注册过的用户并且分页

	List<User> selectAllUserAndFriend(User user, long timer);// 查询出来用户的所有好友

	boolean checkAddUser(Relationship relationship);// 验证好友是否存在

	void addUserrelationship(Relationship r);// 添加用户好友信息

	String insertChat(Chat chat);// 写入聊天信息

	User selectUserById(User user,String sid);// 通过iD查询出需要加的好友的详细信息 sid 自己的id

	List<Chat> selectChat(Chat chat, int pageIndex, int pageSize);// 查看聊天记录 并分页

	List<Album> selectUserAlbum(Album album);// 查询用户相册
	
	List<DynamicInfor> selectDynamic(String time); //查询动态
}
