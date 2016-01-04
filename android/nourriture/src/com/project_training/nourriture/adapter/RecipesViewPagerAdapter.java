package com.project_training.nourriture.adapter;

import com.project_training.nourriture.recipes.controller.RecipesListFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class RecipesViewPagerAdapter extends FragmentStatePagerAdapter {

	CharSequence	titles[];
	int				numOfTabs;
	
	public RecipesViewPagerAdapter(FragmentManager fm, CharSequence titles[], int numOfTabs) {
		super(fm);
		
		this.titles = titles;
		this.numOfTabs = numOfTabs;
	}

	@Override
	public Fragment getItem(int pos) {

		Fragment	fragment = new Fragment();

		switch (pos) {
			case 0:
				fragment = new RecipesListFragment();
				break;
			case 1:
				fragment = new RecipesListFragment();
				break;
				
			default:
				break;
		}
		
		return fragment;
	}
	
	public CharSequence	getPageTitle(int pos) {
		return titles[pos];
	}

	@Override
	public int getCount() {
		return numOfTabs;
	}

}
