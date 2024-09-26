package com.example.app_blx_a1.LuatXuPhat.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.LuatXuPhat.Model.HanhViHopNhat;
import com.example.app_blx_a1.R;

import java.util.List;

public class HanhViHopNhatAdapter extends RecyclerView.Adapter<HanhViHopNhatAdapter.HanhViHopNhatViewHolder> {
    private Context context;
    private List<HanhViHopNhat> hanhViHopNhatList;

    public HanhViHopNhatAdapter(Context context, List<HanhViHopNhat> hanhViHopNhatList) {
        this.context = context;
        this.hanhViHopNhatList = hanhViHopNhatList;
    }

    @NonNull
    @Override
    public HanhViHopNhatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_hanh_vi_hop_nhat, parent, false);
        return new HanhViHopNhatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HanhViHopNhatViewHolder holder, int position) {
        HanhViHopNhat hanhViHopNhat = hanhViHopNhatList.get(position);
        holder.txtNguoiDieuKhien.setText(hanhViHopNhat.getNguoiDieuKhien());
        holder.txtMoTaViPham.setText(hanhViHopNhat.getMoTaViPham());
        holder.txtMucPhat.setText(hanhViHopNhat.getMucPhat());
        holder.txtPhatBoSung.setText(hanhViHopNhat.getPhatBoSung());
    }

    @Override
    public int getItemCount() {
        return hanhViHopNhatList.size();
    }

    public static class HanhViHopNhatViewHolder extends RecyclerView.ViewHolder {
        TextView txtNguoiDieuKhien, txtMoTaViPham, txtMucPhat, txtPhatBoSung;

        public HanhViHopNhatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNguoiDieuKhien = itemView.findViewById(R.id.txtNguoiDieuKhienItemHN);
            txtMoTaViPham = itemView.findViewById(R.id.txtMoTaViPhamItemHN);
            txtMucPhat = itemView.findViewById(R.id.txtMucPhatItemHN);
            txtPhatBoSung = itemView.findViewById(R.id.txtPhatBoSungITemHN);
        }
    }
}
