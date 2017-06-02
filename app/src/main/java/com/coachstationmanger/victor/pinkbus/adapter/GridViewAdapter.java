package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 01/04/2017.
 */

public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    List<City> mList=new ArrayList<City>();
    int mLayout;

    public GridViewAdapter(Context mContext, List<City> mList, int mLayout) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayout = mLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(mLayout,null);
        City city=mList.get(position);
        TextView tv_city_name=(TextView)convertView.findViewById(R.id.tv_city_name);
        tv_city_name.setText(city.getCityName());
        return convertView;
    }
}
