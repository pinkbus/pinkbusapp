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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_profile)
    Toolbar mToolBar;
    @BindView(R.id.tvNumber)
    TextView tv_number_profile;
    @BindView(R.id.tvEmail)
    TextView tv_email_profile;
    @BindView(R.id.tvName)
    TextView tv_name_profile;
    @BindView(R.id.image_profile)
    ImageView image_profile;
    @BindView(R.id.btn_update_avt)
    Button btn_update_avt;

    List<TicketOrder> orderList=new ArrayList<>();
    BookingDetails booking=new BookingDetails();

    private static final int SELECT_PICTURE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_profile);
        ButterKnife.bind(this);
        GetIntent();
        SetUpToolBar();
        btn_update_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    // Set the image in ImageView
                    image_profile.setImageURI(selectedImageUri);
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        String imageEncoded=encodeImageToBase64(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String encodeImageToBase64(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void SetUpToolBar()
    {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolBar.setTitleTextColor(Color.WHITE);
    }

    private void GetIntent()
    {
        Bundle bundle=getIntent().getBundleExtra("DataIntent");
        booking= (BookingDetails) bundle.getSerializable("booking6");
        orderList=(List<TicketOrder>)bundle.getSerializable("orderList");

        if (!booking.isLoginStatus())
        {
            CustomDialogAlert();
        }
    }

    private void CustomDialogAlert()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MyProfileActivity.this);
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
        final Dialog dialog=new Dialog(MyProfileActivity.this);
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
        final Dialog dialog=new Dialog(MyProfileActivity.this);
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

    private void LoginStatus(boolean stt,String title)
    {
        if (stt)
        {
            mToolBar.setBackgroundColor(Color.rgb(255,69,0));
            mToolBar.setSubtitle("Welcome " + title);
            getSupportActionBar().setTitle(booking.getStrName());
            tv_email_profile.setText(booking.getStrEmail());
            tv_number_profile.setText(booking.getStrTel());
            tv_name_profile.setText(booking.getStrName());
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
                Toast.makeText(MyProfileActivity.this,"Sign Up Failed!!",Toast.LENGTH_SHORT).show();
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
                /*Intent intentToItinerary=new Intent(MyProfileActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Toast.makeText(MyProfileActivity.this,booking.getStrName()+" Welcome you to here!",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyProfileActivity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
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
                /*Intent intentToItinerary=new Intent(MyProfileActivity.this,ItineraryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("booking2",(Serializable)booking);
                intentToItinerary.putExtra("Bundle2",bundle);
                startActivity(intentToItinerary);*/
                Toast.makeText(MyProfileActivity.this,booking.getStrName()+" Login Success!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
