package com.coachstationmanger.victor.pinkbus;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DateTimePatternGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.GridViewAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.City;
import com.coachstationmanger.victor.pinkbus.models.Ticket;
import com.coachstationmanger.victor.pinkbus.models.TicketOrder;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.btn_search_route)
        Button btn_next;
    @BindView(R.id.tv_day)
            TextView tv_day;
    @BindView(R.id.tv_month)
        TextView tv_month;
    @BindView(R.id.tv_year)
            TextView tv_year;
    @BindView(R.id.edt_from_city)
            TextView edt_from_city;
    @BindView(R.id.edt_to_city)
            TextView edt_to_city;
    RelativeLayout relativeLayout;
    List<String> list_from_city=new ArrayList<String>();
    List<String> list_to_city=new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    //
    BookingDetails booking=new BookingDetails();
    //
    Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        GetIntent();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        ButterKnife.bind(this);
        //
        ProcessUI();
        //
        SelectDateDeparture();


    }

    private void SetUpToolbar()
    {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setTitle("Pink Bus");
        setSupportActionBar(mToolBar);
        if (booking.isLoginStatus())
        {
            mToolBar.setBackgroundColor(Color.rgb(255,69,0));
            mToolBar.setSubtitle("Welcome " + booking.getStrName());
            mToolBar.setSubtitleTextColor(Color.WHITE);
        }
    }

    private void ProcessUI()
    {
        edt_from_city.setEnabled(true);
        edt_from_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CustomDialogFromCiTy();
            }
        });
        edt_to_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogToCiTy();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRoute=new Intent(getApplicationContext(),RouteActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking1",(Serializable) booking);
                intentToRoute.putExtra("Bundle1",bundle);
                startActivity(intentToRoute);
            }
        });
    }

    private void GetIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Data");
        booking=(BookingDetails)bundle.getSerializable("booking");
        SetUpToolbar();
        LoginStatus(booking.isLoginStatus(),booking.getStrName());
    }

    private void LoginStatus(boolean stt, String title) {
        if (stt) {
            mToolBar.setBackgroundColor(Color.rgb(255, 69, 0));
            mToolBar.setSubtitle("Welcome " + title);
            mToolBar.setSubtitleTextColor(Color.WHITE);
        }
    }

    private void CustomDialogToCiTy()
    {
        final Dialog dialog=new Dialog(Main2Activity.this);
        dialog.setContentView(R.layout.activity_search);
        GridView dialog_gv_city=(GridView)dialog.findViewById(R.id.gv_city);
        final List<City> cities=new ArrayList<>();
        final GridViewAdapter adapter;
        adapter=new GridViewAdapter(getApplicationContext(),cities,R.layout.city_item);
        dialog_gv_city.setAdapter(adapter);
        class Async_Get_To_City extends AsyncTask<String,Void,List<String>>
        {
            @Override
            protected List<String> doInBackground(String... params) {
                WebService ws=new WebService();
                return ws.GetListToCity(params[0]);
            }


            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);
                if (strings.size()>0)
                {
                    for(int i=0; i<strings.size();i++)
                    {
                        City city=new City(strings.get(i));
                        cities.add(city);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
        dialog.show();
        Async_Get_To_City async_get_to_city=new Async_Get_To_City();
        async_get_to_city.execute(edt_from_city.getText().toString());
        dialog_gv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city=(City)parent.getItemAtPosition(position);
                edt_to_city.setText(city.getCityName());
                booking.setArrivalStation(city.getCityName());
                dialog.dismiss();
            }
        });
    }

    private void CustomDialogFromCiTy()
    {
        final Dialog dialog=new Dialog(Main2Activity.this);
        dialog.setContentView(R.layout.activity_search);
        GridView dialog_gv_city=(GridView)dialog.findViewById(R.id.gv_city);
        final List<City> cities=new ArrayList<>();
        final GridViewAdapter adapter;
        adapter=new GridViewAdapter(getApplicationContext(),cities,R.layout.city_item);
        dialog_gv_city.setAdapter(adapter);
        class Async_Get_From_City extends AsyncTask<Void,Void,List<String>>
        {
            @Override
            protected List<String> doInBackground(Void... params) {
                WebService ws=new WebService();
                return ws.GetListFromCiTy();
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);
                if (strings.size()>0) {
                    for(int i=0; i<strings.size();i++)
                    {
                        City city=new City(strings.get(i));
                        cities.add(city);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
        dialog.show();
        Async_Get_From_City async_get_from_city=new Async_Get_From_City();
        async_get_from_city.execute();
        dialog_gv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city=(City)parent.getItemAtPosition(position);
                edt_from_city.setText(city.getCityName());
                booking.setDepartureStation(city.getCityName());
                dialog.dismiss();
            }
        });

    }

    private void SelectDateDeparture()
    {
        Calendar c = Calendar.getInstance();
        int   mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        tv_day.setText(ConvertDay(mDay));
        ConvertMonth(mMonth);
        tv_year.setText(String.valueOf(mYear));
        booking.setDateDeparture(tv_year.getText()+"-"+ConvertDay(mMonth+1)+"-"+tv_day.getText());
        relativeLayout=(RelativeLayout)findViewById(R.id.pk_date);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int   mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                tv_day.setText(ConvertDay(mDay));
                ConvertMonth(mMonth);
                tv_year.setText(String.valueOf(mYear));

                DatePickerDialog dialog=new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tv_day.setText(ConvertDay(dayOfMonth));
                        ConvertMonth(month);
                        tv_year.setText(String.valueOf(year));
                        booking.setDateDeparture(tv_year.getText()+"-"+ConvertDay(mMonth+1)+"-"+tv_day.getText());
                    }
                },mYear,mMonth,mDay);
                dialog.show();
            }
        });
    }

    private void ConvertMonth(int month)
    {
        switch (month+1)
        {
            case 1: tv_month.setText("January");
                break;
            case 2: tv_month.setText("February");
                break;
            case 3: tv_month.setText("March");
                break;
            case 4: tv_month.setText("April");
                break;
            case 5: tv_month.setText("May");
                break;
            case 6: tv_month.setText("June");
                break;
            case 7: tv_month.setText("July");
                break;
            case 8: tv_month.setText("August");
                break;
            case 9: tv_month.setText("September");
                break;
            case 10: tv_month.setText("October");
                break;
            case 11: tv_month.setText("November");
                break;
            case 12: tv_month.setText("December");
                break;
        }
    }

    private String ConvertDay(int day)
    {
        if(day<10)
        {
            return "0"+String.valueOf(day);
        }
        else
        {
            return String.valueOf(day);
        }
    }

    //Danh sach fro city

    //Danh sach to city



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent_toMain2=new Intent(Main2Activity.this,Main2Activity.class);
            startActivity(intent_toMain2);
            // Handle the camera action
        } else if (id == R.id.nav_my_booking) {
            List<TicketOrder> orders=new ArrayList<>();
            Intent intent_toMy_Booking=new Intent(getApplicationContext(),MyBookingActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("booking6",booking);
            bundle.putString("order_id", "ORDER73");
            intent_toMy_Booking.putExtra("DataIntent",bundle);
            startActivity(intent_toMy_Booking);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
