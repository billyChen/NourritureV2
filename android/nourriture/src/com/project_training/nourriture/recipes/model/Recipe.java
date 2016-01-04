package com.project_training.nourriture.recipes.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.project_training.nourriture.ingredients.model.Ingredient;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

	@SerializedName("_id")
	private String	_id;

	@SerializedName("name")
	protected String 	name;

	@SerializedName("ingredients")
	protected List<Ingredient>	ingredients;

	@SerializedName("country")
	protected String	country;

	@SerializedName("time")
	protected String	time;

	@SerializedName("calories")
	protected long	calories;

	@SerializedName("description")
	protected String	description;

	@SerializedName("type")
	protected String	type;

	@SerializedName("cost")
	protected long	cost;

	@SerializedName("difficulty")
	protected long	difficulty;

	@SerializedName("image")
	protected String	image;

	public Recipe() {}

    public Recipe(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        this._id = data[0];
        this.name = data[1];
    }

	public String	getId() {
		return _id;
	}
	
	public void	setId(String _id) {
		this._id = _id;
	}
	
	public String getName() {
		return name;
	}
	
	public void	setName(String name) {
		this.name = name;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setListIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getCalories() {
		return calories;
	}

	public void setCalories(long calories) {
		this.calories = calories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(long difficulty) {
		this.difficulty = difficulty;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Recipe [_id=" + _id + ", name=" + name + ", ingredients=" + ingredients + ", country=" + country
				+ ", time=" + time + ", calories=" + calories + ", description=" + description + ", type=" + type
				+ ", cost=" + cost + ", difficulty=" + difficulty + ", image=" + image + "]";
	}
	
	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { this._id, this.name });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
