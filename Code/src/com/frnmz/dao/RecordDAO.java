package com.frnmz.dao;

import java.util.List;

import com.frnmz.model.Record;

public interface RecordDAO {
	public List<Record> getAllRecord(String emailId);
	public List<Record> getAllRecord();
	public boolean removeAllRecord();
}