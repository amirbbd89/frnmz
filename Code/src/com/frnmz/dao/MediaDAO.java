package com.frnmz.dao;

import com.mongodb.gridfs.GridFSDBFile;

public interface MediaDAO {
	public boolean saveFile(String emailId, byte[] inputBytes);
	public GridFSDBFile getFile(String emailId);
}