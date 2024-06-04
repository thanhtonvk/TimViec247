package com.utehy.timviec247.fragments.business;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.TrangThaiAdapter;
import com.utehy.timviec247.adapters.business.TrangThaiUngTuyenAdapter;
import com.utehy.timviec247.adapters.business.TuyenDungAdapter;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.models.UngTuyen;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class NotiBussFragment extends Fragment {


    public NotiBussFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase database;
    List<Job> viecLams = new ArrayList<>();
    TuyenDungAdapter tuyenDungAdapter;
    RecyclerView rvViecLam;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        load();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noti_buss, container, false);
    }

    private void load() {
        database.getReference("Jobs").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viecLams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Job job = dataSnapshot.getValue(Job.class);
                    viecLams.add(job);
                }
                tuyenDungAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        database.getReference("UngTuyen").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                viecLams.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()
//                ) {
//                    UngTuyen ungTuyen = dataSnapshot.getValue(UngTuyen.class);
//                    if (ungTuyen != null) {
//                        database.getReference("Jobs").child(Common.account.getId()).child(ungTuyen.getIdCongViec()).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Job job = snapshot.getValue(Job.class);
//                                viecLams.add(job);
//                                trangThaiAdapter.notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        rvViecLam = getView().findViewById(R.id.rvCongViec);
        tuyenDungAdapter = new TuyenDungAdapter(getContext(), viecLams);
        rvViecLam.setAdapter(tuyenDungAdapter);
    }
}