package com.frnmz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.frnmz.dao.UserDAO;
import com.frnmz.model.UserInfo;

@Service(value="mongoAuthService")
public class MongoAuthService implements UserDetailsService {
	@Autowired
	UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException, DataAccessException {
		try {
			UserInfo userInfo = userDao.getUserInfoByEmail(emailId);
			
			if(null != userInfo){
				String[] roles = new String[1];
				
				if(userInfo.getAdminAccess()){
					roles[0] = "AD";
				} else {
					roles[0] = "MM";
				}
				
				return new User(emailId, userInfo.getPassword(), userInfo.getEnabled(), true, true, true, AuthorityUtils.createAuthorityList(roles));
			}
		} catch (Exception e) {}

		throw new UsernameNotFoundException("Invalid username/password.");
	}
}