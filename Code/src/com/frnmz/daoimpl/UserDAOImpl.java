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
import com.frnmz.model.Record;
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
	public List<UserInfo> getAllWhoHaveNotPaid(String emailId) {
		List<UserInfo> activeUserList = mongoOperations.find(new Query(Criteria.where("enabled").is(true)), UserInfo.class);
		List<Record> allRecords = mongoOperations.find(Query.query(Criteria.where("adminEmail").is(emailId)) ,Record.class);
		
		for(Record record : allRecords){
			UserInfo uiTemp = new UserInfo(record.getMemberEmail());
			if(uiTemp.getEmailId() != null && activeUserList.contains(uiTemp)){
				activeUserList.remove(activeUserList.indexOf(uiTemp));
			}
		}
		
		return activeUserList;
	}

	@Override
	public boolean transferAuthority(String newAdminEmailId, String oldAdminEmailId, String newPassword) {
		try {
			mongoOperations.findAndModify(Query.query(Criteria.where("emailId").is(oldAdminEmailId)),
					new Update().set("password", null).set("adminAccess", false), UserInfo.class);
			
			mongoOperations.findAndModify(Query.query(Criteria.where("emailId").is(newAdminEmailId)),
					new Update().set("password", newPassword).set("adminAccess", true), UserInfo.class);
			
			return true;
		} catch (Exception e) {}
		return false;
	}
}