package com.utehy.timviec247;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.utehy.timviec247.activities.LoginActivity;
import com.utehy.timviec247.activities.ParentActivity;
import com.utehy.timviec247.activities.StartActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}