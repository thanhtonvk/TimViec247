package com.utehy.timviec247.activities.business;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utehy.timviec247.R;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;
import com.utehy.timviec247.utils.EditextValidator;

import java.util.Random;

public class AddJobActivity extends AppCompatActivity {
    EditText edtViTri, edtLuongMin, edtLuongMax, edtDiaChi, edtKinhNghiem, edtHinhThuc, edtSoLuong, edtGioiTinh, edtCapBac, edtThoiGian, edtMoTa, edtYeuCau, edtQuyenLoi, edtThoiGianLamViec;
    Button btnAdd;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        init();
        onClick();
        if (Common.job != null) {
            loadData();
        }

    }

    private void loadData() {
        edtViTri.setText(Common.job.getViTri());
        edtLuongMin.setText(String.valueOf(Common.job.getLuongMin()));
        edtLuongMax.setText(String.valueOf(Common.job.getLuongMax()));
        edtDiaChi.setText(Common.job.getDiaChi());
        edtKinhNghiem.setText(String.valueOf(Common.job.getKinhNghiem()));
        edtHinhThuc.setText(Common.job.getHinhThuc());
        edtSoLuong.setText(String.valueOf(Common.job.getSoLuong()));
        edtGioiTinh.setText(Common.job.getGioiTinh());
        edtCapBac.setText(Common.job.getCapBac());
        edtThoiGian.setText(String.valueOf(Common.job.getThoiGian()));
        edtMoTa.setText(Common.job.getMoTa());
        edtYeuCau.setText(Common.job.getYeuCau());
        edtQuyenLoi.setText(Common.job.getQuyenLoi());
        edtThoiGianLamViec.setText(Common.job.getThoiGianLamViec());

    }

    private void onClick() {

        btnAdd.setOnClickListener(view -> {
                    boolean isValid = EditextValidator.validateAllEditTexts(edtViTri, edtLuongMin, edtLuongMax, edtDiaChi, edtKinhNghiem, edtHinhThuc, edtSoLuong, edtGioiTinh, edtCapBac, edtThoiGian, edtMoTa, edtYeuCau, edtQuyenLoi, edtThoiGianLamViec);
                    if (isValid) {
                        ProgressDialog dialog = new ProgressDialog(AddJobActivity.this);
                        dialog.setMessage("Loading...");
                        dialog.show();
                        String[] texts = EditextValidator.getTextFromEditTexts(edtViTri, edtLuongMin, edtLuongMax, edtDiaChi, edtKinhNghiem, edtHinhThuc, edtSoLuong, edtGioiTinh, edtCapBac, edtThoiGian, edtMoTa, edtYeuCau, edtQuyenLoi, edtThoiGianLamViec);
                        Job job = new Job();
                        job.setIdAccount(Common.account.getId());
                        if (Common.job != null) {
                            job.setId(Common.job.getId());
                        } else {
                            job.setId(String.valueOf(new Random().nextInt()));
                        }
                        job.setViTri(texts[0]);
                        job.setLuongMin(Integer.parseInt(texts[1]));
                        job.setLuongMax(Integer.parseInt(texts[2]));
                        job.setDiaChi(texts[3]);
                        job.setKinhNghiem(Integer.parseInt(texts[4]));
                        job.setHinhThuc(texts[5]);
                        job.setSoLuong(Integer.parseInt(texts[6]));
                        job.setGioiTinh(texts[7]);
                        job.setCapBac(texts[8]);
                        job.setThoiGian(Integer.parseInt(texts[9]));
                        job.setMoTa(texts[10]);
                        job.setYeuCau(texts[11]);
                        job.setQuyenLoi(texts[12]);
                        job.setThoiGianLamViec(texts[13]);
                        reference.child(Common.user.getUid()).child(job.getId()).setValue(job).addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(AddJobActivity.this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show();
                                Common.job = null;
                                finish();
                            } else {
                                Toast.makeText(AddJobActivity.this, "Cập nhật  công việc thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(e -> {
                            dialog.dismiss();
                            Toast.makeText(AddJobActivity.this, "Cập nhật  công việc thất bại", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
                    }
                }
        );
    }

    private void init() {
        edtViTri = findViewById(R.id.edtViTri);
        edtLuongMin = findViewById(R.id.edtLuongMin);
        edtLuongMax = findViewById(R.id.edtLuongMax);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtKinhNghiem = findViewById(R.id.edtKinhNghiem);
        edtHinhThuc = findViewById(R.id.edtHinhThuc);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtGioiTinh = findViewById(R.id.edtGioiTinh);
        edtCapBac = findViewById(R.id.edtCapBac);
        edtThoiGian = findViewById(R.id.edtThoiGian);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtYeuCau = findViewById(R.id.edtYeuCau);
        edtQuyenLoi = findViewById(R.id.edtQuyenLoi);
        edtThoiGianLamViec = findViewById(R.id.edtThoiGianLamViec);
        btnAdd = findViewById(R.id.btnAdd);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Jobs");
    }

}