package com.ringletter.service.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringletter.bean.Album;
import com.ringletter.bean.Chat;
import com.ringletter.bean.DynamicInfor;
import com.ringletter.bean.Relationship;
import com.ringletter.bean.User;
import com.ringletter.dao.UserDao;
import com.ringletter.service.UserService;

@Service
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public Boolean selectByPhone(User user) {
		List<User> result = userDao.selectByPhone(user);
		if (result != null && result.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean register(User user) {
		boolean result =  userDao.register(user);
		com.ringletter.utils.Util.addUser(user.getUserId()+"", user.getYxpassword());
		return result;
	}

	@Override
	public void uploadHeadPortrait(User user) {
		userDao.uploadHeadPortrait(user);
	}

	@Override
	public User login(User user) {
		User loginUser = userDao.login(user);
		if (loginUser != null) {
			return loginUser;
		}
		return null;
	}

	@Override
	public void uploadImageToAlbum(Album album) {
		userDao.uploadImageToAlbum(album);
	}

	@Override
	public List<User> selectAllUserAndFriend(User user, long currenttimer
			) {
		List<User> selectAllUser = userDao.selectAllUserAndFriend(user,
				currenttimer);
		return selectAllUser;
	}

	@Override
	public User updatePassword(String newPassword) {
		User user = userDao.updatePassword(newPassword);
		User u = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		if(user == null){
			return null;
		}else{
			if (u != null) {
				com.ringletter.utils.Util.updatePass(u.getPhone(), newPassword);
			} else {
				
			}
		}

		return user;
	}

	@Override
	public User updateUser(User user) {
		User user2 = userDao.updateUser(user);
		return user2;
	}

	@Override
	public String insertChat(Chat chat) {
		userDao.insertChat(chat);
//		Integer userId = chat.getUserId();
//		User u1 = new User();
//		u1.setUserId(userId);
//		User fromUser = userDao.selectUserById(u1);
//		Integer toUserId = chat.getTouserId();
//		User u2 = new User();
//		u2.setUserId(toUserId);
//		User selectUserById = userDao.selectUserById(u2);
//		ArrayList<String> list = new ArrayList<String>();
//		list.add(selectUserById.getPhone());
//		String sendText = com.ringletter.utils.Util.sendText(
//				fromUser.getPhone(), list, chat.getMessage());
//		return sendText;
		return "" ;
	}

	@Override
	public List<Chat> selectChat(Chat chat, int pageIndex, int pageSize) {
		List<Chat> selectChat = userDao.selectChat(chat, pageIndex, pageSize);
		return selectChat;
	}

	@Override
	public void addUserrelationship(Relationship relationship) {
		userDao.addUserrelationship(relationship);
	}

	@Override
	public User selectUserById(User user,String sid) {
		User selectUserById = userDao.selectUserById(user,sid);
		return selectUserById;
	}


	@Override
	public List<User> selectAllUser(long currenttimer) {
		return userDao.selectAllUser(currenttimer);
	}

	@Override
	public boolean checkAddUser(Relationship relationship) {
		boolean checkAddUser = userDao.checkAddUser(relationship);
		return checkAddUser;
	}
	
	@Override
	public List<Album> selectUserAlbum(Album album) {
		List<Album> selectUserAlbum = userDao.selectUserAlbum(album);
		return selectUserAlbum;
	}
	
	@Override
	public List<DynamicInfor> selectDynamic(String time) {
		// TODO Auto-generated method stub
		return userDao.selectDynamic(time);
	}

}
