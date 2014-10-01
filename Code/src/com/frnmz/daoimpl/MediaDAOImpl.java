package com.frnmz.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.frnmz.dao.MediaDAO;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class MediaDAOImpl implements MediaDAO {
	@Autowired
	MongoOperations mongoOperations;

	@Override
	public boolean saveFile(String emailId, byte[] inputBytes) {
		try {
			GridFS gridFS = new GridFS(mongoOperations.getCollection("userinfo").getDB());
			
			if(gridFS.find(emailId).size() > 0){
				gridFS.remove(emailId);
			}
			
			GridFSInputFile gfsFile = gridFS.createFile(inputBytes);
			gfsFile.setFilename(emailId);
			gfsFile.setId(emailId);
			gfsFile.save();

			return true;
		} catch (Exception e) {}
		return false;
	}

	@Override
	public GridFSDBFile getFile(String emailId) {
		try {
			return new GridFS(mongoOperations.getCollection("userinfo").getDB()).findOne(emailId);
		} catch (Exception e) {}
		
		return null;
	}
}