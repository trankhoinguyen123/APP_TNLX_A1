package com.example.app_blx_a1.Thi.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_blx_a1.R;
import com.example.app_blx_a1.Thi.Controller.QuestionAdapter;
import com.example.app_blx_a1.Thi.Controller.QuestionDialogAdapter;
import com.example.app_blx_a1.Thi.Model.Questions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CauHoiThi extends AppCompatActivity {
    public static List<Questions> listQuestions = new ArrayList<>();
    TextView tv_CauHoi, tv_ThoiGian;
    Button btnChamDiem;
    ImageButton btnBack, btnNext, imgLuu, imgMenu;
    private int quesID;
    RecyclerView recyclerView;
    QuestionAdapter apdapter;
    FirebaseFirestore firebaseFirestore;
    CountDownTimer countDownTimer;
    public static long thoiGianThi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi_thi);
        recyclerView = findViewById(R.id.questions);
        tv_CauHoi = findViewById(R.id.tv_CauHoi);
        tv_ThoiGian = findViewById(R.id.tv_ThoiGian);
        btnChamDiem = findViewById(R.id.btn_ChamDiem);
        btnBack = findViewById(R.id.imgBack);
        btnNext = findViewById(R.id.btnNext);
        imgLuu = findViewById(R.id.imgLuu);
        imgMenu = findViewById(R.id.imgMenu);
        quesID = 0;

        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("Cauhoi");
        reference.whereEqualTo("danhSach_ID", DanhSachDeThi.selectedDanhSachID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                        Questions questions = new Questions();
                        if (documentSnapshot.contains("cauhoi") && documentSnapshot.get("cauhoi") != null) {
                            questions.setCauhoi(documentSnapshot.get("cauhoi").toString());
                        } else {
                            questions.setCauhoi("Unknown");
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

                        if (documentSnapshot.contains("img") && documentSnapshot.get("img") != null) {
                            questions.setImg(documentSnapshot.get("img").toString());
                        } else {
                            questions.setC("Unknown");
                        }
                        questions.setCautraloi(Integer.parseInt(documentSnapshot.get("cautraloi").toString()));
                        questions.setSelectdAns(0);

                        listQuestions.add(questions);
                    }
                    apdapter = new QuestionAdapter(listQuestions);
                    recyclerView.setAdapter(apdapter);
                    //tv_CauHoi.setText("1/" + String.valueOf(listQuestions.size()));

                } else {
                    Toast.makeText(CauHoiThi.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setSnapHelper();
        setClickListeners();
        startTime();
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoCauHoiDialog();
            }
        });
        btnChamDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });
        imgLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listQuestions.clear();
                CauHoiThi.this.finish();

            }
        });
    }

    public void submitTest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CauHoiThi.this);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_cham_diem, null);
        Button btnCancle = view.findViewById(R.id.btnCancel);
        Button btnYes = view.findViewById(R.id.btnYes);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer.cancel();
                alertDialog.dismiss();
                int socaudung = 0;
                int tongDiem = 0;
                int socausai = 0; // Khởi tạo số điểm

                for (int i = 0; i < listQuestions.size(); i++) {
                    int cauTraLoi = listQuestions.get(i).getSelectdAns();
                    int cautraloi = listQuestions.get(i).getCautraloi();

                    if (cauTraLoi == cautraloi) {
                        socaudung++;
                        tongDiem += 4;
                    } else {
                        socausai++;
                    }
                }
                Intent intent = new Intent(CauHoiThi.this, ChamDiemThi.class);
                intent.putExtra("DANH_SACH_ID", DanhSachDeThi.selectedTenDanhSach);
                intent.putExtra("SO_CAU_DUNG", socaudung);
                intent.putExtra("SO_CAU_SAI", socausai);
                intent.putExtra("THOI_GIAN_THI", thoiGianThi);
                intent.putExtra("TONG_DIEM", tongDiem);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
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
                tv_CauHoi.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(listQuestions.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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

    public void startTime() {
        long totaltime = 25 * 60 * 1000;
        countDownTimer = new CountDownTimer(totaltime + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                thoiGianThi = millisUntilFinished;
                String time = String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tv_ThoiGian.setText(time);
            }

            @Override
            public void onFinish() {
                showNotification();
                Intent intent = new Intent(CauHoiThi.this, ChamDiemThi.class);
                startActivity(intent);
                CauHoiThi.this.finish();
            }
        };
        countDownTimer.start();
    }

    private void showSoCauHoiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_cauhoi_list, null);
        builder.setView(dialogView);

        RecyclerView rvQuestionsDialog = dialogView.findViewById(R.id.rvQuestionsDialog);
        rvQuestionsDialog.setLayoutManager(new LinearLayoutManager(this));
        QuestionDialogAdapter dialogAdapter = new QuestionDialogAdapter(listQuestions, new QuestionDialogAdapter.OnQuestionClickListener() {
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Exam Time Up Channel";
            String description = "Channel for exam time up notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("examTimeUpChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("MissingPermission")
    private void showNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, ChamDiemThi.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "examTimeUpChannel")
                .setSmallIcon(R.drawable.history)
                .setContentTitle("Hết Giờ!")
                .setContentText("Thời gian làm bài đã hết. Vui lòng nộp bài của bạn.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}
