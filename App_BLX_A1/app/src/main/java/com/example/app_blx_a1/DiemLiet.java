package com.example.app_blx_a1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionOnTapDialogAdapter;
import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionsOnTap;
import com.example.app_blx_a1.OnTapCauHoi.Model.QuestionsOnTapAdapter;
import com.example.app_blx_a1.OnTapCauHoi.View.CauHoiOnTap;
import com.example.app_blx_a1.OnTapCauHoi.View.OnTap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiemLiet extends AppCompatActivity {
    public static List<QuestionsOnTap> listQuestions = new ArrayList<>();
    ImageButton btnBack, btnNext, imgMenu,btnBack1,imgXoa;
    RecyclerView recyclerView;
    QuestionsOnTapAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    TextView tv_CauHoi;
    private int quesID;
    public static String selectedAnswer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_liet);
        btnBack=findViewById(R.id.btnBack1);
        btnNext=findViewById(R.id.btnNext1);
        imgMenu=findViewById(R.id.imgMenu1);
        btnBack1=findViewById(R.id.imgBack1);
        tv_CauHoi=findViewById(R.id.txt_CauHoi);
        imgXoa=findViewById(R.id.imgLuu);
        quesID = 0;
        recyclerView=findViewById(R.id.questionsOnTap);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("DsOntap");
        reference.whereEqualTo("Ontap_id", "f8jvRWCp9rywnP88WAd4").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                        QuestionsOnTap questions = new QuestionsOnTap();
                        if (documentSnapshot.contains("tencau") && documentSnapshot.get("tencau") != null) {
                            questions.setTencau(documentSnapshot.get("tencau").toString());
                        } else {
                            questions.setTencau("Unknown");
                        }

                        if (documentSnapshot.contains("A") && documentSnapshot.get("A") != null) {
                            questions.setA(documentSnapshot.get("A").toString());
                        } else {
                            questions.setA("Unknown");
                        }

                        if (documentSnapshot.contains("B") && documentSnapshot.get("B") != null) {
                            questions.setB(documentSnapshot.get("B").toString());
                        } else {
                            questions.setB("Unknown");
                        }

                        if (documentSnapshot.contains("C") && documentSnapshot.get("C") != null) {
                            questions.setC(documentSnapshot.get("C").toString());
                        } else {
                            questions.setC("Unknown");
                        }

                        if (documentSnapshot.contains("imgontap") && documentSnapshot.get("imgontap") != null) {
                            questions.setImg(documentSnapshot.get("imgontap").toString());
                        } else {
                            questions.setC("Unknown");
                        }
                        questions.setAnswer(documentSnapshot.get("answer").toString());
                        questions.setSelectdAns(0);

                        listQuestions.add(questions);
                    }
                    adapter = new QuestionsOnTapAdapter(listQuestions);
                    recyclerView.setAdapter(adapter);
                    tv_CauHoi.setText("1/" + String.valueOf(listQuestions.size())+" Điểm liệt");

                } else {
                    Toast.makeText(DiemLiet.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setSnapHelper();
        setClickListeners();
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoCauHoiDialog();
            }
        });
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listQuestions.clear();
                DiemLiet.this.finish();
            }
        });
        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearSelections();
            }
        });
    }
    public void setClickListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quesID > 0) {
                    recyclerView.smoothScrollToPosition(quesID - 1);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quesID < listQuestions.size()) {
                    recyclerView.smoothScrollToPosition(quesID + 1);
                }
            }
        });
    }
    private void showSoCauHoiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_cauhoi_list, null);
        builder.setView(dialogView);

        RecyclerView rvQuestionsDialog = dialogView.findViewById(R.id.rvQuestionsDialog);
        rvQuestionsDialog.setLayoutManager(new LinearLayoutManager(this));
        QuestionOnTapDialogAdapter dialogAdapter = new QuestionOnTapDialogAdapter(listQuestions, new QuestionOnTapDialogAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(int position) {
                if (position >= 0 && position < listQuestions.size()) {

                    recyclerView.smoothScrollToPosition(position);
                }
            }
        });
        rvQuestionsDialog.setAdapter(dialogAdapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);
                tv_CauHoi.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(listQuestions.size()) +" Điểm liệt");
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}