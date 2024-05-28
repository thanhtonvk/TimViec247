package com.utehy.timviec247.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utehy.timviec247.R;
import com.utehy.timviec247.utils.Common;

public class JobDetailsActivity extends AppCompatActivity {
    ImageView imgLogo;
    TextView tvViTriCongViec, tvTenCongTy, tvThongTinCongTy, tvMucLuong, tvDiaDiem, tvKinhNghiem, tvKinhNghiem2,
            tvHinhThuc, tvSoLuong, tvGioiTinh, tvCapBac, tvThoiGian, tvMoTa, tvYeuCau, tvQuyenLoi, tvDiaDiemLamViec, tvThoiGianLamViec;
    Button btnUngTuyen;
    ImageButton btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        init();
        loadData();
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
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        Glide.with(JobDetailsActivity.this).load(Common.company.getLogo()).into(imgLogo);
        tvViTriCongViec.setText(Common.job.getViTri());
        tvTenCongTy.setText(Common.company.getTenCongTy());
//        tvThongTinCongTy.setText(Common.company.getThongTinCongTy());
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