package com.project_training.nourriture.api.users;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.project_training.nourriture.users.model.User;

public class UserSerializer implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject	jsonObject = new JsonObject();
		
		jsonObject.addProperty("username", user.getUsername());
		jsonObject.addProperty("password", user.getPassword());
		jsonObject.addProperty("email", user.getEmail());
		jsonObject.addProperty("gender", user.getGender());
		jsonObject.addProperty("firstname", user.getFirstname());
		jsonObject.addProperty("lastname", user.getLastname());
		jsonObject.addProperty("address", user.getAddress());
		jsonObject.addProperty("postal_code", user.getPostalCode());
		jsonObject.addProperty("city", user.getCity());
		jsonObject.addProperty("country", user.getCountry());
		jsonObject.addProperty("birthday", user.getBirthday());

		return jsonObject;
	}

}
