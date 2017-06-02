package com.coachstationmanger.victor.pinkbus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.fragment.BunkAFragment;
import com.coachstationmanger.victor.pinkbus.fragment.BunkBFragment;
import com.coachstationmanger.victor.pinkbus.fragment.OneFragment;
import com.coachstationmanger.victor.pinkbus.fragment.TwoFragment;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.Seat;
import com.coachstationmanger.victor.pinkbus.models.SeatDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SleeperActivity extends AppCompatActivity implements
        BunkAFragment.OnBunkAFragmentInteractionListener,BunkBFragment.OnFragmentInteractionListener {

    private ViewPager pager;
    private TabLayout tabLayout;
    @BindView(R.id.toolbar_sleeper)
    Toolbar mToolbar;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_total_seat)
            TextView tv_total_seat;
    @BindView(R.id.btn_next)
    ImageButton btn_next;
    BookingDetails booking=new BookingDetails();
    List<Seat> seatList=new ArrayList<>();//Danh sach ghe lay tu csdl
    Seat seat=new Seat();
    List<SeatDetails> seatDetailsList=new ArrayList<>();//Ds ghe ngoi cua hanh khach mua ve
    String seat_id="";
    int price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeper);
        ButterKnife.bind(this);
        GetIntent();
        addControl();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking.setList(seatDetailsList);
                Intent intentToBooking =new Intent(SleeperActivity.this,BookingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking4",(Serializable) booking);
                intentToBooking.putExtra("Bundle4",bundle);
                startActivity(intentToBooking);
            }
        });
    }


    public void SendToFragment(Fragment fragment,List<Seat> list)
    {
        Bundle arg=new Bundle();
        arg.putSerializable("list",(ArrayList<Seat>)list);
        fragment.setArguments(arg);
    }
    public void GetIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Bundle3");
        booking=(BookingDetails)bundle.getSerializable("booking3");
        seatList.addAll(booking.getSeatList());
        //Set up toolbar
        mToolbar.setLogo(R.drawable.ic_directions_car_white_12dp);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(seatList.get(0).getStLicense_Plate());
        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_priority_high_white_12dp);
        mToolbar.setOverflowIcon(drawable);
        if (booking.isLoginStatus())
        {
            mToolbar.setBackgroundColor(Color.rgb(255,69,0));
            mToolbar.setSubtitle("Welcome " + booking.getStrName());
            mToolbar.setSubtitleTextColor(Color.WHITE);
        }
    }
    private void addControl() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        SleeperActivity.ViewPagerAdapter adapter = new SleeperActivity.ViewPagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onFragmentInteraction(SeatDetails s) {
        if (s!=null)
        {
            seatDetailsList.add(s);
            seat_id=seat_id+s.getSeatID()+" ";
            price=price+booking.getRoute_Price();
            tv_total_price.setText(String.valueOf(price));
            tv_total_seat.setText(seat_id);
        }
    }

    @Override
    public void onBunkAFragmentInteraction(SeatDetails s) {
        if (s!=null)
        {
            seatDetailsList.add(s);
            seat_id=seat_id+s.getSeatID()+" ";
            price=price+booking.getRoute_Price();
            tv_total_price.setText(String.valueOf(price));
            tv_total_seat.setText(seat_id);
        }
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position) {
                case 0:
                    fragment=new BunkAFragment();
                    SendToFragment(fragment,seatList);
                    break;
                case 1:
                    fragment=new BunkBFragment();
                    SendToFragment(fragment,seatList);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position){
                case 0:
                    title = "Bunk A";
                    break;
                case 1:
                    title = "Bunk B";
                    break;
            }
            return title;
        }
    }
}
