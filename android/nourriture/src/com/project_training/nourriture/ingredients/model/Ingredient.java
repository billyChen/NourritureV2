package com.project_training.nourriture.ingredients.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

	@SerializedName("_id")
	private String	_id;

	@SerializedName("name")
	private String	name;

	@SerializedName("calories")
	private float	calories;
	
	@SerializedName("description")
	private String	description;
	
	@SerializedName("protein")
	private float	protein;

	@SerializedName("lipid")
	private float	lipid;

	@SerializedName("saturated fat")
	private float	saturated_fat;

	@SerializedName("carbohydrate")
	private float	carbohydrate;
	
	@SerializedName("starch")
	private float	starch;

	@SerializedName("Total sugar")
	private float	total_sugar;

	@SerializedName("dietary fiber")
	private float	dietary_fiber;
	
	@SerializedName("salt")
	private float	salt;

	@SerializedName("calcium")
	private float	calcium;

	@SerializedName("magnesium")
	private float	magnesium;

	@SerializedName("phosphorus")
	private float	phosphorus;

	@SerializedName("potassium")
	private float	potassium;

	@SerializedName("sodium")
	private float	sodium;

	@SerializedName("E")
	private	float	E;

	@SerializedName("B1")
	private float	B1;

	@SerializedName("B2")
	private float	B2;

	@SerializedName("B6")
	private float	B6;

	@SerializedName("B9")
	private float	B9;

	@SerializedName("quantity")
	private String	quantity;
	
	public Ingredient(String name) {
		this.name = name;
	}
	
	public String getId() {
		return _id;
	}
	
	public void	setId(String _id) {
		this._id = _id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void	setQuantity(String quantity) {
		this.quantity = quantity;
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

	public float getProtein() {
		return protein;
	}

	public void setProtein(float protein) {
		this.protein = protein;
	}

	public float getLipid() {
		return lipid;
	}

	public void setLipid(float lipid) {
		this.lipid = lipid;
	}

	public float getSaturatedFat() {
		return saturated_fat;
	}

	public void setSaturatedFat(float saturated_fat) {
		this.saturated_fat = saturated_fat;
	}

	public float getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(float carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public float getStarch() {
		return starch;
	}

	public void setStarch(float starch) {
		this.starch = starch;
	}

	public float getTotalSugar() {
		return total_sugar;
	}

	public void setTotalSugar(float total_sugar) {
		this.total_sugar = total_sugar;
	}

	public float getDietaryFiber() {
		return dietary_fiber;
	}

	public void setDietaryFiber(float dietary_fiber) {
		this.dietary_fiber = dietary_fiber;
	}

	public float getSalt() {
		return salt;
	}

	public void setSalt(float salt) {
		this.salt = salt;
	}

	public float getCalcium() {
		return calcium;
	}

	public void setCalcium(float calcium) {
		this.calcium = calcium;
	}

	public float getMagnesium() {
		return magnesium;
	}

	public void setMagnesium(float magnesium) {
		this.magnesium = magnesium;
	}

	public float getPhosphorus() {
		return phosphorus;
	}

	public void setPhosphorus(float phosphorus) {
		this.phosphorus = phosphorus;
	}

	public float getPotassium() {
		return potassium;
	}

	public void setPotassium(float potassium) {
		this.potassium = potassium;
	}

	public float getSodium() {
		return sodium;
	}

	public void setSodium(float sodium) {
		this.sodium = sodium;
	}

	public float getE() {
		return E;
	}

	public void setE(float e) {
		E = e;
	}

	public float getB1() {
		return B1;
	}

	public void setB1(float b1) {
		B1 = b1;
	}

	public float getB2() {
		return B2;
	}

	public void setB2(float b2) {
		B2 = b2;
	}

	public float getB6() {
		return B6;
	}

	public void setB6(float b6) {
		B6 = b6;
	}

	public float getB9() {
		return B9;
	}

	public void setB9(float b9) {
		B9 = b9;
	}

	@Override
	public String toString() {
		return "Ingredient [_id=" + _id + ", name=" + name + ", calories=" + calories + ", description=" + description
				+ ", protein=" + protein + ", lipid=" + lipid + ", saturated_fat=" + saturated_fat + ", carbohydrate="
				+ carbohydrate + ", starch=" + starch + ", total_sugar=" + total_sugar + ", dietary_fiber="
				+ dietary_fiber + ", salt=" + salt + ", calcium=" + calcium + ", magnesium=" + magnesium
				+ ", phosphorus=" + phosphorus + ", potassium=" + potassium + ", sodium=" + sodium + ", E=" + E
				+ ", B1=" + B1 + ", B2=" + B2 + ", B6=" + B6 + ", B9=" + B9 + ", quantity=" + quantity + "]";
	}
}
