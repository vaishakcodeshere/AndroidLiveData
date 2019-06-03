package com.architecture.androidlivedata.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architecture.androidlivedata.R;
import com.architecture.androidlivedata.model.UserData;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.FavViewHolder> {

    private ArrayList<UserData> data ;

    public EventAdapter(ArrayList<UserData> data) {
        this.data = data;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
        return new FavViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        UserData favourites = data.get(position);
        holder.username.setText("Name: " + favourites.mName);
        holder.profession.setText("Profession: " + favourites.mProfession);
        holder.company.setText("Company: " + favourites.mCompany);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {

        TextView username, profession, company;

        FavViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profession = itemView.findViewById(R.id.profession);
            company = itemView.findViewById(R.id.company);
        }
    }
}