package com.utehy.timviec247.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.JobAdapter;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class ViecLamDaUngTuyenActivity extends AppCompatActivity {

    List<Job> viecLams = new ArrayList<>();
    JobAdapter jobAdapter;
    RecyclerView rvViecLam;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viec_lam_da_ung_tuyen);
        init();
        load();
    }

    private void load() {
        database = FirebaseDatabase.getInstance();
        database.getReference("CongViecDaUngTuyen").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viecLams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Job job = dataSnapshot.getValue(Job.class);
                    viecLams.add(job);
                }
                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        rvViecLam = findViewById(R.id.rvCongViec);
        jobAdapter = new JobAdapter(getApplicationContext(), viecLams);
        rvViecLam.setAdapter(jobAdapter);
    }
}