package com.utehy.timviec247.fragments.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.business.AddJobActivity;
import com.utehy.timviec247.adapters.business.PostAdapter;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<Job> jobList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;

    private void init() {
        btnAdd = getView().findViewById(R.id.btnAdd);
        recyclerView = getView().findViewById(R.id.rvPost);
        postAdapter = new PostAdapter(getContext(), jobList);
        recyclerView.setAdapter(postAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Jobs");
    }

    private void loadData() {
        reference.child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Job job = dataSnapshot.getValue(Job.class);
                    jobList.add(job);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Common.job = null;
                startActivity(new Intent(getContext(), AddJobActivity.class));
            }
        });
    }
}