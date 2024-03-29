package com.architecture.androidlivedata.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.architecture.androidlivedata.R;
import com.architecture.androidlivedata.model.UserData;
import com.architecture.androidlivedata.viewmodel.EventViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton fab;
    EventViewModel eventViewModel;
    List<UserData> userData;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Observer<List<UserData>> dataObserver = new Observer<List<UserData>>() {
            @Override
            public void onChanged(@Nullable final List<UserData> updatedList) {

                Log.d("onChanged_ViewModel", "onChanged: " + updatedList.size());

                if (userData == null) {

                    userData = updatedList;
                    eventAdapter = new EventAdapter();
                    recyclerView.setAdapter(eventAdapter);

                } else {

                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                        @Override
                        public int getOldListSize() {
                            return userData.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return updatedList.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return userData.get(oldItemPosition).mId ==
                                    updatedList.get(newItemPosition).mId;
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            UserData oldFav = userData.get(oldItemPosition);
                            UserData newFav = updatedList.get(newItemPosition);
                            return oldFav.equals(newFav);
                        }
                    });
                    result.dispatchUpdatesTo(eventAdapter);
                    userData = updatedList;
                }

            }
        };


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog, null);

                final EditText name = dialogView.findViewById(R.id.name);
                final EditText profession = dialogView.findViewById(R.id.profession);
                final EditText company = dialogView.findViewById(R.id.company);
                Button submit = dialogView.findViewById(R.id.submit);
                Button cancel = dialogView.findViewById(R.id.cancel);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(profession.getText()) || TextUtils.isEmpty(company.getText())) {
                            Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            eventViewModel.addFav(name.getText().toString(), profession.getText().toString(), company.getText().toString());
                            dialogBuilder.dismiss();
                        }


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });


                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

            }
        });

        eventViewModel.getFavs().observe(this, dataObserver);

    }



    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.FavViewHolder> {


        @Override
        public EventAdapter.FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
            return new EventAdapter.FavViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(EventAdapter.FavViewHolder holder, int position) {
            UserData favourites = userData.get(position);
            holder.username.setText("Name: " + favourites.mName);
            holder.profession.setText("Profession: " + favourites.mProfession);
            holder.company.setText("Company: " + favourites.mCompany);

        }

        @Override
        public int getItemCount() {
            return userData.size();
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
}
