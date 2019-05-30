package com.example.pascos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class wheight extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wheight);






    }

    public void mOnClose(View v){
        EditText gethe = (EditText) findViewById(R.id.gethe);
        EditText getwe = (EditText) findViewById(R.id.getwe);
        String hei = gethe.getText().toString();
        String wei = getwe.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("hei",hei);


        setResult(RESULT_OK,intent);

        finish();

    }





}