package com.project_training.nourriture.adapter;

import java.util.List;

import com.project_training.nourriture.R;
import com.project_training.nourriture.utils.FontHelper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends ArrayAdapter<List<String>> {

	private Activity context;
	private List<Integer>	catImgList;
	private List<String>	catNameList;
	
	public NavigationAdapter(Activity context, List<Integer> catImgList, List<String> catNameList) {
		super(context, -1);
		this.context = context;
		this.catImgList = catImgList;
		this.catNameList = catNameList;
	}
	
	@Override
	public int getCount() {
		return catImgList.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		if ( convertView == null ) {
			LayoutInflater	inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.navigation_drawer_row_layout, parent, false);
			convertView.setPadding(20, 20, 20, 20);
				
			int	cat_logo = catImgList.get(pos);
			ImageView	imgCat = (ImageView) convertView.findViewById(R.id.cat_logo);
			imgCat.setImageResource(cat_logo);
			
			String	cat_name = catNameList.get(pos);
			TextView	txtCat = (TextView) convertView.findViewById(R.id.cat_name);
			txtCat.setText(cat_name);
		}
		
		return convertView;
	}

}
