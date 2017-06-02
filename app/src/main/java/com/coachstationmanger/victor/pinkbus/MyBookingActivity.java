package com.coachstationmanger.victor.pinkbus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.OrdersAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.TicketOrder;
import com.coachstationmanger.victor.pinkbus.ws.WebService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBookingActivity extends AppCompatActivity {
    @BindView(R.id.lv_orders)
    ListView lv_orders;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    List<TicketOrder> orderList=new ArrayList<>();
    BookingDetails booking=new BookingDetails();
    OrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        ButterKnife.bind(this);
        GetIntent();
        SetUpListView();
    }

    private void SetUpListView()
    {
        TicketOrder order=new TicketOrder();
        List<Integer> integers=new ArrayList<Integer>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        order.setListTicketId(integers);
        order.setEmail("someone@gmail.com");
        order.setReceived_Date("2017-04-02");
        order.setOrder_Date("2017-04-02");
        order.setStatus_Ticket("PAYED");
        order.setOrder_Id("ORDER1");
        order.setPayment_Id("ONLINE");
        order.setPayout_Date("2017-04-02");
        order.setTotalPrice(260);
        orderList.add(order);
        adapter=new OrdersAdapter(getApplicationContext(),R.layout.order_ticket_item,orderList);
        lv_orders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*private void SetUpToolBar()
    {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setTitleTextColor(Color.WHITE);
    }*/

    private void GetIntent()
    {
        Bundle bundle=getIntent().getBundleExtra("DataIntent");
        booking= (BookingDetails) bundle.getSerializable("booking6");
        String order_id=(String) bundle.get("order_id");
        //if (!booking.isLoginStatus())
        if (true)
        {
            CustomDialogAlert();
        }
        //Async_GetOrderLst async_getOrderLst=new Async_GetOrderLst();
        //async_getOrderLst.execute(order_id,booking.getStrEmail());
    }

    private void CustomDialogAlert()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MyBookingActivity.this);
        builder.setTitle("Is this the first time You use PinkBus App?");
        builder.setMessage("If you have account of PinkBus App, please press 'I have account' ");
        String positiveText="Yes";
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomDialogSignUp();
            }
        });
        String negativeText="I have account";
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomDialogSignIn();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void CustomDialogSignIn()
    {
        final Dialog dialog=new Dialog(getApplicationContext());
        dialog.setContentView(R.layout.layout_login);
        //
        final EditText edtEmail=(EditText)dialog.findViewById(R.id.your_email_address);
        final EditText edtPassword=(EditText) dialog.findViewById(R.id.create_new_password);
        Button btnOk=(Button)dialog.findViewById(R.id.btn_ok);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async_LoginPassenger loginPassenger=new Async_LoginPassenger();
                loginPassenger.execute(edtEmail.getText().toString(),edtPassword.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void CustomDialogSignUp()
    {
        final Dialog dialog=new Dialog(MyBookingActivity.this);
        dialog.setContentView(R.layout.layout_sign_up_passenger);
        //
        final EditText edtEmail=(EditText)dialog.findViewById(R.id.your_email_address);
        final EditText edtFullName=(EditText)dialog.findViewById(R.id.your_full_name);
        final EditText edtTel=(EditText) dialog.findViewById(R.id.your_telephone_number);
        final EditText edtPassword=(EditText) dialog.findViewById(R.id.create_new_password);
        final EditText edtConfirmPass=(EditText) dialog.findViewById(R.id.confirm_password);
        Button btnOk=(Button)dialog.findViewById(R.id.btn_ok);
        Button btnCancer=(Button) dialog.findViewById(R.id.btn_cancer);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingPerson person=new BookingPerson();
                person.setStrEmail(edtEmail.getText().toString());
                person.setStrName(edtFullName.getText().toString());
                person.setStrTel(edtTel.getText().toString());
                person.setStrPassword(edtPassword.getText().toString());
                Async_SignUpPassenger signUpPassenger=new Async_SignUpPassenger();
                signUpPassenger.execute(person);
                dialog.dismiss();
            }
        });
        btnCancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /*private void LoginStatus(boolean stt,String title)
    {
        if (stt)
        {
            mToolBar.setBackgroundColor(Color.rgb(255,69,0));
            mToolBar.setSubtitle("Welcome " + title);
            getSupportActionBar().setTitle(booking.getStrName());
        }
    }*/

    private class Async_GetOrderLst extends AsyncTask<String,Void,List<TicketOrder>>
    {
        @Override
        protected List<TicketOrder> doInBackground(String... params) {
            WebService ws=new WebService();
            return ws.TicketOrderList(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(List<TicketOrder> ticketOrders) {
            super.onPostExecute(ticketOrders);
            if (ticketOrders.size()>0)
            {
                adapter=new OrdersAdapter(getApplicationContext(),R.layout.order_ticket_item,orderList);
                lv_orders.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Snackbar snackbar=Snackbar.make(linearLayout,"Done",Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            }

        }
    }

    private class Async_SignUpPassenger extends AsyncTask<BookingPerson,Void,BookingPerson>
    {

        @Override
        protected BookingPerson doInBackground(BookingPerson... params) {
            WebService ws=new WebService();
            return ws.SignUpPassenger(params[0]);
        }

        @Override
        protected void onPostExecute(BookingPerson person) {
            super.onPostExecute(person);
            if (person.getStrEmail().compareTo("0")<1)
            {
                Toast.makeText(MyBookingActivity.this,"Sign Up Failed!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //bookingPerson=person;
                booking.setStrName(person.getStrName());
                booking.setStrEmail(person.getStrEmail());
                booking.setStrTel(person.getStrTel());
                booking.setStrPassword(person.getStrPassword());
                booking.setLoginStatus(person.isLoginStatus());
                //LoginStatus(booking.isLoginStatus(),booking.getStrName());
                /*Intent intentToItinerary=new Intent(MyBookingActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Toast.makeText(MyBookingActivity.this,booking.getStrName()+" Welcome you to here!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Async_LoginPassenger extends AsyncTask<String,Void,BookingPerson>
    {
        @Override
        protected BookingPerson doInBackground(String... params) {
            WebService ws=new WebService();
            return ws.LoginPassenger(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(BookingPerson person) {
            super.onPostExecute(person);
            if (person.getStrEmail().compareTo("0")<1)
            {
                Toast.makeText(MyBookingActivity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //bookingPerson=person;
                booking.setStrName(person.getStrName());
                booking.setStrEmail(person.getStrEmail());
                booking.setStrTel(person.getStrTel());
                booking.setStrPassword(person.getStrPassword());
                booking.setLoginStatus(person.isLoginStatus());
                //LoginStatus(booking.isLoginStatus(),booking.getStrName());
                /*Intent intentToItinerary=new Intent(MyBookingActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Toast.makeText(MyBookingActivity.this,booking.getStrName()+" Login Success!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
