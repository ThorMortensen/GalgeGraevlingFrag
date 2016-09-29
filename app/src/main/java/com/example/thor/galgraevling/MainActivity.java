package com.example.thor.galgraevling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSingleP).setOnClickListener(this);
        findViewById(R.id.buttonMulty).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSingleP:
                Intent playscreen = new Intent(this, Playscreen.class);
                this.startActivity(playscreen);
                break;
            case R.id.buttonMulty:
                break;
        }
    }
}
