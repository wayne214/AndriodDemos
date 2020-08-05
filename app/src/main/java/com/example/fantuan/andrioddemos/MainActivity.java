package com.example.fantuan.andrioddemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fantuan.andrioddemos.customView.PieImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pieImageView) PieImageView pieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pieImageView.setProgress(50);
    }


}
