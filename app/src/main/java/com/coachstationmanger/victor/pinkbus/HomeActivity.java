package com.coachstationmanger.victor.pinkbus;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coachstationmanger.victor.pinkbus.adapter.CompanyAdapter;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.BookingPerson;
import com.coachstationmanger.victor.pinkbus.models.Company;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    CoordinatorLayout coordinatorLayout;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private TextView tv_search,tv_sign_in,tv_sign_up;
    CompanyAdapter adapter;
    BookingDetails booking=new BookingDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);
        tv_search=(TextView)findViewById(R.id.tv_search);
        tv_sign_up=(TextView) findViewById(R.id.tv_sgn_up);
        tv_sign_in=(TextView)findViewById(R.id.tv_sgn_in);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        AsyncGetCompanies asyncGetCompanies=new AsyncGetCompanies();
        //asyncGetCompanies.execute();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                tv_search.startAnimation(fab_close);
                tv_sign_in.startAnimation(fab_close);
                tv_sign_up.startAnimation(fab_close);
                tv_search.setClickable(false);
                fab3.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                Log.d("Raj", "Fab 1");
                CustomDialogSignUp();
                break;
            case R.id.fab2:

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                tv_search.startAnimation(fab_close);
                tv_sign_in.startAnimation(fab_close);
                tv_sign_up.startAnimation(fab_close);
                tv_search.setClickable(false);
                fab3.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                Log.d("Raj", "Fab 2");
                Intent intentToMain2 =new Intent(getApplicationContext(),Main2Activity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking",booking);
                intentToMain2.putExtra("Data",bundle);
                startActivity(intentToMain2);
                break;
            case R.id.fab3:
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                tv_search.startAnimation(fab_close);
                tv_sign_in.startAnimation(fab_close);
                tv_sign_up.startAnimation(fab_close);
                tv_search.setClickable(false);
                fab3.setClickable(false);
                fab1.setClickable(false);
                fab2.setClickable(false);
                CustomDialogSignIn();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            tv_search.startAnimation(fab_close);
            tv_sign_in.startAnimation(fab_close);
            tv_sign_up.startAnimation(fab_close);
            tv_search.setClickable(false);
            fab3.setClickable(false);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            tv_search.setAnimation(fab_open);
            tv_sign_up.setAnimation(fab_open);
            tv_sign_in.setAnimation(fab_open);
            fab3.setClickable(true);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    private void LoginStatus(boolean stt, String title) {
        if (stt) {
            mToolBar.setBackgroundColor(Color.rgb(255, 69, 0));
            mToolBar.setSubtitle("Welcome " + title);
            mToolBar.setSubtitleTextColor(Color.WHITE);
        }
    }

    private void CustomDialogSignIn()
    {
        final Dialog dialog=new Dialog(HomeActivity.this);
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
        final Dialog dialog=new Dialog(HomeActivity.this);
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
                Toast.makeText(getApplicationContext(),"Sign Up Failed!!",Toast.LENGTH_SHORT).show();
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
                /*Intent intentToItinerary=new Intent(HomeActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Snackbar snackbar=Snackbar.make(coordinatorLayout,"Welcome you to here!",Snackbar.LENGTH_LONG);
                snackbar.show();
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
                Toast.makeText(getApplicationContext(),"Login Failed!!",Toast.LENGTH_SHORT).show();
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
                /*Intent intentToItinerary=new Intent(MyBookingActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Snackbar snackbar=Snackbar.make(coordinatorLayout,booking.getStrName()+" Login Success!",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    private class AsyncGetCompanies extends AsyncTask<Void,Void,List<Company>>{
        @Override
        protected List<Company> doInBackground(Void... params) {
            WebService ws=new WebService();
            return ws.GetCompanies();
        }

        @Override
        protected void onPostExecute(List<Company> companies) {
            super.onPostExecute(companies);
            if (companies.size()>0)
            {
                adapter=new CompanyAdapter(getApplicationContext(),companies);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}