package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.Itinerary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 18/04/2017.
 */

public class ItineraryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Itinerary> mList=new ArrayList<>();
    private int mLayout;

    public ItineraryAdapter(Context mContext, List<Itinerary> mList, int mLayout) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayout = mLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Itinerary getItem(int position) {
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
        Itinerary itinerary=getItem(position);
        TextView tv=(TextView)convertView.findViewById(R.id.tv_itinerary_name);
        tv.setText(itinerary.getItineraryName());
        return convertView;
    }
}
