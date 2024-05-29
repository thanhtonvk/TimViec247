package com.utehy.timviec247.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.utehy.timviec247.R;
import com.utehy.timviec247.models.ThongTin;
import com.utehy.timviec247.utils.Common;
import com.utehy.timviec247.utils.EditextValidator;

public class CapNhatThongTinActivity extends AppCompatActivity {
    EditText edtDiaChi, edtSdt, edtEmail, edtNgaySinh, edtGioiThieu, edtAvatar;
    Button btnCapNhat;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_thong_tin);
        init();
        onClick();
        loadData();
    }

    private void init() {
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSdt = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        edtGioiThieu = findViewById(R.id.edtGioiThieu);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        edtAvatar = findViewById(R.id.edtAvatar);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ThongTin");
    }

    private void onClick() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void loadData() {
        if (Common.thongTin != null) {
            edtDiaChi.setText(Common.thongTin.getDiaChi());
            edtSdt.setText(Common.thongTin.getSdt());
            edtEmail.setText(Common.thongTin.getEmail());
            edtNgaySinh.setText(Common.thongTin.getNgaySinh());
            edtGioiThieu.setText(Common.thongTin.getGioiThieu());
            edtAvatar.setText(Common.thongTin.getHinhAnh());
        }
    }

    private void add() {
        boolean isValid = EditextValidator.validateAllEditTexts(edtDiaChi, edtSdt, edtEmail, edtNgaySinh, edtGioiThieu);
        if (isValid) {
            String[] listText = EditextValidator.getTextFromEditTexts(edtDiaChi, edtSdt, edtEmail, edtNgaySinh, edtGioiThieu,edtAvatar);
            ThongTin thongTin = new ThongTin(Common.account.getId(), listText[0], listText[1], listText[2], listText[3], listText[4],listText[5]);
            ProgressDialog dialog = new ProgressDialog(CapNhatThongTinActivity.this);
            dialog.setMessage("Loading");
            dialog.show();
            reference.child(Common.account.getId()).setValue(thongTin).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(CapNhatThongTinActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CapNhatThongTinActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
        }

    }
}