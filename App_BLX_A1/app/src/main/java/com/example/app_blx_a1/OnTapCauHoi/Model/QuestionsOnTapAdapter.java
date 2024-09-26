package com.example.app_blx_a1.OnTapCauHoi.Model;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionsOnTapAdapter extends RecyclerView.Adapter<QuestionsOnTapAdapter.ViewHolder> {
    public static List<QuestionsOnTap> listQuestionOnTap;

    public QuestionsOnTapAdapter(List<QuestionsOnTap> listQuestionOnTap) {
        this.listQuestionOnTap = listQuestionOnTap;
    }

    @NonNull
    @Override
    public QuestionsOnTapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cau_hoi_on_tap, parent, false);
        return new QuestionsOnTapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsOnTapAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        QuestionsOnTap questions = listQuestionOnTap.get(position);
        holder.txtCau11.setText(questions.getTencau());
        holder.btn_A1.setText(questions.getA());
        holder.btn_B1.setText(questions.getB());
        holder.btn_C1.setText(questions.getC());
        String imgUrl = questions.getImg();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            holder.imgHinhAnh1.setVisibility(View.VISIBLE);
            Picasso.get().load(imgUrl).into(holder.imgHinhAnh1);
        } else {
            holder.imgHinhAnh1.setVisibility(View.GONE);
        }
        holder.resetButtonBackgrounds();
        // Set selected answer
        int selectedAns = questions.getSelectdAns();
        if (selectedAns == 1) {
            holder.btn_A1.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL1 = holder.btn_A1;
        } else if (selectedAns == 2) {
            holder.btn_B1.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL1 = holder.btn_B1;
        } else if (selectedAns == 3) {
            holder.btn_C1.setBackgroundResource(R.drawable.round_layout_un);
            holder.bnt_ALL1 = holder.btn_C1;
        } else {
            holder.bnt_ALL1 = null;
        }



        holder.btn_A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_A1, 1, position);
                holder.txtCauTraLoi.setText("Giải thích: \n"+questions.getAnswer());
                listQuestionOnTap.get(position).setSelectdAns(1);
            }
        });
        holder.btn_B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_B1, 2, position);
                holder.txtCauTraLoi.setText("Giải thích: \n"+questions.getAnswer());
                listQuestionOnTap.get(position).setSelectdAns(2);
            }
        });
        holder.btn_C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_C1, 3, position);
                holder.txtCauTraLoi.setText("Giải thích: \n"+questions.getAnswer());
                listQuestionOnTap.get(position).setSelectdAns(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuestionOnTap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCau11,txtCauTraLoi;
        Button btn_A1, btn_B1, btn_C1, bnt_ALL1;
        ImageButton imgHinhAnh1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCau11 = itemView.findViewById(R.id.txtCauHoiOnTap);
            btn_A1 = itemView.findViewById(R.id.btn_A1);
            btn_B1 = itemView.findViewById(R.id.btn_B1);
            btn_C1 = itemView.findViewById(R.id.btn_C1);
            imgHinhAnh1 = itemView.findViewById(R.id.imgHinhAnhOnTap);
            txtCauTraLoi=itemView.findViewById(R.id.txtCauTraLoi);

        }
        private void resetButtonBackgrounds() {
            btn_A1.setBackgroundResource(R.drawable.round_layout_button);
            btn_B1.setBackgroundResource(R.drawable.round_layout_button);
            btn_C1.setBackgroundResource(R.drawable.round_layout_button);
        }
        private void selectOption(Button btn, int option_menu, int quesID) {
            if (bnt_ALL1 == null) {
                btn.setBackgroundResource(R.drawable.round_layout_un);
                listQuestionOnTap.get(quesID).setSelectdAns(option_menu);
                bnt_ALL1 = btn;
            } else {
                if (bnt_ALL1.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.round_layout_button);
                    listQuestionOnTap.get(quesID).setSelectdAns(-1);
                    bnt_ALL1 = null;
                } else {
                    bnt_ALL1.setBackgroundResource(R.drawable.round_layout_button);
                    btn.setBackgroundResource(R.drawable.round_layout_un);
                    listQuestionOnTap.get(quesID).setSelectdAns(option_menu);
                    bnt_ALL1 = btn;
                }
            }
        }
    }
    public void clearSelections() {
        for (QuestionsOnTap question : listQuestionOnTap) {
            question.setSelectdAns(0);
        }
        notifyDataSetChanged();
    }
}
