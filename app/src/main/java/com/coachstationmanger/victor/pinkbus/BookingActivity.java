package com.coachstationmanger.victor.pinkbus;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.PassengerAdapter;
import com.coachstationmanger.victor.pinkbus.adapter.TicketAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.RouteDetails;
import com.coachstationmanger.victor.pinkbus.models.Seat;
import com.coachstationmanger.victor.pinkbus.models.SeatDetails;
import com.coachstationmanger.victor.pinkbus.models.Ticket;
import com.coachstationmanger.victor.pinkbus.models.TicketDetails;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingActivity extends AppCompatActivity {

    @BindView(R.id.input_name)
    EditText edt_input_name;
    @BindView(R.id.input_email)
    EditText edt_input_email;
    @BindView(R.id.input_tel)
    EditText edt_input_tel;
    @BindView(R.id.lv_passenger)
    ListView lv_passenger;
    @BindView(R.id.btn_booking)
    ImageButton btn_booking;
    @BindView(R.id.toolbar_booking)
    Toolbar mToolbar;
    @BindView(R.id.tv_order_id)
            TextView tv_order_id;
    BookingDetails booking = new BookingDetails();
    List<SeatDetails> seats = new ArrayList<>();
    BookingPerson person = new BookingPerson();
    List<TicketDetails> ticketDetailsList=new ArrayList<>();
    SeatDetails seat;
    int tk_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);
        GetIntent();
        SetUpToolBar();
        SetUpListView();
        //
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingTicket();
                booking.setOrder_Id(tv_order_id.getText().toString());
                Intent intentToPayment = new Intent(BookingActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("booking5", (Serializable) booking);
                intentToPayment.putExtra("Bundle5", bundle);
                startActivity(intentToPayment);
            }
        });

    }

    private void SetUpOrderID()
    {
        Random r=new Random();
        int id=r.nextInt(100);
        String order_id=String.valueOf("ORDER"+id);
        tv_order_id.setText(order_id);
    }

    private void GetIntent()
    {
        Bundle bundle = this.getIntent().getBundleExtra("Bundle4");
        booking = (BookingDetails) bundle.getSerializable("booking4");
        seats.addAll(booking.getList());
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

    private void SetUpListView()
    {
        edt_input_email.setText(booking.getStrEmail());
        edt_input_name.setText(booking.getStrName());
        edt_input_tel.setText(booking.getStrTel());
        SetUpOrderID();
        //
        TicketAdapter adapter=new TicketAdapter(getApplicationContext(),ticketDetailsList,R.layout.passenger_item);
        lv_passenger.setAdapter(adapter);
        for (SeatDetails s:seats)
        {
            TicketDetails tk=new TicketDetails();
            tk.setTicketID(0);
            tk.setCompanyName(booking.getCompany_Name());
            tk.setDepartureStation(booking.getDepartureStation());
            tk.setArrivalStation(booking.getArrivalStation());
            tk.setDepartureDate(booking.getDateDeparture());
            tk.setDepartureTime(booking.getDeparture_Time());
            tk.setNameOfPassenger(s.getPassengerName());
            tk.setTelOfPassenger(s.getTelOfPassenger());
            tk.setSeatID(s.getSeatID());
            tk.setBoardingLocal(s.getBoardingLocation());
            tk.setDroppingLocal(s.getDroppingLocation());
            tk.setPrice(booking.getRoute_Price());
            tk.setCoachRouteID(booking.getCoach_Route_Id());
            tk.setEmail(edt_input_email.getText().toString());
            ticketDetailsList.add(tk);
            adapter.notifyDataSetChanged();
        }
    }

    public void BookingTicket() {

        for (int i=0; i<ticketDetailsList.size();i++)
        {
            ticketDetailsList.get(i).setEmail(edt_input_email.getText().toString());
            ticketDetailsList.get(i).setOrderID(tv_order_id.getText().toString());
            AsyncBookingTicket asyncBookingTicket=new AsyncBookingTicket();
            asyncBookingTicket.execute(ticketDetailsList.get(i));
        }
    }

    private class AsyncBookingTicket extends AsyncTask<TicketDetails,Void,Integer>
    {
        @Override
        protected Integer doInBackground(TicketDetails... params) {
            WebService ws=new WebService();
            return ws.BookingTicket(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"LOADING....",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer ticketDetailsList) {
            super.onPostExecute(ticketDetailsList);
            if (ticketDetailsList>0)
            {
                Toast.makeText(getApplicationContext(),"ADDED!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();

            }
        }
    }
}