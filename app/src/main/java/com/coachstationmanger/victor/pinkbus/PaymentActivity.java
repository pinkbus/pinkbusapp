package com.coachstationmanger.victor.pinkbus;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.TicketAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.Ticket;
import com.coachstationmanger.victor.pinkbus.models.TicketDetails;
import com.coachstationmanger.victor.pinkbus.models.TicketOrder;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.jar.Manifest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
        TextView tv_input_email;
    @BindView(R.id.input_price)
    TextView tv_input_price;
    @BindView(R.id.input_ticket_id)
    TextView tv_input_ticket_id;
    @BindView(R.id.input_date_order)
    TextView tv_input_date_order;
    @BindView(R.id.rd_onl)
        RadioButton rd_onl;
    @BindView(R.id.rd_off)
        RadioButton rd_off;
    @BindView(R.id.btn_payout)
    ImageButton btn_payout;
    @BindView(R.id.toolbar_payment)
    Toolbar mToolbar;
    @BindView(R.id.edt_dis_code)
            EditText edt_dis_code;
    @BindView(R.id.btn_confirm_dis)
    Button btn_confirm_dis;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv_seconds_count)
            TextView tv_seconds_count;
    @BindView(R.id.tv_order_id)
            TextView tv_order_id;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    BookingDetails booking=new BookingDetails();
    TicketAdapter adapter;
    List<TicketOrder> orderList=new ArrayList<TicketOrder>();
    String ticket_id="";
    int price=0;
    String payment_id="";
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        //
        GetIntent();
        SetUpToolBar();
        //
        final AsyncTicketOrderList asyncTicketOrderList=new AsyncTicketOrderList();
        asyncTicketOrderList.execute(booking.getOrder_Id(),booking.getStrEmail());
        //
        rd_onl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_onl.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"You selected ONL",Toast.LENGTH_SHORT).show();
                    payment_id="ONL";
                }
            }
        });
        rd_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"You selected OFF",Toast.LENGTH_SHORT).show();
                payment_id="OFF";
            }
        });
        btn_confirm_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dis=Integer.valueOf(edt_dis_code.getText().toString());
                price=price-dis;
                tv_input_price.setText(String.valueOf(price));
                Toast.makeText(getApplicationContext(),"Code Validated!!",Toast.LENGTH_SHORT).show();
            }
        });
        btn_payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                pb.setProgress(100);
                for(TicketOrder od:orderList)
                {
                    od.setTicketID(od.getTicketID());
                    od.setPayment_Id(payment_id);
                    od.setOrder_Id(tv_order_id.getText().toString());
                    AsyncUpdatePayment asyncUpdatePayment=new AsyncUpdatePayment();
                    asyncUpdatePayment.execute(od);
                }
            }
        });
        SetUpProgressBar();

    }

    private void SetUpProgressBar()
    {
        final int[] progressStatus = {0};
        long millisInFuture = 45000;
        long countDownInterval = 1000;
        int progressBarMaximumValue = (int)(millisInFuture/countDownInterval);
        pb.setMax(progressBarMaximumValue);
        countDownTimer=new CountDownTimer(millisInFuture,countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_seconds_count.setText(millisUntilFinished/1000 + "  Seconds...");
                progressStatus[0] +=1;
                pb.setProgress(progressStatus[0]);
            }

            @Override
            public void onFinish() {
                progressStatus[0] +=1;
                pb.setProgress(progressStatus[0]);
                tv_seconds_count.setText(progressStatus[0] - pb.getMax() + "  Seconds...");
                for (TicketOrder tk:orderList)
                {
                    AsyncDeleteTicket asyncDeleteTicket=new AsyncDeleteTicket();
                    asyncDeleteTicket.execute(tk);
                }
                CustomDialogAlert();
            }
        };
        countDownTimer.start();
    }

    public void SendSMS()
    {
        String phoneNo="0979677902";
        String message="Dat Ve Thanh Cong";
    }

    private void GetIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Bundle5");
        booking= (BookingDetails) bundle.getSerializable("booking5");
        tv_order_id.setText(booking.getOrder_Id());
    }

    private void SetUpToolBar()
    {
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Local");
        if (booking.isLoginStatus())
        {
            mToolbar.setBackgroundColor(Color.rgb(255,69,0));
            mToolbar.setSubtitle("Welcome " + booking.getStrName());
            mToolbar.setSubtitleTextColor(Color.WHITE);
        }
    }

    private void CustomDialogAlert()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle("Time Out");
        builder.setMessage("Your ticket was deleted!!");
        String positiveText="Yes, Exit App";
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        String negativeText="I Wanna Book Ticket One More!!";
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(PaymentActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private class AsyncDeleteTicket extends AsyncTask<TicketOrder,Void,Integer>
    {

        @Override
        protected Integer doInBackground(TicketOrder... params) {
            WebService ws=new WebService();
            return ws.DeleteTicket(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"LOADING...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer>0)
            {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsyncUpdatePayment extends AsyncTask<TicketOrder,Void,List<TicketOrder>> {

        @Override
        protected List<TicketOrder> doInBackground(TicketOrder... params) {
            WebService ws = new WebService();
            return ws.UpdatePayment(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "LOADING...", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPostExecute(List<TicketOrder> ticketOrders) {
            super.onPostExecute(ticketOrders);
            if (ticketOrders.size() == 1 && ticketOrders.get(0).getEmail().compareTo("0") == 0)
            //if (true)
            {
                Snackbar snackbar=Snackbar.make(linearLayout,"ERROR",Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                Snackbar snackbar=Snackbar.make(linearLayout,"Book Ticket Successfully",Snackbar.LENGTH_LONG);
                snackbar.show();
                Intent intentToMyBooking = new Intent(PaymentActivity.this, MyBookingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("order_id",tv_order_id.getText().toString());
                bundle.putSerializable("booking6",booking);
                intentToMyBooking.putExtra("DataIntent",bundle);
                startActivity(intentToMyBooking);
            }
        }
    }
    private class AsyncTicketOrderList extends AsyncTask<String,Void,List<TicketOrder>>
    {
        @Override
        protected List<TicketOrder> doInBackground(String... params) {
            WebService ws=new WebService();
            return ws.TicketOrderList(params[0],params[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"LOADING...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<TicketOrder> ticketOrders) {
            super.onPostExecute(ticketOrders);
            for (int i=0;i<ticketOrders.size();i++)
            {
                ticket_id=ticket_id+String.valueOf(ticketOrders.get(i).getTicketID())+" ";
                price=price+booking.getRoute_Price();
                tv_input_email.setText(ticketOrders.get(i).getEmail());
                tv_input_date_order.setText(ticketOrders.get(i).getOrder_Date());
            }
            tv_input_price.setText(String.valueOf(price));
            tv_input_ticket_id.setText(ticket_id);
            orderList.addAll(ticketOrders);
        }
    }
}
