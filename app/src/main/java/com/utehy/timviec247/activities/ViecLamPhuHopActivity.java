package com.utehy.timviec247.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.CompanyAdapter;
import com.utehy.timviec247.adapters.JobAdapter;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class ViecLamPhuHopActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView rvViecLamPhuHop;
    List<Job> vietLamPhuHopList = new ArrayList<>();
    JobAdapter viecLamPhuHopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viec_lam_phu_hop);
        init();
        loadViecLamPhuHop();
    }

    private void init() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        rvViecLamPhuHop = findViewById(R.id.rvCongViec);
        viecLamPhuHopAdapter = new JobAdapter(ViecLamPhuHopActivity.this, vietLamPhuHopList, false);
        rvViecLamPhuHop.setAdapter(viecLamPhuHopAdapter);

    }

    int namKN = 0;
    String[] congViecMonMuons = new String[0];
    String[] diaDiemMongMuons = new String[0];

    private void loadViecLamPhuHop() {
        //kiem tra nam kinh nghiem
        database.getReference("KinhNghiem").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String data = snapshot.getValue(String.class);
                if (data != null) {
                    namKN = Integer.parseInt(data.trim());
                }
                //kiem tra cong viec mong muon
                database.getReference("CongViecMongMuon").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String data = snapshot.getValue(String.class);
                        if (data != null) {
                            congViecMonMuons = data.split(",");
                        }
                        //kiem tra dia diem lam viec mong muon
                        database.getReference("DiaDiemMongMuon").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String data = snapshot.getValue(String.class);
                                if (data != null) {
                                    diaDiemMongMuons = data.split(",");
                                }
                                //lay ve danh sach congg viec
                                reference.child("Jobs").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        vietLamPhuHopList.clear();
                                        for (DataSnapshot key : snapshot.getChildren()
                                        ) {
                                            String subChild = key.getKey();
                                            reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                                                @SuppressLint("NotifyDataSetChanged")
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                                                    ) {

                                                        Job job = dataSnapshot.getValue(Job.class);
                                                        assert job != null;
                                                        if (job.getKinhNghiem() <= namKN) {
                                                            boolean isCongViec = false;
                                                            for (String congViec : congViecMonMuons
                                                            ) {
                                                                if (Common.xoaDauTiengViet(job.getViTri()).contains(Common.xoaDauTiengViet(congViec))) {
                                                                    isCongViec = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (isCongViec) {
                                                                boolean isDiaDiem = false;
                                                                for (String diaDiem : diaDiemMongMuons
                                                                ) {
                                                                    if (Common.xoaDauTiengViet(job.getDiaChi()).contains(Common.xoaDauTiengViet(diaDiem))) {
                                                                        isDiaDiem = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (isDiaDiem) {
                                                                    vietLamPhuHopList.add(job);
                                                                }
                                                            }
                                                        }

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

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}