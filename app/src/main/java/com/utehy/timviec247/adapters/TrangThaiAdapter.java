package com.utehy.timviec247.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.JobDetailsActivity;
import com.utehy.timviec247.activities.LienHeActivity;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.models.UngTuyen;
import com.utehy.timviec247.utils.Common;

import java.util.List;

public class TrangThaiAdapter extends RecyclerView.Adapter<TrangThaiAdapter.ViewHolder> {
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    List<Job> JobList;

    public TrangThaiAdapter(Context context, List<Job> JobList) {
        this.context = context;
        this.JobList = JobList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public TrangThaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrangThaiAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trangthai, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TrangThaiAdapter.ViewHolder holder, int position) {
        Job Job = JobList.get(position);
        holder.tvViTriCongViec.setText(Job.getViTri());
        reference.child("CongTy").child(Job.getIdAccount()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Company congTy = snapshot.getValue(Company.class);
                if (congTy != null) {
                    Common.company = congTy;
                    holder.tvCongTy.setText(congTy.getTenCongTy());
                    Glide.with(context).load(congTy.getLogo()).into(holder.imgLogo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.tvDiaChi.setText(Job.getDiaChi());
        holder.tvKinhNghiem.setText(Job.getKinhNghiem() + " năm");
        holder.tvMucLuong.setText(Job.getLuongMin() + " - " + Job.getLuongMax() + " triệu");


        reference.child("UngTuyen").child(Job.getIdAccount()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UngTuyen ut = dataSnapshot.getValue(UngTuyen.class);
                    if (ut != null) {
                        Common.ungTuyen = ut;
                        if (String.valueOf(ut.getIdTaiKhoanUngTuyen()).equalsIgnoreCase(String.valueOf(Common.account.getId())) && String.valueOf(Job.getId()).equalsIgnoreCase(String.valueOf(ut.getIdCongViec()))) {
                            if (ut.getTrangThai() == 1) {
                                holder.tvTrangThai.setText("Đã chấp nhận");
                                holder.tvTrangThai.setTextColor(Color.GREEN);
                            } else if (ut.getTrangThai() == 2) {
                                holder.tvTrangThai.setText("Đã từ chỗi");
                                holder.tvTrangThai.setTextColor(Color.RED);
                            } else {
                                holder.tvTrangThai.setText("Đang chờ ");
                                holder.tvTrangThai.setTextColor(Color.YELLOW);
                            }
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.job = Job;
                Intent i = new Intent(context, JobDetailsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);
            }
        });
        holder.btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, LienHeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return JobList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvViTriCongViec, tvCongTy, tvDiaChi, tvKinhNghiem, tvThoiGian, tvMucLuong;
        TextView tvTrangThai;
        Button btnLienHe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvViTriCongViec = itemView.findViewById(R.id.tvViTriCongViec);
            tvCongTy = itemView.findViewById(R.id.tvCongTy);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvKinhNghiem = itemView.findViewById(R.id.tvKinhNghiem);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvMucLuong = itemView.findViewById(R.id.tvMucLuong);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnLienHe = itemView.findViewById(R.id.btnLienHe);
        }
    }
}
