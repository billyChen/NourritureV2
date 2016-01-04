package com.project_training.nourriture.products.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.project_training.nourriture.ingredients.model.Ingredient;

public class Product {

	@SerializedName("_id")
	private String	_id;
	
	@SerializedName("name")
	private String	name;
	
	@SerializedName("ingredients")
	private List<Ingredient> ingredients;
	
	@SerializedName("country")
	private String	country;
	
	@SerializedName("price")
	private float	price;
	
	@SerializedName("calories")
	private float	calories;
	
	@SerializedName("description")
	private String	description;
	
	@SerializedName("allergens")
	private List<Allergen> allergens;

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getCalories() {
		return calories;
	}

	public void setCalories(float calories) {
		this.calories = calories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Allergen> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<Allergen> allergens) {
		this.allergens = allergens;
	}
	
	
}
