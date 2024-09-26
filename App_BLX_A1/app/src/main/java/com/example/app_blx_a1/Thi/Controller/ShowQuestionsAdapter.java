package com.example.app_blx_a1.Thi.Controller;

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
import com.example.app_blx_a1.Thi.Model.Questions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowQuestionsAdapter extends RecyclerView.Adapter<ShowQuestionsAdapter.ViewHolder> {
    public static List<Questions> listQuestion;

    public ShowQuestionsAdapter(List<Questions> listQuestionOnTap) {
        this.listQuestion = listQuestionOnTap;
    }

    @NonNull
    @Override
    public ShowQuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_dap_an, parent, false);
        return new ShowQuestionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowQuestionsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Questions questions = listQuestion.get(position);
        holder.txtCau11.setText(questions.getCauhoi());
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
        holder.txtCauTraLoi.setText("Đáp án là: \n"+ "Câu "+questions.getCautraloi());
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
                holder.txtCauTraLoi.setText("Đáp án là: \n"+ "Câu "+questions.getCautraloi());
                listQuestion.get(position).setSelectdAns(1);
            }
        });
        holder.btn_B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_B1, 2, position);
                holder.txtCauTraLoi.setText("Đáp án là: \n"+ "Câu "+questions.getCautraloi());
                listQuestion.get(position).setSelectdAns(2);
            }
        });
        holder.btn_C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectOption(holder.btn_C1, 3, position);
                holder.txtCauTraLoi.setText("Đáp án là: \n"+ "Câu "+questions.getCautraloi());
                listQuestion.get(position).setSelectdAns(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCau11,txtCauTraLoi;
        Button btn_A1, btn_B1, btn_C1, bnt_ALL1;
        ImageButton imgHinhAnh1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCau11 = itemView.findViewById(R.id.txtCauHoiThi);
            btn_A1 = itemView.findViewById(R.id.btn_A0);
            btn_B1 = itemView.findViewById(R.id.btn_B0);
            btn_C1 = itemView.findViewById(R.id.btn_C0);
            imgHinhAnh1 = itemView.findViewById(R.id.imgHinhAnhThi);
            txtCauTraLoi=itemView.findViewById(R.id.txtCauTraLoiThi);

        }
        private void resetButtonBackgrounds() {
            btn_A1.setBackgroundResource(R.drawable.round_layout_button);
            btn_B1.setBackgroundResource(R.drawable.round_layout_button);
            btn_C1.setBackgroundResource(R.drawable.round_layout_button);
        }
        private void selectOption(Button btn, int option_menu, int quesID) {
            if (bnt_ALL1 == null) {
                btn.setBackgroundResource(R.drawable.round_layout_un);
                listQuestion.get(quesID).setSelectdAns(option_menu);
                bnt_ALL1 = btn;
            } else {
                if (bnt_ALL1.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.round_layout_button);
                    listQuestion.get(quesID).setSelectdAns(-1);
                    bnt_ALL1 = null;
                } else {
                    bnt_ALL1.setBackgroundResource(R.drawable.round_layout_button);
                    btn.setBackgroundResource(R.drawable.round_layout_un);
                    listQuestion.get(quesID).setSelectdAns(option_menu);
                    bnt_ALL1 = btn;
                }
            }
        }
    }
}
