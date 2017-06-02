package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.RouteDetails;
import com.coachstationmanger.victor.pinkbus.models.TicketDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 16/04/2017.
 */

public class TicketAdapter extends BaseAdapter {
    private Context mContext;
    private List<TicketDetails> mList;
    private int mLayout;

    public TicketAdapter(Context mContext, List<TicketDetails> mList, int mLayout) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayout = mLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TicketDetails getItem(int position) {
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
        TicketDetails t=mList.get(position);

        TextView tel=(TextView)convertView.findViewById(R.id.tv_p_tel);
        tel.setText(t.getTelOfPassenger());
        TextView name=(TextView)convertView.findViewById(R.id.tv_p_name);
        name.setText(t.getNameOfPassenger());
        TextView company=(TextView)convertView.findViewById(R.id.tv_company_name);
        company.setText(t.getCompanyName());
        TextView ticket_id=(TextView)convertView.findViewById(R.id.tv_ticket_id);
        ticket_id.setText(String.valueOf(t.getTicketID()));
        TextView seat_id=(TextView)convertView.findViewById(R.id.tv_p_seat_id);
        seat_id.setText(t.getSeatID());
        TextView boarding=(TextView)convertView.findViewById(R.id.tv_p_boarding_local);
        boarding.setText(t.getBoardingLocal());
        TextView dropping=(TextView)convertView.findViewById(R.id.tv_p_dropping_local);
        dropping.setText(t.getDroppingLocal());
        TextView dpt_date=(TextView)convertView.findViewById(R.id.tv_date);
        dpt_date.setText(t.getDepartureDate());
        TextView time=(TextView)convertView.findViewById(R.id.tv_time);
        time.setText(t.getDepartureTime());
        TextView from=(TextView)convertView.findViewById(R.id.tv_from);
        from.setText(t.getDepartureStation());
        TextView to=(TextView)convertView.findViewById(R.id.tv_to);
        to.setText(t.getArrivalStation());
        TextView price=(TextView)convertView.findViewById(R.id.tv_p_price);
        price.setText(String.valueOf(t.getPrice()));
        return convertView;
    }
}
