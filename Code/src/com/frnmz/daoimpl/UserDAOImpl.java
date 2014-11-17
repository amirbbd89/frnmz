package com.frnmz.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.frnmz.dao.UserDAO;
import com.frnmz.model.UserInfo;

@Service
public class UserDAOImpl implements UserDAO{
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public UserInfo getUserInfoByEmail(String email) {
		try {
			return mongoOperations.findOne(Query.query(Criteria.where("emailId").is(email)), UserInfo.class);
		} catch (Exception e) {}

		return null;
	}

	@Override
	public UserInfo getUserInfo(String emailId, String password) {
		try {
			return mongoOperations.findOne(Query.query(Criteria.where("emailId").is(emailId).andOperator(Criteria.where("password").is(password))), UserInfo.class);
		} catch (Exception e) {}

		return null;
	}

	@Override
	public List<UserInfo> getAllActiveUsers() {
		try {
			return mongoOperations.find(Query.query(Criteria.where("enabled").is(true)), UserInfo.class);
		} catch (Exception e) {}

		return new ArrayList<UserInfo>();
	}

	@Override
	public List<UserInfo> getAllUsers() {
		try {
			return mongoOperations.findAll(UserInfo.class);
		} catch (Exception e) {}

		return new ArrayList<UserInfo>();
	}

	@Override
	public boolean transferAuthority(String newAdminEmailId, String oldAdminEmailId) {
		try {
			mongoOperations.findAndModify(Query.query(Criteria.where("emailId").is(oldAdminEmailId)),
					new Update().set("adminAccess", false), UserInfo.class);
			
			mongoOperations.findAndModify(Query.query(Criteria.where("emailId").is(newAdminEmailId)),
					new Update().set("adminAccess", true), UserInfo.class);
			
			return true;
		} catch (Exception e) {}
		return false;
	}

	@Override
	public boolean updatePassword(String emailId, String password) {
		try {
			mongoOperations.findAndModify(Query.query(Criteria.where("emailId").is(emailId)),
					new Update().set("password", password), UserInfo.class);			
			return true;
		} catch (Exception e) {}
		return false;
	}

	@Override
	public boolean deleteUser(String emailId) {
		try {
			mongoOperations.findAndRemove(Query.query(Criteria.where("emailId").is(emailId)), UserInfo.class);			
			return true;
		} catch (Exception e) {}
		return false;
	}
}