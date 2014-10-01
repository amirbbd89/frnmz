package com.frnmz.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.frnmz.dao.NotificationDAO;
import com.frnmz.model.Notification;

@Service
public class NotificationDAOImpl implements NotificationDAO {
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public Notification getNotification(String id) {
		try {
			return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)) ,Notification.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notification> getAllNotifications() {
		try {
			return mongoOperations.findAll(Notification.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Notification>();
	}

	@Override
	public Notification getNotificationByDate(String date) {
		try {
			return mongoOperations.findOne(Query.query(Criteria.where("date").is(date)) ,Notification.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}