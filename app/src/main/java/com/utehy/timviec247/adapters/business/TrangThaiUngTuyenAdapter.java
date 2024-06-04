package com.utehy.timviec247.adapters.business;


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
import com.utehy.timviec247.activities.business.XemCVActivity;
import com.utehy.timviec247.models.Account;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.models.Job;
import com.utehy.timviec247.models.ThongTin;
import com.utehy.timviec247.models.UngTuyen;
import com.utehy.timviec247.utils.Common;

import java.util.List;

public class TrangThaiUngTuyenAdapter extends RecyclerView.Adapter<TrangThaiUngTuyenAdapter.ViewHolder> {
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    List<Job> JobList;

    public TrangThaiUngTuyenAdapter(Context context, List<Job> JobList) {
        this.context = context;
        this.JobList = JobList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public TrangThaiUngTuyenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trangthai_ungtuyen, parent, false));
    }

    public UngTuyen ungTuyen;

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TrangThaiUngTuyenAdapter.ViewHolder holder, int position) {
        Job Job = JobList.get(position);
        holder.tvViTriCongViec.setText(Job.getViTri());

        reference.child("UngTuyen").child(Common.account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ungTuyen = dataSnapshot.getValue(UngTuyen.class);

                    if (ungTuyen != null) {

                        database.getReference("ThongTin").child(ungTuyen.getIdTaiKhoanUngTuyen()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ThongTin thongTin = snapshot.getValue(ThongTin.class);
                                if (thongTin != null) {
                                    if (thongTin.getHinhAnh() != null) {
                                        Glide.with(context).load(thongTin.getHinhAnh()).into(holder.imgLogo);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        reference.child("Account").child(ungTuyen.getIdTaiKhoanUngTuyen()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Account account = snapshot.getValue(Account.class);
                                if (account != null) {

                                    holder.tvHoTen.setText(account.getFullName() +" đã ứng tuyển");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if (Job.getId().equalsIgnoreCase(ungTuyen.getIdCongViec())) {
                            holder.tvViTriCongViec.setText(ungTuyen.getThoiGian());
                            if (ungTuyen.getTrangThai() == 1) {
                                holder.tvTrangThaiUngTuyen.setText("Đã chấp nhận");
                                holder.tvTrangThaiUngTuyen.setTextColor(Color.GREEN);
                            } else if (ungTuyen.getTrangThai() == 2) {
                                holder.tvTrangThaiUngTuyen.setText("Đã từ chỗi");
                                holder.tvTrangThaiUngTuyen.setTextColor(Color.RED);
                            } else {
                                holder.tvTrangThaiUngTuyen.setText("Đang chờ ");
                                holder.tvTrangThaiUngTuyen.setTextColor(Color.YELLOW);
                            }
                        }

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ungTuyen != null) {
                    ungTuyen.setTrangThai(1);
                    reference.child("UngTuyen").child(Common.account.getId()).child(ungTuyen.getId()).setValue(ungTuyen);
                }
            }
        });

        holder.btnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ungTuyen != null) {
                    ungTuyen.setTrangThai(2);
                    reference.child("UngTuyen").child(Common.account.getId()).child(ungTuyen.getId()).setValue(ungTuyen);
                }
            }
        });
        holder.btnXemCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.ungTuyen = ungTuyen;
                Intent i = new Intent(context, XemCVActivity.class);
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
        TextView tvViTriCongViec;
        TextView tvTrangThaiUngTuyen, tvHoTen;
        Button btnChapNhan, btnTuChoi, btnXemCV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvViTriCongViec = itemView.findViewById(R.id.tvViTriCongViec);
            tvTrangThaiUngTuyen = itemView.findViewById(R.id.tvTrangThai);
            btnChapNhan = itemView.findViewById(R.id.btnChapNhan);
            btnTuChoi = itemView.findViewById(R.id.btnTuChoi);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            btnXemCV = itemView.findViewById(R.id.btnXemCV);
        }
    }
}