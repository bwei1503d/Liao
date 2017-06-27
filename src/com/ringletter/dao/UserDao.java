package com.ringletter.dao;

import java.util.List;

import com.ringletter.bean.Album;
import com.ringletter.bean.Chat;
import com.ringletter.bean.Relationship;
import com.ringletter.bean.User;

public interface UserDao {
	List<User> selectByPhone(User user);// 验证手机号

	void register(User user);// 注册用户

	void uploadHeadPortrait(User user);// 上传头像

	User login(User user);// 登录

	void uploadImageToAlbum(Album album);// 添加图片到相册

	User updatePassword(String newPassword);// 修改密码 传入原本的对象，以及新的密码

	User updateUser(User user);// 修改用户基本信息

	public List<User> selectAllUser(int pageIndex, int pageSize);// 查询出来所有注册过的用户并分页

	List<User> selectAllUserAndFriend(User user, int pageIndex, int pageSize);// 查询出来用户的所有好友并分页

	boolean checkAddUser(Relationship relationship);// 验证好友是否存在

	void addUserrelationship(Relationship relationship);// 添加用户好友信息

	void insertChat(Chat chat);// 写入聊天信息

	User selectUserById(User user);// 通过iD查询出需要加的好友的详细信息

	List<Chat> selectChat(Chat chat, int pageIndex, int pageSize);// 查看聊天记录 并分页

	List<Album> selectUserAlbum(Album album);// 查询用户相册
}
