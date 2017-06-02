package com.coachstationmanger.victor.pinkbus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.coachstationmanger.victor.pinkbus.adapter.GridViewAdapter;
import com.coachstationmanger.victor.pinkbus.models.City;
import com.coachstationmanger.victor.pinkbus.ws.WebService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.gv_city)
    GridView gv_city;
    List<City> cities=new ArrayList<>();
    GridViewAdapter adapter;
    City c=new City();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        //
        Intent intent=this.getIntent();
        //
        adapter=new GridViewAdapter(getApplicationContext(),cities,R.layout.city_item);
        gv_city.setAdapter(adapter);
        gv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City c=(City) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),c.getCityName(),Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {

        Intent intentToMain2=new Intent();
        intentToMain2.putExtra("from_city",c.getCityName());
        this.setResult(Activity.RESULT_OK,intentToMain2);
        super.finish();
    }


}
