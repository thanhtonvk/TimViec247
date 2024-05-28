package com.utehy.timviec247.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.utehy.timviec247.models.Company;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadCongTy();
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