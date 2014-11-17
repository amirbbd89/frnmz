package com.frnmz.dao;

import java.util.List;

import com.frnmz.model.UserInfo;

public interface UserDAO {
	public UserInfo getUserInfoByEmail(String email);
	public UserInfo getUserInfo(String username, String password);
	public List<UserInfo> getAllActiveUsers();
	public List<UserInfo> getAllUsers();
	public boolean transferAuthority(String newAdminEmailId, String oldAdminEmailId);
	public boolean updatePassword(String emailId, String password);
	public boolean deleteUser(String emailId);
}