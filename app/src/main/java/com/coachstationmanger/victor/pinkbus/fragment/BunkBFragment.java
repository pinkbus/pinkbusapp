package com.coachstationmanger.victor.pinkbus.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.Seat;
import com.coachstationmanger.victor.pinkbus.models.SeatDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BunkBFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BunkBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BunkBFragment extends Fragment {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16
            ,btn17,btn18,btn19,btn20;

    Button[] listButtonSeat=new Button[20];

    public void setListButtonSeat(Button[] listButtonSeat) {
        this.listButtonSeat = listButtonSeat;
    }

    public Button[] getListButtonSeat() {
        return listButtonSeat;
    }
    List<Seat> seatList=new ArrayList<>();
    List<SeatDetails> seatDetailsList=new ArrayList<>();//Ds ghe ngoi cua hanh khach mua ve

    private OnFragmentInteractionListener mListener;

    public BunkBFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BunkBFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BunkBFragment newInstance(String param1, String param2) {
        BunkBFragment fragment = new BunkBFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seatList = (List<Seat>) getArguments().getSerializable("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.bunk_item,container,false);
        SetUpButtonSeat(view);
        SetSeatStatus();
        for (final Button p:getListButtonSeat())
        {
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog(view,p);
                }
            });
        }
        return view;
    }

    private void SetUpButtonSeat(View view)
    {
        btn1=(Button) view.findViewById(R.id.sleeper_1);
        btn2=(Button)view.findViewById(R.id.sleeper_2);
        btn3=(Button)view.findViewById(R.id.sleeper_3);
        btn4=(Button)view.findViewById(R.id.sleeper_4);
        btn5=(Button)view.findViewById(R.id.sleeper_5);
        btn6=(Button)view.findViewById(R.id.sleeper_6);
        btn7=(Button)view.findViewById(R.id.sleeper_7);
        btn8=(Button)view.findViewById(R.id.sleeper_8);
        btn9=(Button)view.findViewById(R.id.sleeper_9);
        btn10=(Button)view.findViewById(R.id.sleeper_10);
        btn11=(Button)view.findViewById(R.id.sleeper_11);
        btn12=(Button)view.findViewById(R.id.sleeper_12);
        btn13=(Button)view.findViewById(R.id.sleeper_13);
        btn14=(Button)view.findViewById(R.id.sleeper_14);
        btn15=(Button)view.findViewById(R.id.sleeper_15);
        btn16=(Button)view.findViewById(R.id.sleeper_16);
        btn17=(Button)view.findViewById(R.id.sleeper_17);
        btn18=(Button)view.findViewById(R.id.sleeper_18);
        btn19=(Button)view.findViewById(R.id.sleeper_19);
        btn20=(Button)view.findViewById(R.id.sleeper_20);
        Button[] btnList= new Button[]{btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16
                ,btn17,btn18,btn19,btn20};;
        setListButtonSeat(btnList);
    }

    public void CustomDialog(final View view, final Button idSeat)
    {
        //Khoi tao mot dialog
        final Dialog dialog=new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_seat);//Gan dialog vao giao dien
        //Anh xa cach control tu giao dien dialog
        final TextView dialog_tv_seatID=(TextView)dialog.findViewById(R.id.dialog_tv_seat_id);//Hien thi Seat ID
        final EditText dialog_edt_passenger=(EditText)dialog.findViewById(R.id.dialog_edt_name_of_passenger);//Nhap ten Hanh kach
        final EditText dialog_edt_tel_of_passenger=(EditText)dialog.findViewById(R.id.dialog_edt_tel_of_passenger);//Nhap so dien thoai cua hanh khach
        dialog.show();//Hien thi dialog len UI
        //
        final String dialog_id_seat=idSeat.getText().toString()+"B";
        dialog_tv_seatID.setText(dialog_id_seat);
        for (int i=0; i<seatDetailsList.size();i++)
        {
            if (seatDetailsList.get(i).getSeatID().compareTo(dialog_id_seat)==0)
            {
                //dialog_btn_Select.setEnabled(false);
                dialog_edt_passenger.setText(seatDetailsList.get(i).getPassengerName());
                dialog_edt_passenger.setEnabled(false);
                dialog_edt_tel_of_passenger.setText(seatDetailsList.get(i).getTelOfPassenger());
                dialog_edt_tel_of_passenger.setEnabled(false);
            }
        }
        /*dialog_btn_Unselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<seatDetailsList.size();i++)
                {
                    if(seatDetailsList.get(i).getSeatId().compareTo(dialog_id_seat)<1)
                    {
                        seatDetailsList.remove(i);
                    }
                }
                idSeat.setBackgroundColor(Color.rgb(215,215,215));
                dialog.dismiss();
            }
        });*/

        /*dialog_btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_edt_passenger.getText().toString().compareTo("")>0)
                {
                    SeatDetails s=new SeatDetails(dialog_edt_passenger.getText().toString()
                            ,dialog_id_seat,dialog_edt_tel_of_passenger.getText().toString());
                    seatDetailsList.add(s);
                    onButtonPressed(s);
                    idSeat.setBackgroundColor(Color.rgb(220,20,60));
                    dialog.dismiss();
                }
            }
        });*/
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(SeatDetails seatDetails) {
        if (mListener != null) {
            mListener.onFragmentInteraction(seatDetails);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void SetSeatStatus()
    {
        Seat seat=new Seat();
        int btn_id;
        for (Button b:getListButtonSeat())
        {
            btn_id = Integer.valueOf(b.getText().toString());//id cua btn
            for (Seat s : seatList)
            {
                String seat_id=s.getSeatID();
                if (seat_id.length()==2)
                {
                    int id=Integer.valueOf(seat_id.substring(0,1));//id
                    String bunk=seat_id.substring(1);//bunk
                    if((bunk.compareTo("B")==0))
                    {
                        if (id==btn_id)
                        {
                            seat=s;
                            break;
                        }
                    }
                }
                else
                {
                    int id=Integer.valueOf(seat_id.substring(0,2));//id
                    String bunk=seat_id.substring(1,1);//bunk
                    if((bunk.compareTo("A")==0)&&(btn_id==id))
                    {
                        seat=s;
                        break;
                    }
                }
            }
            if(seat.getStatusSeat()==1)
            {
                b.setBackgroundColor(Color.rgb(25,25,112));
                b.setEnabled(false);
            }
        }

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(SeatDetails seatDetails);
    }
}
