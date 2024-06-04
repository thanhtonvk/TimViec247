package com.utehy.timviec247.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.JobAdapter;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class ViecLamTotNhatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView rvViecLamPhuHop;
    List<Job> vietLamPhuHopList = new ArrayList<>();
    JobAdapter viecLamPhuHopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viec_lam_tot_nhat);
        init();
        loadViecLamTotNhat();
    }

    private void init() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        rvViecLamPhuHop = findViewById(R.id.rvCongViec);
        viecLamPhuHopAdapter = new JobAdapter(ViecLamTotNhatActivity.this, vietLamPhuHopList, false);
        rvViecLamPhuHop.setAdapter(viecLamPhuHopAdapter);

    }


    private void loadViecLamTotNhat() {
        reference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                vietLamPhuHopList.clear();
                for (DataSnapshot key : snapshot.getChildren()
                ) {
                    String subChild = key.getKey();
                    reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Job job = dataSnapshot.getValue(Job.class);
                                vietLamPhuHopList.add(job);
                            }
                            viecLamPhuHopAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}