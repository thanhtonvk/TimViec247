package com.utehy.timviec247.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.CompanyAdapter;
import com.utehy.timviec247.adapters.JobAdapter;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class TimKiemActivity extends AppCompatActivity {
    RecyclerView rvCongViec;
    FirebaseDatabase database;
    DatabaseReference reference;

    List<Job> congViecs = new ArrayList<>();
    JobAdapter congViecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        init();
        loadViecLam();
    }

    private void loadViecLam() {
        reference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                congViecs.clear();
                for (DataSnapshot key : snapshot.getChildren()
                ) {
                    String subChild = key.getKey();
                    reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Job job = dataSnapshot.getValue(Job.class);
                                assert job != null;
                                if (Common.xoaDauTiengViet(job.getViTri()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getMoTa()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getYeuCau()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getCapBac()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(String.valueOf(job.getLuongMin())).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(String.valueOf(job.getLuongMax())).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getDiaChi()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(String.valueOf(job.getKinhNghiem())).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getGioiTinh()).toLowerCase().contains(Common.timKiem.toLowerCase())
                                        || Common.xoaDauTiengViet(job.getHinhThuc()).toLowerCase().contains(Common.timKiem.toLowerCase())) {
                                    congViecs.add(job);
                                }

                            }
                            congViecAdapter.notifyDataSetChanged();
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

    private void init() {
        rvCongViec = findViewById(R.id.rvCongViec);
        congViecAdapter = new JobAdapter(this, congViecs,false);
        rvCongViec.setAdapter(congViecAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }
}