package com.example.app_blx_a1.OnTapCauHoi.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.example.app_blx_a1.OnTapCauHoi.Model.DanhSachOnTapApdapter;
import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Model.DanhSachDeThiThu;

import java.util.List;

public class DanhSachOnTapApdapter extends BaseAdapter {
    private List<DanhSachOnTap>listDanhSach;
    public static int getListDanhSach=0;
    public DanhSachOnTapApdapter(List<DanhSachOnTap> listDanhSach)
    {
        this.listDanhSach=listDanhSach;
    }

    @Override
    public int getCount() {
        return listDanhSach.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(view==null)
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_danh_sach_on_tap,parent,false);
        }
        else
            view=convertView;
        getListDanhSach=position;
        TextView txtTen=view.findViewById(R.id.txtDanhSachCauHoi);
        TextView txtSoCau=view.findViewById(R.id.PhanTramDanhSach);
        txtTen.setText(listDanhSach.get(position).getLoaicau());
        txtSoCau.setText(String.valueOf(listDanhSach.get(position).getSocau())+" câu hỏi.");
        return view;
    }
}
