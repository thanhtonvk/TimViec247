package com.utehy.timviec247.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView rvCongTy;
    CompanyAdapter companyAdapter;
    List<Company> companyList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;

    List<Job> viecLamTotNhatList = new ArrayList<>();
    JobAdapter viecLamTotNhatAdapter;
    RecyclerView rvViecLamTotNhat;
    RecyclerView rvViecLamPhuHop;
    List<Job> vietLamPhuHopList = new ArrayList<>();
    JobAdapter viecLamPhuHopAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadCongTy();
        loadViecLamTotNhat();
        loadViecLamPhuHop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void init() {
        rvCongTy = getView().findViewById(R.id.rvCongty);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        rvCongTy.setLayoutManager(new GridLayoutManager(getContext(), 2));
        companyAdapter = new CompanyAdapter(getContext(), companyList);
        rvCongTy.setAdapter(companyAdapter);

        rvViecLamTotNhat = getActivity().findViewById(R.id.rvViecLamTotNhat);
        viecLamTotNhatAdapter = new JobAdapter(getContext(), viecLamTotNhatList);
        rvViecLamTotNhat.setAdapter(viecLamTotNhatAdapter);

        rvViecLamPhuHop = getActivity().findViewById(R.id.rvPhuHop);
        viecLamPhuHopAdapter = new JobAdapter(getContext(), vietLamPhuHopList);
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
                                            Log.e("TAG", "onDataChange: " + subChild);
                                            reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    //
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                                                    ) {

                                                        Job job = dataSnapshot.getValue(Job.class);
                                                        if (job.getKinhNghiem() >= namKN) {
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
                                                                    viecLamPhuHopAdapter.notifyDataSetChanged();
                                                                }
                                                            }


                                                        }

                                                    }
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

    private void loadViecLamTotNhat() {
        reference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viecLamTotNhatList.clear();
                for (DataSnapshot key : snapshot.getChildren()
                ) {
                    String subChild = key.getKey();
                    Log.e("TAG", "onDataChange: " + subChild);
                    reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Job job = dataSnapshot.getValue(Job.class);
                                viecLamTotNhatList.add(job);
                                viecLamTotNhatAdapter.notifyDataSetChanged();
                            }
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

    private void loadCongTy() {
        reference.child("CongTy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                companyList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Company company = ds.getValue(Company.class);
                    companyList.add(company);
                    companyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}