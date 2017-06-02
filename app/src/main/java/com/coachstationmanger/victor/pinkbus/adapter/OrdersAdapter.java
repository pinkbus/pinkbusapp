package com.coachstationmanger.victor.pinkbus.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.PaymentActivity;
import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.TicketOrder;

import java.util.List;

/**
 * Created by Victor on 23/04/2017.
 */

public class OrdersAdapter extends BaseAdapter {

    private Context mContext;
    private int mLayout;
    private List<TicketOrder> mList;

    public OrdersAdapter(Context mContext, int mLayout, List<TicketOrder> mList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TicketOrder getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(mLayout,null);
        final TicketOrder tko=mList.get(position);
        TextView textView2=(TextView)convertView.findViewById(R.id.tv_ticket_id);
        textView2.setText(String.valueOf(tko.getTicketID()));
        TextView textView3=(TextView)convertView.findViewById(R.id.tv_email);
        textView3.setText(tko.getEmail());
        TextView textView=(TextView)convertView.findViewById(R.id.tv_order_date);
        textView.setText(tko.getOrder_Date());
        TextView textView4=(TextView)convertView.findViewById(R.id.tv_payment_id);
        textView4.setText(tko.getPayment_Id());
        TextView textView5=(TextView)convertView.findViewById(R.id.tv_status);
        textView5.setText(tko.getStatus_Ticket());
        TextView textView6=(TextView)convertView.findViewById(R.id.tv_received_date);
        textView6.setText(tko.getReceived_Date());
        TextView textView7=(TextView)convertView.findViewById(R.id.tv_total_price);
        textView7.setText("0VND");
        Button button=(Button)convertView.findViewById(R.id.btn_payout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"This not setup control",Toast.LENGTH_SHORT).show();
            }
        });
        Button button1=(Button)convertView.findViewById(R.id.btn_cancer);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"This not setup control",Toast.LENGTH_SHORT).show();
            }
        });
        Button button2=(Button)convertView.findViewById(R.id.btn_view_details);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(parent.getContext());
                dialog.setContentView(R.layout.passenger_item);
                TextView tel=(TextView)dialog.findViewById(R.id.tv_p_tel);
                tel.setText(tko.getTelOfPassenger());
                TextView name=(TextView)dialog.findViewById(R.id.tv_p_name);
                name.setText(tko.getNameOfPassenger());
                TextView company=(TextView)dialog.findViewById(R.id.tv_company_name);
                company.setText(tko.getCompanyName());
                TextView ticket_id=(TextView)dialog.findViewById(R.id.tv_ticket_id);
                ticket_id.setText(String.valueOf(tko.getTicketID()));
                TextView seat_id=(TextView)dialog.findViewById(R.id.tv_p_seat_id);
                seat_id.setText(tko.getSeatID());
                TextView boarding=(TextView)dialog.findViewById(R.id.tv_p_boarding_local);
                boarding.setText(tko.getBoardingLocal());
                TextView dropping=(TextView)dialog.findViewById(R.id.tv_p_dropping_local);
                dropping.setText(tko.getDroppingLocal());
                TextView dpt_date=(TextView)dialog.findViewById(R.id.tv_date);
                dpt_date.setText(tko.getDepartureDate());
                TextView time=(TextView)dialog.findViewById(R.id.tv_time);
                time.setText(tko.getDepartureTime());
                TextView from=(TextView)dialog.findViewById(R.id.tv_from);
                from.setText(tko.getDepartureStation());
                TextView to=(TextView)dialog.findViewById(R.id.tv_to);
                to.setText(tko.getArrivalStation());
                TextView price=(TextView)dialog.findViewById(R.id.tv_p_price);
                price.setText(String.valueOf(tko.getPrice()));
                dialog.show();
            }
        });
        Button button3=(Button)convertView.findViewById(R.id.btn_QR);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"This not setup control",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
