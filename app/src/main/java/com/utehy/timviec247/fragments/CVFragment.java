package com.utehy.timviec247.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.utils.Common;
import com.utehy.timviec247.utils.EditextValidator;


public class CVFragment extends Fragment {

    public CVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button btnOK;
    EditText edtLink;
    WebView wvCV;
    FirebaseDatabase database;
    String linkCV = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
        load();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c_v, container, false);
    }

    private void init() {
        btnOK = getActivity().findViewById(R.id.btnOK);
        wvCV = getActivity().findViewById(R.id.wvCV);
        database = FirebaseDatabase.getInstance();
    }

    private void updateCongViec() {
        EditText editText = new EditText(getContext());
        editText.setText(linkCV);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Nhập link CV")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String link = editText.getText().toString();
                    database.getReference("CV").child(Common.account.getId()).setValue(link).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Thành công ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Thất bại ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void onClick() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCongViec();
            }
        });
    }

    private void load() {
        database.getReference("CV").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                if (link != null) {
                    wvCV.getSettings().setJavaScriptEnabled(true);
                    wvCV.getSettings().setBuiltInZoomControls(true);
                    // Load a web page
                    wvCV.loadUrl(link);
                    linkCV = link;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}