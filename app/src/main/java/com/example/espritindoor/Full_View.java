package com.example.espritindoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.espritindoor.databinding.ActivityFullViewBinding;

public class Full_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__view);
        ActivityFullViewBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_full__view);

        int img_id = getIntent().getExtras().getInt("img");
        binding.imgFull.setImageResource(img_id);

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Full_View.this, MainActivity.class);
                intent.putExtra("profile",img_id );
                startActivity(intent);
                finish();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Full_View.this , MainActivity.class);
                intent.putExtra("background", img_id);
                startActivity(intent);
                finish();
            }
        });
    }
}
