package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.SeatDetails;

import java.util.List;

/**
 * Created by Victor on 29/03/2017.
 */

public class PassengerAdapter extends BaseAdapter {

    private Context mContext;
    private List<SeatDetails> mList;
    private int mLayout;

    public PassengerAdapter(Context mContext, List<SeatDetails> list, int mLayout) {
        this.mContext = mContext;
        this.mList = list;
        this.mLayout = mLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SeatDetails getItem(int position) {
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
        SeatDetails seat=mList.get(position);
        TextView tv_seat_id=(TextView)convertView.findViewById(R.id.tv_p_seat_id);
        tv_seat_id.setText(seat.getSeatID());
        TextView tv_name=(TextView)convertView.findViewById(R.id.tv_p_name);
        tv_name.setText(seat.getPassengerName());
        TextView tv_tel=(TextView)convertView.findViewById(R.id.tv_p_tel);
        tv_tel.setText(seat.getTelOfPassenger());
        return convertView;
    }
}
