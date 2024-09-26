// QuestionDialogAdapter.java
package com.example.app_blx_a1.Thi.Controller;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Model.Questions;

import java.util.List;

public class QuestionDialogAdapter extends RecyclerView.Adapter<QuestionDialogAdapter.ViewHolder> {

    private List<Questions> questionList;
    public interface OnQuestionClickListener {
        void onQuestionClicked(int position);
    }
    private OnQuestionClickListener listener;
    public QuestionDialogAdapter(List<Questions> questionList, OnQuestionClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cau_hoi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions question = questionList.get(position);
        holder.tvQuestion.setTypeface(holder.tvQuestion.getTypeface(), Typeface.BOLD);
        holder.tvQuestion.setText((position + 1) + ". " + question.getCauhoi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onQuestionClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestionDialog);
        }
    }
}
