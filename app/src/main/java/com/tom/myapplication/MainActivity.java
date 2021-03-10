package com.tom.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lkn.net.creator.ServiceGenerator;


public class MainActivity extends AppCompatActivity {

    AppCompatTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this::onClicks);

    }

    public void onClicks(View view) {
//        MyToast.show(this, JsonUtils.toJson("123456"));
//        ServiceGenerator serviceGenerator = new ServiceGenerator();
        startActivity(new Intent(MainActivity.this,CalculatorActivity.class));
    }
}
