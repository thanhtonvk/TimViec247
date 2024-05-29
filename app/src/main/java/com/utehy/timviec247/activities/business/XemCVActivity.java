package com.utehy.timviec247.activities.business;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.utils.Common;

public class XemCVActivity extends AppCompatActivity {
    WebView wvCV;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xem_cvactivity);
        init();
        loadData();
    }

    private void init() {
        wvCV = findViewById(R.id.wvCV);
        database = FirebaseDatabase.getInstance();
    }

    private void loadData() {
        database.getReference("CV").child(Common.ungTuyen.getIdTaiKhoanUngTuyen()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                if (link != null) {
                    wvCV.getSettings().setJavaScriptEnabled(true);
                    wvCV.getSettings().setBuiltInZoomControls(true);

                    // Load a web page
                    wvCV.loadUrl(link);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}