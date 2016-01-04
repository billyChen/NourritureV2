package com.project_training.nourriture.users.model;

import com.google.gson.annotations.SerializedName;

import android.preference.Preference;

public class User {

	@SerializedName("_id")
	private String		_id;

	@SerializedName("username")
	private String		username;

	@SerializedName("password")
	private String		password;

	@SerializedName("email")
	private String		email;
	
	@SerializedName("gender")
	private String		gender;

	@SerializedName("firstname")
	private String		firstname;

	@SerializedName("lastname")
	private String		lastname;
	
	@SerializedName("address")
	private String		address;

	@SerializedName("postal_code")
	private String		postal_code;

	@SerializedName("city")
	private String		city;

	@SerializedName("country")
	private String		country;

	@SerializedName("birthday")
	private String		birthday;

	@SerializedName("allergens")
	private Allergen	allergens;

	@SerializedName("preferences")
	private Preference	preferences;

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String	getEmail() {
		return email;
	}
	
	public void	setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postal_code;
	}

	public void setPostalCode(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Allergen getAllergens() {
		return allergens;
	}

	public void setAllergens(Allergen allergens) {
		this.allergens = allergens;
	}

	public Preference getPreferences() {
		return preferences;
	}

	public void setPreferences(Preference preferences) {
		this.preferences = preferences;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", username=" + username + ", gender=" + gender + ", password=" + password + "]";
	}	
}
