package com.utehy.timviec247.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.TimKiemActivity;
import com.utehy.timviec247.activities.ViecLamPhuHopActivity;
import com.utehy.timviec247.activities.ViecLamTotNhatActivity;
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
    EditText edtTimKiem;
    TextView txtViecLamPhuHop, txtViecLamTotNhat, txtViecLamGan;


    RecyclerView rvViecLamGan;
    JobAdapter viecLamGanAdapter;
    List<Job> viectLamGanList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadCongTy();
        loadViecLamTotNhat();
        loadViecLamPhuHop();
        timKiem();
        onClick();
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
        viecLamTotNhatAdapter = new JobAdapter(getContext(), viecLamTotNhatList, false);
        rvViecLamTotNhat.setAdapter(viecLamTotNhatAdapter);

        rvViecLamPhuHop = getActivity().findViewById(R.id.rvPhuHop);
        viecLamPhuHopAdapter = new JobAdapter(getContext(), vietLamPhuHopList, false);
        rvViecLamPhuHop.setAdapter(viecLamPhuHopAdapter);


        rvViecLamGan = getActivity().findViewById(R.id.rvViecLamGan);
        viecLamGanAdapter = new JobAdapter(getContext(), viectLamGanList, false);
        rvViecLamGan.setAdapter(viecLamGanAdapter);
        edtTimKiem = getActivity().findViewById(R.id.edtTimKiem);


        txtViecLamPhuHop = getActivity().findViewById(R.id.txtViecLamPhuHop);
        txtViecLamTotNhat = getActivity().findViewById(R.id.txtViecLamTotNhat);
        txtViecLamGan = getActivity().findViewById(R.id.txtViecLamGan);
    }

    private void onClick() {
        txtViecLamPhuHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViecLamPhuHopActivity.class));
            }
        });
        txtViecLamTotNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViecLamTotNhatActivity.class));
            }
        });
    }

    private void timKiem() {
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String key = edtTimKiem.getText().toString();
                    if (!key.trim().isEmpty()) {
                        Common.timKiem = Common.xoaDauTiengViet(key);
                        startActivity(new Intent(getContext(), TimKiemActivity.class));
                    }
                }
                return false;
            }
        });
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

    private void loadViecLamGan() {
        reference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                viectLamGanList.clear();
                for (DataSnapshot key : snapshot.getChildren()
                ) {
                    String subChild = key.getKey();
                    reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int count = 0;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Job job = dataSnapshot.getValue(Job.class);
                                try {
                                    assert job != null;
                                    assert Common.location != null;
                                    if (Common.doKhoangCach(job.getLat(), job.getLng(), Common.location.getLatitude(), Common.location.getLongitude()) <= 10000) {
                                        viecLamTotNhatList.add(job);
                                        count += 1;
                                        if (count == 4) break;
                                    }

                                } catch (Exception e) {
                                    return;
                                }


                            }
                            viecLamTotNhatAdapter.notifyDataSetChanged();
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

    private void loadViecLamTotNhat() {
        reference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                viecLamTotNhatList.clear();
                for (DataSnapshot key : snapshot.getChildren()
                ) {
                    String subChild = key.getKey();
                    reference.child("Jobs").child(subChild).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int count = 0;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()
                            ) {
                                Job job = dataSnapshot.getValue(Job.class);
                                viecLamTotNhatList.add(job);
                                count += 1;
                                if (count == 4) break;
                            }
                            viecLamTotNhatAdapter.notifyDataSetChanged();
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
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                companyList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Company company = ds.getValue(Company.class);
                    companyList.add(company);
                    count += 1;
                    if (count == 4) break;

                }
                companyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}