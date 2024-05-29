package com.utehy.timviec247.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.CapNhatThongTinActivity;
import com.utehy.timviec247.activities.LoginActivity;
import com.utehy.timviec247.activities.ViecLamDaLuuActivity;
import com.utehy.timviec247.activities.ViecLamDaUngTuyenActivity;
import com.utehy.timviec247.utils.Common;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClick();
        loadData();
    }

    ImageView imgAvatar;
    TextView tvHoTen, tvCapNhat, tvSuaKN, tvKN, tvSuaCV, tvCV1, tvCV2, tvCV3, tvSuaDiaDiem, tvDiadiem1, tvDiadiem2, tvDiadiem3, tvSoLuongUngTuyen, tvSoLuongLuu;
    Button btnDangXuat;
    FirebaseDatabase database;
    CardView cvUngTuyen, cvLuu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    private void onClick() {
        tvCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CapNhatThongTinActivity.class));
            }
        });
        tvSuaKN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKinhNghiem();
            }
        });
        tvSuaCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCongViec();
            }
        });
        tvSuaDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDiaDiem();
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        cvUngTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViecLamDaUngTuyenActivity.class));
            }
        });
        cvLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViecLamDaLuuActivity.class));
            }
        });
    }

    private void updateDiaDiem() {
        EditText editText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Nhập nơi làm việc")
                .setMessage("Mỗi địa điểm cách nhau bởi dấu phẩy")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String congViec = editText.getText().toString();
                    database.getReference("DiaDiemMongMuon").child(Common.account.getId()).setValue(congViec);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void updateKinhNghiem() {
        EditText editText = new EditText(getContext());
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Năm kinh  nghiệm ")
                .setMessage("Nhập năm kinh nghiệm")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String namKN = editText.getText().toString();
                    database.getReference("KinhNghiem").child(Common.account.getId()).setValue(namKN);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void updateCongViec() {
        EditText editText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Nhập công việc mong muốn")
                .setMessage("Mỗi công việc cách nhau bởi dấu phẩy")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String congViec = editText.getText().toString();
                    database.getReference("CongViecMongMuon").child(Common.account.getId()).setValue(congViec);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void loadData() {
        if (Common.thongTin != null) {
            Glide.with(getContext()).load(Common.thongTin.getHinhAnh()).into(imgAvatar);
        }
        tvHoTen.setText(Common.account.getFullName());
        database.getReference("DiaDiemMongMuon").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String diaDiem = snapshot.getValue(String.class);
                if (diaDiem != null) {
                    String[] arr = diaDiem.split(",");
                    if (arr.length > 0) {
                        tvDiadiem1.setText(arr[0]);
                    }
                    if (arr.length > 1) {
                        tvDiadiem2.setText(arr[1]);
                    }
                    if (arr.length > 2) {
                        tvDiadiem3.setText(arr[2]);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference("CongViecMongMuon").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String congViec = snapshot.getValue(String.class);
                if (congViec != null) {
                    String[] arr = congViec.split(",");
                    if (arr.length > 0) {
                        tvCV1.setText(arr[0]);
                    }
                    if (arr.length > 1) {
                        tvCV2.setText(arr[1]);
                    }
                    if (arr.length > 2) {
                        tvCV3.setText(arr[2]);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference("KinhNghiem").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namKN = snapshot.getValue(String.class);
                if (namKN != null) {
                    tvKN.setText(namKN + " năm");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init() {
        imgAvatar = getView().findViewById(R.id.imgAvatar);
        tvHoTen = getView().findViewById(R.id.tvHoTen);
        tvCapNhat = getView().findViewById(R.id.tvCapNhat);
        tvSuaKN = getView().findViewById(R.id.tvSuaKN);
        tvKN = getView().findViewById(R.id.tvKN);
        tvSuaCV = getView().findViewById(R.id.tvSuaCV);
        tvCV1 = getView().findViewById(R.id.tvCV1);
        tvCV2 = getView().findViewById(R.id.tvCV2);
        tvCV3 = getView().findViewById(R.id.tvCV3);
        tvSuaDiaDiem = getView().findViewById(R.id.tvSuaDiaDiem);
        tvDiadiem1 = getView().findViewById(R.id.tvDiadiem1);
        tvDiadiem2 = getView().findViewById(R.id.tvDiadiem2);
        tvDiadiem3 = getView().findViewById(R.id.tvDiadiem3);
        tvSoLuongUngTuyen = getView().findViewById(R.id.tvSoLuongUngTuyen);
        tvSoLuongLuu = getView().findViewById(R.id.tvSoLuongLuu);
        btnDangXuat = getView().findViewById(R.id.btnDangXuat);
        database = FirebaseDatabase.getInstance();
        cvLuu = getView().findViewById(R.id.cvViecDaLuu);
        cvUngTuyen = getView().findViewById(R.id.cvUngTuyen);

    }
}