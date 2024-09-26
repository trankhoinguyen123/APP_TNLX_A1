// LichSuThiAdapter.java
package com.example.app_blx_a1.Thi.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.app_blx_a1.R;
import java.util.List;
import java.util.Map;

public class LichSuThiAdapter extends BaseAdapter {

    private Context context;
    public List<Map<String, Object>> lichSuThiList;

    public LichSuThiAdapter(Context context, List<Map<String, Object>> lichSuThiList) {
        this.context = context;
        this.lichSuThiList = lichSuThiList;
    }

    @Override
    public int getCount() {
        return lichSuThiList.size();
    }

    @Override
    public Object getItem(int position) {
        return lichSuThiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_lich_su_thi, parent, false);
        }

        TextView txtThoiGianThi = convertView.findViewById(R.id.txtThoiGianThi1);
        TextView txtSoCauDung = convertView.findViewById(R.id.txtSoCauDung1);
        TextView txtSoCauSai = convertView.findViewById(R.id.txtSoCauSai1);
        TextView txtTongDiem = convertView.findViewById(R.id.txtTongDiem1);
        TextView txtTenDe=convertView.findViewById(R.id.txtTenDeThi);

        Map<String, Object> lichSuThi = lichSuThiList.get(position);

        txtThoiGianThi.setText((String) "Thời gian thi: "+lichSuThi.get("thoiGianThi"));
        txtSoCauDung.setText("Số câu đúng: "+String.valueOf(lichSuThi.get("soCauDung")));
        txtSoCauSai.setText("Số câu sai: "+String.valueOf(lichSuThi.get("soCauSai")));
        txtTongDiem.setText("Tổng điểm: "+String.valueOf(lichSuThi.get("tongDiem")));
        txtTenDe.setText("Số đề: "+lichSuThi.get("danhSach_ID"));

        return convertView;
    }
    public void updateData(List<Map<String, Object>> newData) {
        lichSuThiList.clear();
        lichSuThiList.addAll(newData);
        notifyDataSetChanged();
    }
}
