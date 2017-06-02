package com.coachstationmanger.victor.pinkbus;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.coachstationmanger.victor.pinkbus.models.BookingDetails;
import com.coachstationmanger.victor.pinkbus.models.Seat;
import com.coachstationmanger.victor.pinkbus.models.SeatDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatActivity extends AppCompatActivity {
    Button btn_seat1,btn_seat2,btn_seat3,btn_seat4,btn_seat5,btn_seat6,btn_seat7,btn_seat8,
            btn_seat9,btn_seat10,btn_seat11,btn_seat12,btn_seat13,btn_seat14,btn_seat15,btn_seat16,
    btn_seat17,btn_seat18,btn_seat19,btn_seat20;
    Button listButtonSeat[]=new Button[20];

    public Button[] getListButtonSeat() {
        return listButtonSeat;
    }

    public void setListButtonSeat(Button[] listButtonSeat) {
        this.listButtonSeat = listButtonSeat;
    }
    //
    @BindView(R.id.toolbar_seat)
    Toolbar mToolBar;
    @BindView(R.id.tv_count_seat)
    TextView tv_count_seat;
    @BindView(R.id.tv_count_total)
    TextView tv_count_total;
    @BindView(R.id.btn_next)
    ImageButton btn_next;
    Spinner spn_dropping;
    ArrayAdapter<String> arrayAdapter;
    Spinner spn_boarding;
    TextView tv_dialog_cancel;
    TextView tv_dialog_submit;
    TextView tv_station1;
    TextView tv_station2;
    Toolbar mToolbar_dialog_seat;
    int int_count_seat=0;
    int int_amount=0;
    List<String> listBoarding=new ArrayList<>();
    List<String>listDropping=new ArrayList<>();
    ViewPager pager;
    TabLayout tabLayout;
    RadioButton rd_me;
    RadioButton rd_other;
    //
    EditText dialog_edt_passenger;
    EditText dialog_edt_tel_of_passenger;
    TextInputLayout inputLayoutName;
    TextInputLayout inputLayoutTel;
    //
    BookingDetails booking=new BookingDetails();
    List<Seat> seatList=new ArrayList<>();//Danh sach ghe lay tu csdl
    Seat seat=new Seat();
    List<SeatDetails> seatDetailsList=new ArrayList<>();//Ds ghe ngoi cua hanh khach mua ve
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        ButterKnife.bind(this);
        //
        GetIntent();
        SetUpToolbar();
        //
        SetUpButton();
        SetSeatStatus();
        for (final Button b:getListButtonSeat())
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog(b);
                }
            });
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking.setList(seatDetailsList);
                Intent intentToBooking =new Intent(SeatActivity.this,BookingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking4",(Serializable) booking);
                intentToBooking.putExtra("Bundle4",bundle);
                startActivity(intentToBooking);
            }
        });
    }

    private void SetUpSpinner(Dialog dialog)
    {
        spn_boarding=(Spinner)dialog.findViewById(R.id.spn_boarding);
        arrayAdapter=new ArrayAdapter<>(SeatActivity.this,android.R.layout.simple_spinner_item,listBoarding);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_boarding.setAdapter(arrayAdapter);
        spn_boarding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn_boarding.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_dropping=(Spinner)dialog.findViewById(R.id.spn_dropping);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listDropping);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_dropping.setAdapter(arrayAdapter);
        spn_dropping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn_dropping.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SetUpButton()
    {
        btn_seat1=(Button) findViewById(R.id.btn_seat1);

        btn_seat2=(Button)findViewById(R.id.btn_seat2);

        btn_seat3=(Button)findViewById(R.id.btn_seat3);

        btn_seat4=(Button)findViewById(R.id.btn_seat4);

        btn_seat5 =(Button)findViewById(R.id.btn_seat5);

        btn_seat6=(Button)findViewById(R.id.btn_seat6);

        btn_seat7=(Button)findViewById(R.id.btn_seat7);

        btn_seat8=(Button)findViewById(R.id.btn_seat8);

        btn_seat9=(Button)findViewById(R.id.btn_seat9);

        btn_seat10=(Button)findViewById(R.id.btn_seat10);

        btn_seat11 =(Button)findViewById(R.id.btn_seat11);

        btn_seat12=(Button)findViewById(R.id.btn_seat12);

        btn_seat13 =(Button)findViewById(R.id.btn_seat13);

        btn_seat14 =(Button)findViewById(R.id.btn_seat14);

        btn_seat15 =(Button)findViewById(R.id.btn_seat15);

        btn_seat16 =(Button)findViewById(R.id.btn_seat16);
        btn_seat17=(Button)findViewById(R.id.btn_seat17);
        btn_seat18=(Button)findViewById(R.id.btn_seat18);
        btn_seat19=(Button)findViewById(R.id.btn_seat19);
        btn_seat20=(Button)findViewById(R.id.btn_seat20);
        Button[] button=new Button[]{btn_seat1,btn_seat2,btn_seat3,btn_seat4,btn_seat5,btn_seat6,
        btn_seat7,btn_seat8,btn_seat9,btn_seat10,btn_seat11,btn_seat12,btn_seat13,btn_seat14,btn_seat15,
        btn_seat16,btn_seat17,btn_seat18,btn_seat19,btn_seat20};
        setListButtonSeat(button);
    }

    public void SetSeatStatus()
    {
        int id=0;
        //Seat seat=new Seat();
        for (Button b:getListButtonSeat())
        {
            id=Integer.valueOf(b.getText().toString());
            for (Seat s:seatList)
            {
                if (id==Integer.valueOf(s.getSeatID()))
                {
                    b.setBackground(getDrawable(R.drawable.selected_seat_color));
                    b.setEnabled(false);
                    break;
                }
            }
        }
    }

    public void GetIntent()
    {
        Bundle bundle=this.getIntent().getBundleExtra("Bundle2");
        booking=(BookingDetails)bundle.getSerializable("booking2");
        listBoarding=booking.getListBoarding();
        listDropping.addAll(booking.getListDropping());
        seatList.addAll(booking.getSeatList());
    }

    private void SetUpToolbar()
    {
        mToolBar.setLogo(R.drawable.ic_directions_car_white_12dp);
        mToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle(seatList.get(0).getStLicense_Plate());
        Drawable drawable= ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_priority_high_white_12dp);
        mToolBar.setOverflowIcon(drawable);
        if (booking.isLoginStatus())
        {
            mToolBar.setBackgroundColor(Color.rgb(255,69,0));
            mToolBar.setSubtitle("Welcome " + booking.getStrName());
            mToolBar.setSubtitleTextColor(Color.WHITE);
        }
    }

    public void CustomDialog(final Button button)
    {
        //Khoi tao mot dialog
        final Dialog dialog=new Dialog(SeatActivity.this);
        dialog.setContentView(R.layout.dialog_seat);//Gan dialog vao giao dien
        //Anh xa cach control tu giao dien dialog
        mToolbar_dialog_seat=(Toolbar)dialog.findViewById(R.id.dialog_toolbar_seat);
        setSupportActionBar(mToolbar_dialog_seat);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_dialog_cancel=(TextView)mToolbar_dialog_seat.findViewById(R.id.tv_dialog_cancel);
        tv_dialog_submit=(TextView)mToolbar_dialog_seat.findViewById(R.id.tv_dialog_submit);
        final TextView dialog_tv_seatID=(TextView)dialog.findViewById(R.id.dialog_tv_seat_id);//Hien thi Seat ID
        dialog_edt_passenger=(EditText)dialog.findViewById(R.id.dialog_edt_name_of_passenger);//Nhap ten Hanh kach
        dialog_edt_tel_of_passenger=(EditText)dialog.findViewById(R.id.dialog_edt_tel_of_passenger);//Nhap so dien thoai cua hanh khach
        inputLayoutName=(TextInputLayout) dialog.findViewById(R.id.dialog_input_layout_name);
        inputLayoutTel=(TextInputLayout)dialog.findViewById(R.id.dialog_input_layout_tel);
        rd_me=(RadioButton)dialog.findViewById(R.id.rd_me);
        rd_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_me.setChecked(true);
                dialog_edt_passenger.setText(booking.getStrName());
                dialog_edt_passenger.setEnabled(false);
                dialog_edt_tel_of_passenger.setEnabled(false);
                dialog_edt_tel_of_passenger.setText(booking.getStrTel());
            }
        });
        rd_other=(RadioButton)dialog.findViewById(R.id.rd_other);
        rd_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_edt_passenger.setEnabled(false);
                dialog_edt_tel_of_passenger.setEnabled(false);
            }
        });
        SetUpSpinner(dialog);
        dialog.show();//Hien thi dialog len UI
        //
        dialog_tv_seatID.setText(button.getText());
        for (int i=0; i<seatDetailsList.size();i++)
        {
            if (seatDetailsList.get(i).getSeatID().compareTo(button.getText().toString())==0)
            {
                tv_dialog_submit.setEnabled(false);
                dialog_edt_passenger.setText(seatDetailsList.get(i).getPassengerName());
                dialog_edt_passenger.setEnabled(false);
                dialog_edt_tel_of_passenger.setText(seatDetailsList.get(i).getTelOfPassenger());
                dialog_edt_tel_of_passenger.setEnabled(false);
            }
        }
        tv_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<seatDetailsList.size();i++)
                {
                    if(seatDetailsList.get(i).getSeatID().compareTo(button.getText().toString())==0)
                    {
                        seatDetailsList.remove(i);
                    }
                }
                if((int_amount<=0)||(int_count_seat<=0))
                {
                    tv_count_seat.setText(String.valueOf(int_count_seat));
                    tv_count_total.setText(String.valueOf(int_amount));
                }
                else {
                    int_count_seat=int_count_seat-1;
                    int_amount=int_amount-booking.getRoute_Price();
                    tv_count_seat.setText(String.valueOf(int_count_seat));
                    tv_count_total.setText(String.valueOf(int_amount));
                }
                button.setBackground(getDrawable(R.drawable.available_seat));
                dialog.dismiss();
            }
        });

        tv_dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_edt_passenger.getText().toString().compareTo("")>0)
                {
                    SeatDetails s=new SeatDetails();
                    s.setPassengerName(dialog_edt_passenger.getText().toString());
                    s.setTelOfPassenger(dialog_edt_tel_of_passenger.getText().toString());
                    s.setBoardingLocation(spn_boarding.getSelectedItem().toString());
                    s.setDroppingLocation(spn_dropping.getSelectedItem().toString());
                    s.setSeatID(dialog_tv_seatID.getText().toString());
                    seatDetailsList.add(s);
                    button.setBackgroundColor(Color.rgb(220,20,60));
                    int_count_seat=int_count_seat+1;
                    int_amount=int_amount+booking.getRoute_Price();
                    tv_count_seat.setText(String.valueOf(int_count_seat));
                    tv_count_total.setText(String.valueOf(int_amount));
                    dialog.dismiss();
                }
            }
        });
    }

}
