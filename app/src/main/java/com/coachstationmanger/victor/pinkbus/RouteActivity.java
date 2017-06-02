package com.coachstationmanger.victor.pinkbus;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.RouteDetailsAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.Itinerary;
import com.coachstationmanger.victor.pinkbus.models.RouteDetails;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteActivity extends AppCompatActivity {

    @BindView(R.id.tv_station1)
    TextView tv_station1;
    @BindView(R.id.tv_station2)
    TextView tv_station2;
    @BindView(R.id.tv_date1)
    TextView tv_date1;
    @BindView(R.id.toolbar_route)
    Toolbar mToolBar;
    @BindView(R.id.linear_layout)
            LinearLayout linearLayout;
    /*

    @BindView(R.id.email_address_layout)
            TextInputLayout email_layout;
    @BindView(R.id.full_name_layout)
            TextInputLayout name_layout;
    @BindView(R.id.telephone_number_layout)
            TextInputLayout tel_layout;
    @BindView(R.id.password_layout)
            TextInputLayout password_layout;
    @BindView(R.id.confirm_password_layout)
            TextInputLayout confirm_layout;
    @BindView(R.id.your_email_address)
            EditText edtEmail;
    @BindView(R.id.your_full_name)
            EditText edtFullName;
    @BindView(R.id.your_telephone_number)
            EditText edtTel;
    @BindView(R.id.create_new_password)
            EditText edtPassword;
    @BindView(R.id.confirm_password)
            EditText edtConfirmPass;
    @BindView(R.id.btn_ok)
            Button btnOk;
    @BindView(R.id.btn_cancer)
            Button btnCancer;
    */
    List<RouteDetails> routeDetailsList = new ArrayList<RouteDetails>();
    ListView lvRouteDetails;
    RouteDetailsAdapter adapter;
    //
    public BookingDetails booking = new BookingDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
        SetUpToolBar();
        //
        GetIntent();
        //
        lvRouteDetails = (ListView) findViewById(R.id.lvRoute_details);
        adapter = new RouteDetailsAdapter(RouteActivity.this, routeDetailsList, R.layout.route_details_item);
        lvRouteDetails.setAdapter(adapter);
        prepareData();
        lvRouteDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RouteDetails route = (RouteDetails) lvRouteDetails.getItemAtPosition(position);
                booking.setCompany_Name(route.getCompany_Name());
                booking.setDeparture_Time(route.getDeparture_Time());
                booking.setType_Seat(route.getType_Seat());
                booking.setArrival_Time(route.getArrival_Time());
                booking.setTotal_Seat(route.getTotal_Seat());
                booking.setRoute_Time(route.getRoute_Time());
                booking.setRoute_Price(route.getRoute_Price());
                booking.setListBoarding(route.getListBoarding());
                booking.setListDropping(route.getListDropping());
                booking.setSeatList(route.getSeatList());
                booking.setCoach_Route_Id(route.getCoach_Route_Id());
                //
                if (booking.isLoginStatus())
                //if (true)
                {
                    if (booking.getType_Seat().compareTo("GHE") < 1) {
                        Intent intentToSSeat = new Intent(getApplicationContext(), SeatActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("booking2", (Serializable) booking);
                        intentToSSeat.putExtra("Bundle2", bundle);
                        startActivity(intentToSSeat);
                        Toast.makeText(getApplicationContext(), booking.getArrivalStation() +
                                booking.getDepartureStation(), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intentToSleeper = new Intent(getApplicationContext(), SleeperActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("booking2", (Serializable) booking);
                        intentToSleeper.putExtra("Bundle2", bundle);
                        startActivity(intentToSleeper);
                        Toast.makeText(getApplicationContext(), booking.getArrivalStation() +
                                booking.getDepartureStation(), Toast.LENGTH_SHORT).show();
                    }
                }
                CustomDialogAlert();
            }
        });
        tv_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDPTDate();
                if (tv_date1.getText().length()>0)
                {
                    Async_GetListRouteDetails async_getListRouteDetails=new Async_GetListRouteDetails();
                    async_getListRouteDetails.execute(tv_station1.getText().toString(),
                            tv_station2.getText().toString(),tv_date1.getText().toString());
                }
            }
        });


    }

    private void SetDPTDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(RouteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String dpt = String.valueOf(year) + "-" + String.valueOf("0"+(month+1)) + "-" + String.valueOf("0"+dayOfMonth);
                booking.setDateDeparture(dpt);
                tv_date1.setText(dpt);
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void SetUpToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Select Route");
    }

    private void LoginStatus(boolean stt, String title) {
        if (stt) {
            mToolBar.setBackgroundColor(Color.rgb(255, 69, 0));
            mToolBar.setSubtitle("Welcome " + title);
            mToolBar.setSubtitleTextColor(Color.WHITE);
        }
    }

    private void CustomDialogSignUp() {
        final Dialog dialog = new Dialog(RouteActivity.this);
        dialog.setContentView(R.layout.layout_sign_up_passenger);
        //
        final EditText edtEmail = (EditText) dialog.findViewById(R.id.your_email_address);
        final EditText edtFullName = (EditText) dialog.findViewById(R.id.your_full_name);
        final EditText edtTel = (EditText) dialog.findViewById(R.id.your_telephone_number);
        final EditText edtPassword = (EditText) dialog.findViewById(R.id.create_new_password);
        final EditText edtConfirmPass = (EditText) dialog.findViewById(R.id.confirm_password);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        Button btnCancer = (Button) dialog.findViewById(R.id.btn_cancer);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingPerson person = new BookingPerson();
                person.setStrEmail(edtEmail.getText().toString());
                person.setStrName(edtFullName.getText().toString());
                person.setStrTel(edtTel.getText().toString());
                person.setStrPassword(edtPassword.getText().toString());
                Async_SignUpPassenger signUpPassenger = new Async_SignUpPassenger();
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

    private void CustomDialogSignIn() {
        final Dialog dialog = new Dialog(RouteActivity.this);
        dialog.setContentView(R.layout.layout_login);
        //
        final EditText edtEmail = (EditText) dialog.findViewById(R.id.your_email_address);
        final EditText edtPassword = (EditText) dialog.findViewById(R.id.create_new_password);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async_LoginPassenger loginPassenger = new Async_LoginPassenger();
                loginPassenger.execute(edtEmail.getText().toString(), edtPassword.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void CustomDialogAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RouteActivity.this);
        builder.setTitle(getString(R.string.do_you_hav_acc_gmail));
        builder.setMessage(getString(R.string.messages));
        String positiveText = getString(R.string.sign_up);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomDialogSignUp();
            }
        });
        String negativeText = getString(R.string.sign_in);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomDialogSignIn();
            }
        });
        /*builder.setNegativeButton("I have no another account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent goi dien thoai toi tong dai
                MakeACall();
            }
        });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void MakeACall() {
        String company_name = booking.getCompany_Name();
        switch (company_name) {
            case "PHUONG TRANG":
                try {
                    String phoneNumber = "19006067";

                    Intent it = new Intent(Intent.ACTION_CALL);
                    it.setData(Uri.parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(it);
                    break;
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                }
            case "THANH BUOI":
                try {
                    String phoneNumber = "19006079";

                    Intent it = new Intent(Intent.ACTION_CALL);
                    it.setData(Uri.parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(it);
                    break;
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void GetIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Bundle1");
        booking=(BookingDetails)bundle.getSerializable("booking1");
        tv_station1.setText(booking.getDepartureStation());
        tv_station2.setText(booking.getArrivalStation());
        tv_date1.setText(booking.getDateDeparture());
    }

    public void prepareData()
    {
        Async_GetListRouteDetails async_getListRouteDetails=new Async_GetListRouteDetails();
        async_getListRouteDetails.execute(booking.getDepartureStation()
                ,booking.getArrivalStation(),booking.getDateDeparture());
        adapter.notifyDataSetChanged();

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
                Toast.makeText(RouteActivity.this,"Sign Up Failed!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //bookingPerson=person;
                booking.setStrName(person.getStrName());
                booking.setStrEmail(person.getStrEmail());
                booking.setStrTel(person.getStrTel());
                booking.setStrPassword(person.getStrPassword());
                booking.setLoginStatus(person.isLoginStatus());
                LoginStatus(booking.isLoginStatus(),booking.getStrName());
                Intent intentToItinerary=new Intent(RouteActivity.this,SeatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);
                Toast.makeText(RouteActivity.this,booking.getStrName()+" Welcome you to here!",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RouteActivity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //bookingPerson=person;
                booking.setStrName(person.getStrName());
                booking.setStrEmail(person.getStrEmail());
                booking.setStrTel(person.getStrTel());
                booking.setStrPassword(person.getStrPassword());
                booking.setLoginStatus(person.isLoginStatus());
                LoginStatus(booking.isLoginStatus(),booking.getStrName());
                Snackbar snackbar=Snackbar.make(linearLayout,"Wellcome "+booking.getStrName(),Snackbar.LENGTH_LONG);
                snackbar.show();
                Intent intentToItinerary=new Intent(RouteActivity.this,SeatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);
                //Toast.makeText(RouteActivity.this,booking.getStrName()+" Login Success!",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class Async_GetListRouteDetails extends AsyncTask<String,Void,List<RouteDetails>>
    {
        @Override
        protected List<RouteDetails> doInBackground(String... params) {
            WebService ws=new WebService();
            return ws.GetListRouteDetails(params[0],params[1],params[2]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"LOADING...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<RouteDetails> routeDetailses) {
            super.onPostExecute(routeDetailses);
            if (routeDetailses.size()>0)
            {
                adapter = new RouteDetailsAdapter(RouteActivity.this, routeDetailses, R.layout.route_details_item);
                lvRouteDetails.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"ERORR",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
