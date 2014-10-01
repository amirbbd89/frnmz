package com.frnmz.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.frnmz.dao.RecordDAO;
import com.frnmz.model.Record;

@Service
public class RecordDAOImpl implements RecordDAO {
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public List<Record> getAllRecord(String emailId) {
		try {
			return mongoOperations.find(Query.query(Criteria.where("adminEmail").is(emailId)) ,Record.class);
		} catch (Exception e) {}
		
		return new ArrayList<Record>();
	}

	@Override
	public List<Record> getAllRecord() {
		try {
			return mongoOperations.findAll(Record.class);
		} catch (Exception e) {}
		
		return new ArrayList<Record>();
	}

	@Override
	public boolean removeAllRecord() {
		try {
			mongoOperations.dropCollection(Record.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}