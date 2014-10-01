package com.frnmz.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.frnmz.dao.GenericDAO;

@Service
public class GenericDAOImpl<E> implements GenericDAO<E> {
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public boolean insertObject(E object) {
		try {
			mongoOperations.insert(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateObject(E object) {
		try {
			mongoOperations.save(object);
			return true;
		} catch (Exception e) {}

		return false;
	}

	@Override
	public boolean deleteObject(E object) {
		try {
			mongoOperations.remove(object);
			return true;
		} catch (Exception e) {}

		return false;
	}
}