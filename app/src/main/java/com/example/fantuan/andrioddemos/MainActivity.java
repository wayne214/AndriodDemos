package com.example.fantuan.andrioddemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fantuan.andrioddemos.customView.PieImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pieImageView) PieImageView pieImageView;
    @OnClick(R.id.pieImageView) void click() {
        Toast.makeText(this, "单击了吗？", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pieImageView.setProgress(50);
    }

}
