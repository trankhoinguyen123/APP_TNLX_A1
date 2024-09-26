package com.example.app_blx_a1.Thi.Controller;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionsOnTap;
import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Model.DanhSachDeThiThu;
import com.example.app_blx_a1.Thi.Model.Questions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    public static List<Questions> listQuestion;
    List<DanhSachDeThiThu> list;
    public QuestionAdapter(List<Questions> listQuestion) {
        this.listQuestion = listQuestion;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cau_hoi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Questions questions = listQuestion.get(position);
        holder.txtCau1.setText(questions.getCauhoi());
        holder.btn_A.setText(questions.getA());
        holder.btn_B.setText(questions.getB());
        holder.btn_C.setText(questions.getC());
        String imgUrl = questions.getImg();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            holder.imgHinhAnh.setVisibility(View.VISIBLE);
            Picasso.get().load(imgUrl).into(holder.imgHinhAnh);
        } else {
            holder.imgHinhAnh.setVisibility(View.GONE);
        }

        // Reset button backgrounds
        holder.resetButtonBackgrounds();

        // Set selected answer
        int selectedAns = questions.getSelectdAns();
        if (selectedAns == 1) {
            holder.btn_A.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL = holder.btn_A;
        } else if (selectedAns == 2) {
            holder.btn_B.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL = holder.btn_B;
        } else if (selectedAns == 3) {
            holder.btn_C.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL = holder.btn_C;
        } else {
            holder.bnt_ALL = null;
        }

        holder.btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_A, 1, position);
                listQuestion.get(position).setSelectdAns(1);
            }
        });
        holder.btn_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_B, 2, position);
                listQuestion.get(position).setSelectdAns(2);
            }
        });
        holder.btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_C, 3, position);
                listQuestion.get(position).setSelectdAns(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCau1;
        Button btn_A, btn_B, btn_C, bnt_ALL;
        ImageButton imgHinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCau1 = itemView.findViewById(R.id.txtCauHoi1);
            btn_A = itemView.findViewById(R.id.btn_A);
            btn_B = itemView.findViewById(R.id.btn_B);
            btn_C = itemView.findViewById(R.id.btn_C);
            imgHinhAnh = itemView.findViewById(R.id.imgHinhAnh);
        }

        private void resetButtonBackgrounds() {
            btn_A.setBackgroundResource(R.drawable.round_layout_button);
            btn_B.setBackgroundResource(R.drawable.round_layout_button);
            btn_C.setBackgroundResource(R.drawable.round_layout_button);
        }

        private void selectOption(Button btn, int option_menu, int quesID) {
            if (bnt_ALL == null) {
                btn.setBackgroundResource(R.drawable.round_layout_un);
                listQuestion.get(quesID).setSelectdAns(option_menu);
                bnt_ALL = btn;
            } else {
                if (bnt_ALL.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.round_layout_button);
                    listQuestion.get(quesID).setSelectdAns(-1);
                    bnt_ALL = null;
                } else {
                    bnt_ALL.setBackgroundResource(R.drawable.round_layout_button);
                    btn.setBackgroundResource(R.drawable.round_layout_un);
                    listQuestion.get(quesID).setSelectdAns(option_menu);
                    bnt_ALL = btn;
                }
            }
        }
    }
    public void clearSelections() {
        for (Questions question : listQuestion) {
            question.setSelectdAns(0);
        }
        notifyDataSetChanged();
    }
}
