package com.utehy.timviec247.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.CompanyDetailsActivity;
import com.utehy.timviec247.models.Company;
import com.utehy.timviec247.utils.Common;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    Context context;
    List<Company> companyList;

    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_company, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.tvTenCongTy.setText(company.getTenCongTy());
        holder.tvLinhVuc.setText(company.getLinhVuc());
        Glide.with(context).load(company.getLogo()).into(holder.imgLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.company = company;
                context.startActivity(new Intent(context, CompanyDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvTenCongTy, tvLinhVuc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvTenCongTy = itemView.findViewById(R.id.tvTenCongTy);
            tvLinhVuc = itemView.findViewById(R.id.tvLinhVuc);
        }
    }
}
