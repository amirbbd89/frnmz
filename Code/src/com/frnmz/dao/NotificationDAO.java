package com.frnmz.dao;

import java.util.List;

import com.frnmz.model.Notification;

public interface NotificationDAO {
	Notification getNotification(String id);
	Notification getNotificationByDate(String date);
	List<Notification> getAllNotifications();
}