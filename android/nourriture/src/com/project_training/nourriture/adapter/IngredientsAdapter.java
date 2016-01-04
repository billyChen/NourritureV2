package com.project_training.nourriture.adapter;

import java.util.List;

import com.project_training.nourriture.R;
import com.project_training.nourriture.ingredients.model.Ingredient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IngredientsAdapter extends ArrayAdapter<List<Ingredient>> {

	private Activity 			context;
	private List<Ingredient>	listIngredients;
	
	static class ViewHolder {
		@Bind(R.id.title_ingredient) TextView	titleIngredient;
		@Bind(R.id.desc_ingredient) TextView	descIngredient;
	
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
	
	public IngredientsAdapter(Activity context, List<Ingredient> listIngredients) {
		super(context, -1);
		this.context = context;
		this.listIngredients = listIngredients;
	}
	
	public List<Ingredient> getListIngredients() {
		return listIngredients;
	}
	
	@Override
	public int getCount() {
		return listIngredients.size();
	}

	@Override
	public long getItemId(int position) {
		return listIngredients.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if ( convertView == null ) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listview_ingredient_row, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.titleIngredient.setText(listIngredients.get(position).getName());
		holder.descIngredient.setText(listIngredients.get(position).getDescription());
		
		return convertView;
	}
}
