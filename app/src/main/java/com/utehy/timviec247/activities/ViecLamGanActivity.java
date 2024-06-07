package com.utehy.timviec247.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Objects;

public class ViecLamGanActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView rvViecLamGan;
    List<Job> vietLamGanList = new ArrayList<>();
    JobAdapter viecLamGanAdapter;
    int kc = 10;
    EditText edtTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viec_lam_gan);
        init();
        loadViecLamGan();
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = edtTimKiem.getText().toString();
                    if (!key.trim().isEmpty()) {
                        kc = Integer.parseInt(key);
                        loadViecLamGan();
                    }
                }
                return false;
            }
        });
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        rvViecLamGan = findViewById(R.id.rvCongViec);
        viecLamGanAdapter = new JobAdapter(ViecLamGanActivity.this, vietLamGanList, false);
        rvViecLamGan.setAdapter(viecLamGanAdapter);
        edtTimKiem = findViewById(R.id.edtTimKiem);
    }


    private void loadViecLamGan() {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(this));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Common.location = location;
                            reference.child("Jobs").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot key : snapshot.getChildren()
                                    ) {
                                        String subChild = key.getKey();
                                        reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                vietLamGanList.clear();
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                                                ) {
                                                    Job job = dataSnapshot.getValue(Job.class);
                                                    try {
                                                        if (job != null && Common.location != null) {
                                                            double khoangCach = Common.doKhoangCach(job.getLat(), job.getLng(), Common.location.getLatitude(), Common.location.getLongitude());
                                                            if (khoangCach <= kc * 1000) {
                                                                vietLamGanList.add(job);
                                                            }
                                                        }


                                                    } catch (Exception e) {
                                                        return;
                                                    }


                                                }
                                                viecLamGanAdapter.notifyDataSetChanged();
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
                });

    }
}