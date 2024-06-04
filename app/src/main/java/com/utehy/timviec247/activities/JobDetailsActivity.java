package com.utehy.timviec247.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.utehy.timviec247.R;
import com.utehy.timviec247.models.UngTuyen;
import com.utehy.timviec247.utils.Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JobDetailsActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView tvViTriCongViec, tvTenCongTy, tvThongTinCongTy, tvMucLuong, tvDiaDiem, tvKinhNghiem, tvKinhNghiem2,
            tvHinhThuc, tvSoLuong, tvGioiTinh, tvCapBac, tvThoiGian, tvMoTa, tvYeuCau, tvQuyenLoi, tvDiaDiemLamViec, tvThoiGianLamViec;
    Button btnUngTuyen;
    ImageButton btnLuu;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        init();
        if (Common.company != null) {
            loadData();
        }

        onClick();
    }

    private void init() {
        imgLogo = findViewById(R.id.imgLogo);
        tvViTriCongViec = findViewById(R.id.tvViTriCongViec);
        tvTenCongTy = findViewById(R.id.tvTenCongTy);
        tvThongTinCongTy = findViewById(R.id.tvThongTinCongTy);
        tvMucLuong = findViewById(R.id.tvMucLuong);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvKinhNghiem = findViewById(R.id.tvKinhNghiem);
        tvKinhNghiem2 = findViewById(R.id.tvKinhNghiem2);
        tvHinhThuc = findViewById(R.id.tvHinhThuc);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvCapBac = findViewById(R.id.tvCapBac);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvYeuCau = findViewById(R.id.tvYeuCau);
        tvQuyenLoi = findViewById(R.id.tvQuyenLoi);
        tvDiaDiemLamViec = findViewById(R.id.tvDiaDiemLamViec);
        tvThoiGianLamViec = findViewById(R.id.tvThoiGianLamViec);
        btnUngTuyen = findViewById(R.id.btnUngTuyen);
        btnLuu = findViewById(R.id.btnLuu);
        database = FirebaseDatabase.getInstance();
    }

    private void onClick() {
        tvThongTinCongTy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CompanyDetailsActivity.class));
            }
        });
        btnUngTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ungTuyenCV();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuCV();
            }
        });
    }

    private void luuCV() {
        String id = String.valueOf(new Random().nextInt());
        database.getReference("LuuCV").child(Common.account.getId()).child(Common.job.getId()).setValue(Common.job).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Lưu công việc hành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ungTuyenCV() {
        UngTuyen ungTuyen = new UngTuyen();
        ungTuyen.setId(Common.job.getId());
        ungTuyen.setIdTaiKhoanUngTuyen(Common.account.getId());
        ungTuyen.setIdCongTy(Common.company.getId());
        ungTuyen.setIdCongViec(Common.job.getId());
        ungTuyen.setTrangThai(0);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String thoiGian = dateFormat.format(date);
        ungTuyen.setThoiGian(thoiGian);
        database.getReference("UngTuyen").child(ungTuyen.getIdCongTy()).child(ungTuyen.getId()).setValue(ungTuyen).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addCongViecUngTuyen();
                    Toast.makeText(getApplicationContext(), "Chúc mừng bạn ứng tuyển thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addCongViecUngTuyen() {
        database.getReference("CongViecDaUngTuyen").child(Common.account.getId()).child(Common.job.getId()).setValue(Common.job);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        Glide.with(JobDetailsActivity.this).load(Common.company.getLogo()).into(imgLogo);
        tvViTriCongViec.setText(Common.job.getViTri());
        tvTenCongTy.setText(Common.company.getTenCongTy());
        tvMucLuong.setText(Common.job.getLuongMin() + "-" + Common.job.getLuongMax() + " triệu");
        tvDiaDiem.setText(Common.job.getDiaChi());
        tvKinhNghiem.setText(Common.job.getKinhNghiem() + " năm");
        tvKinhNghiem2.setText(Common.job.getKinhNghiem() + " năm");
        tvHinhThuc.setText(Common.job.getHinhThuc());
        tvSoLuong.setText(Common.job.getSoLuong() + " người");
        tvGioiTinh.setText(Common.job.getGioiTinh());
        tvCapBac.setText(Common.job.getCapBac());
        tvThoiGian.setText(Common.job.getThoiGian() + " ngày");
        tvMoTa.setText(Common.job.getMoTa());
        tvYeuCau.setText(Common.job.getYeuCau());
        tvQuyenLoi.setText(Common.job.getQuyenLoi());
        tvDiaDiemLamViec.setText(Common.job.getDiaChi());
        tvThoiGianLamViec.setText(Common.job.getThoiGianLamViec());

    }

}