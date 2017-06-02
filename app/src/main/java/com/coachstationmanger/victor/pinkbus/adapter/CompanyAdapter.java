package com.coachstationmanger.victor.pinkbus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coachstationmanger.victor.pinkbus.R;
import com.coachstationmanger.victor.pinkbus.models.Company;

import java.util.List;

/**
 * Created by Victor on 28/04/2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private Context mContext;
    private List<Company> mList;

    public CompanyAdapter(Context mContext, List<Company> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_card, parent, false);

        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompanyViewHolder holder, int position) {
        Company company=mList.get(position);
        holder.companyTel.setText(company.getCompanyTel());
        holder.companyName.setText(company.getCompanyName());
        //Bitmap bitmap = BitmapFactory.decodeByteArray(company.getCompanyPicture(), 0, company.getCompanyPicture().length);
        //holder.companyPicture.setImageBitmap(bitmap);
        //Glide.with(mContext).load(company.getCompanyPicture()).into(holder.companyPicture);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_company, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
   private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.book_now:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView companyPicture,overflow;
        TextView companyName,companyTel,companyAddress;
        private CompanyViewHolder(View itemView) {
            super(itemView);
            companyPicture=(ImageView)itemView.findViewById(R.id.company_picture);
            companyName=(TextView)itemView.findViewById(R.id.name);
            companyTel=(TextView)itemView.findViewById(R.id.tel);
            companyAddress=(TextView)itemView.findViewById(R.id.address);
            overflow=(ImageView)itemView.findViewById(R.id.overflow);
        }
    }
}
