package com.zly.viewstatuslayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn1: //不同界面的切换封装到BaseAtivity中
                startActivity(new Intent(this,SecondActivity.class));
                break;
            case R.id.btn2: //自定义一个View来切换布局。
                startActivity(new Intent(this,ThirdActivity.class));
                break;
        }
    }
}