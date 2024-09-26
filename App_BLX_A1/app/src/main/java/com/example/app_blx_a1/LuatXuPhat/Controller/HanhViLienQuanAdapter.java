package com.example.app_blx_a1.LuatXuPhat.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.LuatXuPhat.Model.HanhViLienQuan;
import com.example.app_blx_a1.R;

import java.util.List;

public class HanhViLienQuanAdapter extends RecyclerView.Adapter<HanhViLienQuanAdapter.HanhViLienQuanViewHolder> {
    private Context context;
    private List<HanhViLienQuan> hanhViLienQuanList;

    public HanhViLienQuanAdapter(Context context, List<HanhViLienQuan> hanhViLienQuanList) {
        this.context = context;
        this.hanhViLienQuanList = hanhViLienQuanList;
    }

    @NonNull
    @Override
    public HanhViLienQuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_hanh_vi_lien_quan, parent, false);
        return new HanhViLienQuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HanhViLienQuanViewHolder holder, int position) {
        HanhViLienQuan hanhViLienQuan = hanhViLienQuanList.get(position);
        holder.txtNguoiDieuKhien.setText(hanhViLienQuan.getNguoiDieuKhien());
        holder.txtMoTaViPham.setText(hanhViLienQuan.getMoTaViPham());
        holder.txtMucPhat.setText(hanhViLienQuan.getMucPhat());
    }

    @Override
    public int getItemCount() {
        return hanhViLienQuanList.size();
    }

    public static class HanhViLienQuanViewHolder extends RecyclerView.ViewHolder {
        TextView txtNguoiDieuKhien, txtMoTaViPham, txtMucPhat;

        public HanhViLienQuanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNguoiDieuKhien = itemView.findViewById(R.id.txtNguoiDieuKhienItemLQ);
            txtMoTaViPham = itemView.findViewById(R.id.txtMoTaViPhamItemLQ);
            txtMucPhat = itemView.findViewById(R.id.txtMucPhatItemLQ);
        }
    }
}
