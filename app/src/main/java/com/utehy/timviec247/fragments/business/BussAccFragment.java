package com.utehy.timviec247.fragments.business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.LoginActivity;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.utils.Common;
import com.utehy.timviec247.utils.EditextValidator;

public class BussAccFragment extends Fragment {

    public BussAccFragment() {
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
        return inflater.inflate(R.layout.fragment_buss_acc, container, false);
    }

    EditText edtTenCongTy, edtDiaChi, edtSoDienThoai, edtEmail, edtWebsite, edtLogo, edtGioiThieu, edtLinhVuc;
    Button btnCapNhat, btnDangXuat;
    FirebaseDatabase database;
    DatabaseReference reference;

    private void init() {
        edtTenCongTy = getView().findViewById(R.id.edtTenCongTy);
        edtDiaChi = getView().findViewById(R.id.edtDiaChiCongTy);
        edtSoDienThoai = getView().findViewById(R.id.edtSoDienThoai);
        edtEmail = getView().findViewById(R.id.edtEmail);
        edtWebsite = getView().findViewById(R.id.edtWebsiteCongTy);
        edtLogo = getView().findViewById(R.id.edtLogo);
        edtGioiThieu = getView().findViewById(R.id.edtGioiThieu);
        btnCapNhat = getView().findViewById(R.id.btnCapNhat);
        edtLinhVuc = getView().findViewById(R.id.edtLinhVuc);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("CongTy");
        btnDangXuat = getView().findViewById(R.id.btnDangXuat);
    }

    private void onClick() {
        btnCapNhat.setOnClickListener(view -> {
            capNhat();
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    private void loadData() {
        reference.child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Company company = snapshot.getValue(Company.class);
                if (company != null) {
                    edtTenCongTy.setText(company.getTenCongTy());
                    edtDiaChi.setText(company.getDiaChi());
                    edtSoDienThoai.setText(company.getSdt());
                    edtEmail.setText(company.getEmail());
                    edtWebsite.setText(company.getWebsite());
                    edtLogo.setText(company.getLogo());
                    edtGioiThieu.setText(company.getGioiThieu());
                    edtLinhVuc.setText(company.getLinhVuc());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void capNhat() {
        boolean isValid = EditextValidator.validateAllEditTexts(edtTenCongTy, edtDiaChi, edtSoDienThoai, edtEmail, edtWebsite, edtLogo, edtGioiThieu, edtLinhVuc);
        if (isValid) {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Đang cập nhật");
            dialog.show();
            String[] texts = EditextValidator.getTextFromEditTexts(edtTenCongTy, edtDiaChi, edtSoDienThoai, edtEmail, edtWebsite, edtLogo, edtGioiThieu, edtLinhVuc);
            Company company = new Company();
            company.setId(Common.account.getId());
            company.setTenCongTy(texts[0]);
            company.setDiaChi(texts[1]);
            company.setSdt(texts[2]);
            company.setEmail(texts[3]);
            company.setWebsite(texts[4]);
            company.setLogo(texts[5]);
            company.setGioiThieu(texts[6]);
            company.setLinhVuc(texts[7]);
            reference.child(Common.account.getId()).setValue(company).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

        }


    }
}