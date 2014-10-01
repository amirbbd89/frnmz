package com.frnmz.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

@Configuration
public class ConfigrationService {
	private String host;
	private int port;
	private String username;
	private String password;
	private String dbname;

	public ConfigrationService() {
		boolean isProduction = false;
		try {
			if(null != System.getenv("VCAP_SERVICES")){
				JSONObject credentialObj = new JSONObject(System.getenv("VCAP_SERVICES")).getJSONArray("mongodb2-2.4.8").getJSONObject(0).getJSONObject("credentials");

				host = credentialObj.getString("hostname");
				port = credentialObj.getInt("port");
				username = credentialObj.getString("username");
				password = credentialObj.getString("password");
				dbname = credentialObj.getString("db");

				isProduction = true;
				System.out.println("on Production Server");
			}
		} catch (JSONException e) {}

		if(!isProduction){
			System.out.println("on Dev Machine");

			host = "localhost";
			port = 27017;
			username = "";
			password = "";
			dbname = "frnmz";
		}
	}

	public @Bean Mongo mongo() throws Exception {
		Mongo mongo = new Mongo(host, port);
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials userCredentials = new UserCredentials(username, password);
		return new SimpleMongoDbFactory(mongo(), dbname, userCredentials);
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
}