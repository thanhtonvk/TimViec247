package com.utehy.timviec247.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class CompanyDetailsActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView tvTenCongTy1, tvTenCongTy2, tvDiaChi, tvWebsite, tvGioiThieu;
    RecyclerView rvJob;
    JobAdapter jobAdapter;
    List<Job> jobList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        init();
        loadData();
        loadJob();
    }

    private void init() {
        imgLogo = findViewById(R.id.imgLogo);
        tvTenCongTy1 = findViewById(R.id.tvTenCongTy1);
        tvTenCongTy2 = findViewById(R.id.tvTenCongTy2);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvWebsite = findViewById(R.id.tvWebsite);
        tvGioiThieu = findViewById(R.id.tvGioiThieu);
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        Glide.with(CompanyDetailsActivity.this).load(Common.company.getLogo()).into(imgLogo);
        tvTenCongTy1.setText(Common.company.getTenCongTy());
        tvTenCongTy2.setText(Common.company.getTenCongTy());
        tvDiaChi.setText(Common.company.getDiaChi());
        tvWebsite.setText(Common.company.getWebsite());
        tvGioiThieu.setText(Common.company.getGioiThieu());
        rvJob = findViewById(R.id.rvJob);
        jobAdapter = new JobAdapter(this, jobList,false);
        rvJob.setAdapter(jobAdapter);

    }

    private void loadJob() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Jobs");
        reference.child(Common.company.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Job job = ds.getValue(Job.class);
                    jobList.add(job);
                }
                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}