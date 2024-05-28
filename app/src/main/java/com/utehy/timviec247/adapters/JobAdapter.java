package com.utehy.timviec247.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.utils.Common;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    List<Job> JobList;

    public JobAdapter(Context context, List<Job> JobList) {
        this.context = context;
        this.JobList = JobList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("CongTy");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job Job = JobList.get(position);
        holder.tvViTriCongViec.setText(Job.getViTri());
        reference.child(Job.getIdAccount()).addValueEventListener(new ValueEventListener() {
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
        holder.tvThoiGian.setText("Thời gian ứng tuyển : " + Job.getThoiGian() + " ngày");
        holder.tvMucLuong.setText(Job.getLuongMin() + " - " + Job.getLuongMax() + " triệu");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.job = Job;
                context.startActivity(new Intent(context, JobDetailsActivity.class));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvViTriCongViec = itemView.findViewById(R.id.tvViTriCongViec);
            tvCongTy = itemView.findViewById(R.id.tvCongTy);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvKinhNghiem = itemView.findViewById(R.id.tvKinhNghiem);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvMucLuong = itemView.findViewById(R.id.tvMucLuong);
        }
    }
}
