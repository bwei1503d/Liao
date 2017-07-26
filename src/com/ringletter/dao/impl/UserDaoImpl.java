package com.ringletter.dao.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.ringletter.bean.Album;
import com.ringletter.bean.Chat;
import com.ringletter.bean.DynamicInfor;
import com.ringletter.bean.Relationship;
import com.ringletter.bean.User;
import com.ringletter.dao.UserDao;
import com.ringletter.utils.StringUtils;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Autowired
	public void DI(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
		
		
	}

	
	@Override
	public List<User> selectByPhone(User user) {
		if (user != null && user.getPhone() != null) {
			List find = getHibernateTemplate().find(
					"from User where phone = ?", user.getPhone());
			return find;
		}
		return null;
	}

	@Override
	public boolean register(User user) {

		Integer userid = (Integer) getHibernateTemplate().save(user);
		if(userid != null){
			return true;
		}
		return false;
	}

	@Override
	public void uploadHeadPortrait(User user) {
		User u = getHibernateTemplate().get(User.class, user.getUserId());
		u.setImagePath(user.getImagePath());
		u.setPicWidth(user.getPicWidth());
		u.setPicHeight(user.getPicHeight());
	}

	@Override
	public User login(User user) {
		List list = getHibernateTemplate().find(
				"from User where phone = ? and password = ?",
				new Object[] { user.getPhone(), user.getPassword() });
		if (list != null && list.size() > 0) {
			User tUser = (User) list.get(0);
			if (user.getLat() != 0.0) {
				tUser.setLat(user.getLat());
				tUser.setLng(user.getLng());
			}
			tUser.setLasttime(System.currentTimeMillis());
			getHibernateTemplate().update(tUser);
			return tUser;
		}
		return null;
	}

	@Override
	public void uploadImageToAlbum(Album album) {
		getHibernateTemplate().save(album);
	}

	@Override
	public User updatePassword(String newPassword) {
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		List list = getHibernateTemplate().find(
				"from User where password = ? and userId=?", newPassword,
				loginUser.getUserId());
		if (list != null && list.size() > 0) {
			return null;
		} else {
			loginUser.setPassword(newPassword);
			getHibernateTemplate().update(loginUser);
			User user2 = getHibernateTemplate().get(User.class,
					loginUser.getUserId());
			return user2;
		}
	}

	@Override
	public User updateUser(User user) {
		User user2 = getHibernateTemplate().get(User.class, user.getUserId());
		user2.setPhone(user.getPhone());
		user2.setGender(user.getGender());
		user2.setArea(user.getArea());
		user2.setNickname(user.getNickname());
		user2.setIntroduce(user.getIntroduce());
		return user2;
	}

	@Override
	public void addUserrelationship(Relationship relationship) {
		getHibernateTemplate().save(relationship);
		
		Relationship oRelationShip = new Relationship();
		
	}

	private List<Relationship> select(User user, long timer) {
		Query query = getSession()
				.createQuery("from Relationship where user_id= ' " + user.getUserId() +  " ' and timer <  " + timer + "  order by timer desc ")
				.setMaxResults(10)
				;
		
		
		List list = query.list();
		return list;
	}

	@Override
	public List<User> selectAllUserAndFriend(User user,long timer) {
		ArrayList<User> l = new ArrayList<User>();
		List<Relationship> list = select(user,timer);
		for (int i = 0; i < list.size(); i++) {
			User u = getHibernateTemplate().get(User.class,
					list.get(i).getFriendId());
			u.setRelationtime(list.get(i).getTimer());
			l.add(u);
		}
		return l;
	}

	@Override
	public User selectUserById(User user,String uid) {
		User u = getHibernateTemplate().get(User.class, user.getUserId());

		List find = getHibernateTemplate().find("from Album where userId=?",
				user.getUserId());
		u.setListAlbum(find);
		if(!StringUtils.isEmpty(uid)){
			String hql = "from Relationship where userId = "
					+ user.getUserId() + " and friendId = "
					+ uid;
			List list = getHibernateTemplate().find(hql);
			if(list.size() > 0){
				u.setRelation(1);
			}else{
				u.setRelation(0);
			}
		}else{
			u.setRelation(0);
		}
		return u;
	}

	@Override
	public void insertChat(Chat chat) {
		getHibernateTemplate().save(chat);
	}

	@Override
	public List<Chat> selectChat(Chat chat, int pageIndex, int pageSize) {

		String hql = "from Chat c where (c.userId = " + chat.getUserId()
				+ " and c.touserId =" + chat.getTouserId()
				+ " ) or (c.userId = " + chat.getTouserId()
				+ " and c.touserId =" + chat.getUserId()
				+ ")order by c.messageTime desc";
		int firstResult = pageIndex * pageSize;
		Query query = getSession().createQuery(hql).setFirstResult(firstResult)
				.setMaxResults(pageSize);
		List list = query.list();
		return list;
	}

	@Override
	public List<User> selectAllUser(long currenttimer) {
		String sqlString = "from User where lasttime < "+ currenttimer +"   order by lasttime desc";
		Query query = getSession().createQuery(sqlString).setMaxResults(20);
				
		List<User> list = query.list();
		return list;
	}

	@Override
	public boolean checkAddUser(Relationship relationship) {
		// 先判断 该getFriendId 存不存在， 然后在判断好友关系
		
		try{
			String usql = "from User where userId = " + relationship.getFriendId() ; 
			List ulist =  getHibernateTemplate().find(usql);
			if(ulist.size() == 0){
				return false ;
			}
			String hql = "from Relationship where userId = "
					+ relationship.getUserId() + " and friendId = "
					+ relationship.getFriendId();
			List list = getHibernateTemplate().find(hql);
			
			List<User> userlist = getHibernateTemplate().find(
					"from User where userId=?",
					relationship.getFriendId());
			
			relationship.setUser(userlist.get(0));
			if (list != null && list.size() > 0) {
				return true;
			}else{
				Relationship oRelationShip = new Relationship();
				oRelationShip.setUserId(relationship.getFriendId());
				oRelationShip.setFriendId(relationship.getUserId());
				oRelationShip.setTimer(relationship.getTimer());
				getHibernateTemplate().save(relationship);
				getHibernateTemplate().save(oRelationShip);
				return true ;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return false ;
		}
		
	}

	@Override
	public List<Album> selectUserAlbum(Album album) {
		List find = getHibernateTemplate().find("from Album where userId=?",
				album.getUserId());
		if (find != null && find.size() > 0) {
			return find;
		}
		return null;
	}
	
	@Override
	public List<DynamicInfor> selectDynamic(String time) {
		// TODO Auto-generated method stub
		
		try{
			Query query = getSession()
					.createQuery("from DynamicInfor where dynamicTime>= ' " + time +  " '  ")
					.setMaxResults(10)
					;
			
			return query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null ;
		}
		
		
		
	}

}
