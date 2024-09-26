package com.example.app_blx_a1.LuatXuPhat.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.LuatXuPhat.Model.LuatGiaoThong;
import com.example.app_blx_a1.LuatXuPhat.View.LuatXuPhatDetailActivity;
import com.example.app_blx_a1.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdapterXuPhat extends RecyclerView.Adapter<AdapterXuPhat.MyViewHolder> implements Filterable {

    List<LuatGiaoThong> luatGiaoThongList;
    List<LuatGiaoThong> luatGiaoThongListFull; // To keep full list

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtHanhVi,txtMucPhat;
        public MyViewHolder(View view){
            super(view);

            txtHanhVi = view.findViewById(R.id.txtHanhVi);
            txtMucPhat = view.findViewById(R.id.txtMucPhat);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    if(i != RecyclerView.NO_POSITION){
                        LuatGiaoThong luatGiaoThong = luatGiaoThongList.get(i);

                        Intent intent = new Intent(context, LuatXuPhatDetailActivity.class);
                        Gson gson = new Gson();
                        String luatGiaoThongJson = gson.toJson(luatGiaoThong);
                        intent.putExtra("luatGiaoThong", luatGiaoThongJson);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }

    public AdapterXuPhat(List<LuatGiaoThong> luatGiaoThongList,Context conText){
        this.luatGiaoThongList = luatGiaoThongList;
        this.context = conText;
        luatGiaoThongListFull = new ArrayList<>(luatGiaoThongList); // Initialize full list

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_xu_phat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LuatGiaoThong luatGiaoThong = luatGiaoThongList.get(position);

        holder.txtHanhVi.setText(luatGiaoThong.getMoTaViPham());
        holder.txtMucPhat.setText(luatGiaoThong.getMucPhat());

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim1));
    }

    @Override
    public int getItemCount() {
        return luatGiaoThongList.size();
    }

    @Override
    public Filter getFilter() {
        return luatGiaoThongFilter;
    }
    private Filter luatGiaoThongFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LuatGiaoThong> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(luatGiaoThongListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (LuatGiaoThong item : luatGiaoThongListFull) {
                    if (item.getMoTaViPham().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            luatGiaoThongList.clear();
            luatGiaoThongList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
