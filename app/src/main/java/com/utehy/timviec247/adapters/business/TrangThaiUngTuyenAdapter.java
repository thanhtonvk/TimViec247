package com.utehy.timviec247.adapters.business;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.business.ChatActivity;
import com.utehy.timviec247.activities.business.XemCVActivity;
import com.utehy.timviec247.models.Account;
import com.utehy.timviec247.models.ThongTin;
import com.utehy.timviec247.models.UngTuyen;
import com.utehy.timviec247.utils.Common;

import java.util.List;

public class TrangThaiUngTuyenAdapter extends RecyclerView.Adapter<TrangThaiUngTuyenAdapter.ViewHolder> {
    FirebaseDatabase database;
    DatabaseReference reference;
    Context context;
    List<UngTuyen> UngTuyenList;

    public TrangThaiUngTuyenAdapter(Context context, List<UngTuyen> UngTuyenList) {
        this.context = context;
        this.UngTuyenList = UngTuyenList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @NonNull
    @Override
    public TrangThaiUngTuyenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_trangthai_ungtuyen, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TrangThaiUngTuyenAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        UngTuyen ungTuyen = UngTuyenList.get(position);
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

                    holder.tvHoTen.setText(account.getFullName() + " đã ứng tuyển");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        holder.btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UngTuyen ungTuyen = UngTuyenList.get(position);
                ungTuyen.setTrangThai(1);
                reference.child("UngTuyen").child(Common.account.getId()).child(ungTuyen.getId()).child(ungTuyen.getIdTaiKhoanUngTuyen()).setValue(ungTuyen);

            }
        });

        holder.btnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UngTuyen ungTuyen = UngTuyenList.get(position);
                ungTuyen.setTrangThai(2);
                reference.child("UngTuyen").child(Common.account.getId()).child(ungTuyen.getId()).child(ungTuyen.getIdTaiKhoanUngTuyen()).setValue(ungTuyen);

            }
        });
        holder.btnXemCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.ungTuyen = UngTuyenList.get(position);
                Intent i = new Intent(context, XemCVActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);
            }
        });
        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "pos " + position, Toast.LENGTH_SHORT).show();
                Common.ungTuyen = UngTuyenList.get(position);
                Intent i = new Intent(context, ChatActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return UngTuyenList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvViTriCongViec;
        TextView tvTrangThaiUngTuyen, tvHoTen;
        Button btnChapNhan, btnTuChoi, btnXemCV, btnChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvViTriCongViec = itemView.findViewById(R.id.tvViTriCongViec);
            tvTrangThaiUngTuyen = itemView.findViewById(R.id.tvTrangThai);
            btnChapNhan = itemView.findViewById(R.id.btnChapNhan);
            btnTuChoi = itemView.findViewById(R.id.btnTuChoi);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            btnXemCV = itemView.findViewById(R.id.btnXemCV);
            btnChat = itemView.findViewById(R.id.btnLienHe);
        }
    }
}