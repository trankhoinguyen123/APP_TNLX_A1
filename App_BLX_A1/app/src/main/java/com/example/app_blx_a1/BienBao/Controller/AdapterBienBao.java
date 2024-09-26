package com.example.app_blx_a1.BienBao.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.BienBao.Model.BienBao;
import com.example.app_blx_a1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterBienBao extends RecyclerView.Adapter<AdapterBienBao.MyViewHolder> {

    List<BienBao> bienBaoList;
    OnItemClickListener clickListener;
    OnItemLongClickListener longClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTen, txtMoTa;
        public ImageView imgBien, imgPinned;

        public MyViewHolder(View view){
            super(view);
            txtTen = view.findViewById(R.id.txtTenBien);
            txtMoTa = view.findViewById(R.id.txtMoTa);
            imgBien = view.findViewById(R.id.imgBienBao);
            imgPinned = view.findViewById(R.id.imgPinned);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BienBao bienBao);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(BienBao bienBao);
    }

    public AdapterBienBao(List<BienBao> bienBaoList, OnItemClickListener clickListener, OnItemLongClickListener longClickListener){
        this.bienBaoList = bienBaoList;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void setFilterList(List<BienBao> filterList){
        this.bienBaoList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bien_bao, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BienBao bienBao = bienBaoList.get(position);

        holder.txtTen.setText(bienBao.getTen_bien());
        holder.txtMoTa.setText(bienBao.getMo_ta());
        Picasso.get().load(bienBao.getHinh_anh()).resize(150, 150).into(holder.imgBien);

        // Hiển thị hoặc ẩn icon ghim
        if (bienBao.isPinned()) {
            holder.imgPinned.setVisibility(View.VISIBLE);
        } else {
            holder.imgPinned.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(bienBao);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onItemLongClick(bienBao);
                return true;
            }
        });

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));
    }

    @Override
    public int getItemCount() {
        return bienBaoList.size();
    }
}