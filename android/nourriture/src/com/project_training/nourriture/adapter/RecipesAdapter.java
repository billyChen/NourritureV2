package com.project_training.nourriture.adapter;

import java.util.List;

import com.project_training.nourriture.R;
import com.project_training.nourriture.recipes.model.Recipe;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipesAdapter extends ArrayAdapter<List<Recipe>> {

	private Activity 		context;
	private List<Recipe>	listRecipes;

	static class ViewHolder {
		
		@Bind(R.id.img_recipe) ImageView	imgRecipe;
		@Bind(R.id.title_recipe) TextView 	titleRecipe;
		@Bind(R.id.type_recipe) TextView 	typeRecipe;
		@Bind(R.id.difficulty_recipe) RatingBar difficultyRecipe;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
	
	public RecipesAdapter(Activity context, List<Recipe> listRecipes) {
		super(context, -1);
		this.context = context;
		this.listRecipes = listRecipes;
	}
	
	public List<Recipe> getListRecipes() {
		return listRecipes;
	}
	
	@Override
	public int getCount() {
		return listRecipes.size();
	}

	@Override
	public long getItemId(int position) {
		return listRecipes.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ( convertView == null ) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listview_recipe_row, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		
//		holder.imgRecipe.setImageResource(listRecipes.get(position).getImage());
		
		holder.titleRecipe.setText(listRecipes.get(position).getName());
		
		holder.typeRecipe.setText(listRecipes.get(position).getType());
		
		holder.difficultyRecipe.setIsIndicator(true);
		holder.difficultyRecipe.setRating(listRecipes.get(position).getDifficulty());

		return convertView;
	}
}
