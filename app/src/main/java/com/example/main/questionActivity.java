package com.example.main;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class questionActivity extends AppCompatActivity {
    String [] que = {"통증", "두통", "복통", "요통", "흉통"};
    int check = 0;

    TextView[] tvArr1;
    RadioGroup[] rgArr;
    RadioButton[] r1;
    RadioButton[] r2;
    int checkedRadioButtonId;
    String nick;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nick = getIntent().getStringExtra("nick");
        result = getIntent().getStringExtra("result");
        setContentView(R.layout.question);
        title();
        question(que);
        Com_Button();
    }

    void title() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        TextView title = new TextView(this);
        title.setTextColor(Color.BLACK);
        title.setTypeface(null, Typeface.BOLD);
        title.setText("문진표");
        title.setTextSize(35);
        title.setGravity(Gravity.CENTER);
        ll.addView(title);
    }

    void question(String[] queArr) {
        int[] numArr = new int[queArr.length];
        for (int i = 0; i < numArr.length; i++) {
            numArr[i] = i + 1;
        }

        tvArr1 = new TextView[numArr.length];
        rgArr = new RadioGroup[numArr.length];
        r1 = new RadioButton[numArr.length];
        r2 = new RadioButton[numArr.length];


        for (int i = 0; i < tvArr1.length; i++) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
            LinearLayout rr = new LinearLayout(this);

            tvArr1[i] = new TextView(this);
            rgArr[i] = new RadioGroup(this);
            r1[i] = new RadioButton(this);
            r2[i] = new RadioButton(this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            r1[i].setText("Y");
            r2[i].setText(("N"));
            rgArr[i].setOrientation(LinearLayout.HORIZONTAL);

            rgArr[i].addView(r1[i]);
            rgArr[i].addView(r2[i]);
            layoutParams.setMargins(0, 0, 0, 50); // 세로 간격을 20dp로 설정합니다.
            tvArr1[i].setLayoutParams(layoutParams);
            tvArr1[i].setTextSize(23);
            tvArr1[i].setTextColor(Color.BLACK);
            tvArr1[i].setTypeface(null, Typeface.BOLD);
            tvArr1[i].setText(numArr[i] + ". " + queArr[i] + "이 있습니까?");
            rr.addView(tvArr1[i]);
            rr.addView(rgArr[i]);
            ll.addView(rr);
        }
    }

    void Com_Button() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        Button bt = new Button(this);
        bt.setText("완료");
        bt.setTextColor(Color.BLACK);
        bt.setTextSize(20);
        ll.addView(bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    check = 0;
                    for(int i = 0;i<que.length;i++){
                        checkedRadioButtonId = rgArr[i].getCheckedRadioButtonId();
                        if(checkedRadioButtonId == -1){
                            Toast.makeText(getApplicationContext(), "라디오 버튼을 선택해주세요", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else{
                            check ++;
                        }
                    }
                    if(que.length == check){
                        String RT  = "";
                        for(int i =0;i<rgArr.length;i++) {
                            boolean isChecked = r1[i].isChecked();
                            if (isChecked == true) {
                                RT += que[i] + ",";
                            }
                        }
                        queActivity task = new queActivity();
                        result = task.execute(nick, RT).get();
                        finish();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }

            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("nickname", nick);
        if(result.equals("")){
            result = "의심되는 증상이 없습니다.";
        }

        intent.putExtra("jindan", result);
        startActivity(intent);
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}