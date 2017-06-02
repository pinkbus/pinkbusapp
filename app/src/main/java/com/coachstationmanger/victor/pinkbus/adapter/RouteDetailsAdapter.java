package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.Main2Activity;
import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.RouteDetails;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Victor on 24/03/2017.
 */

public class RouteDetailsAdapter extends BaseAdapter {

    private Context mContext;
    private List<RouteDetails> mList;
    private int mLayout;

    public RouteDetailsAdapter(Context context, List<RouteDetails> list, int layout) {
        this.mContext = context;
        this.mList = list;
        this.mLayout = layout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RouteDetails getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RouteDetails route=mList.get(position);
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(mLayout,null);

        TextView tv_station_office_name=(TextView)convertView.findViewById(R.id.tv_station_office_name);
        tv_station_office_name.setText(route.getCompany_Name());
        TextView tv_departure_time=(TextView)convertView.findViewById(R.id.tv_departure_time);
        tv_departure_time.setText(route.getDeparture_Time());
        TextView tv_type_seat=(TextView)convertView.findViewById(R.id.tv_type_seat);
        tv_type_seat.setText(route.getType_Seat());
        TextView tv_arrival_time=(TextView)convertView.findViewById(R.id.tv_arrival_time);
        tv_arrival_time.setText(route.getArrival_Time());
        TextView tv_total_seat=(TextView)convertView.findViewById(R.id.tv_total_seat);
        tv_total_seat.setText(String.valueOf(route.getTotal_Seat()));
        TextView tv_route_time=(TextView)convertView.findViewById(R.id.tv_route_time);
        tv_route_time.setText(String.valueOf(route.getTotal_Seat()-route.getSeatList().size()));//*//
        TextView tv_route_price=(TextView)convertView.findViewById(R.id.tv_route_price);
        tv_route_price.setText(String.valueOf(route.getRoute_Price())+" VND");

        return convertView;
    }

}
