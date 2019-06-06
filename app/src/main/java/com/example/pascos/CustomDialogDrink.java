package com.example.pascos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by charlie on 2017. 8. 18..
 */

public class CustomDialogDrink extends Dialog implements View.OnClickListener{

    private Mypopup popup;

    private static final int LAYOUT = R.layout.dialog_custom2;

    private Context context;
    private TextView sent1;
    private TextView sent2;


    private TextView cancelTv;


    private String name;


    public CustomDialogDrink(Context context,String name){
        super(context);
        this.context = context;
        this.name = name;

    }

    public void setPopup(Mypopup popup){
        this.popup = popup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);



        cancelTv = (TextView) findViewById(R.id.findPwDialogCancelTv);


        cancelTv.setOnClickListener(this);

        int am = Integer.parseInt(name);
        int bm = 530 - am;


        sent1 = (TextView) findViewById(R.id.sent1);
        sent2 = (TextView) findViewById(R.id.sent2);
        if(!TextUtils.isEmpty(name)) {
            sent1.setText("오늘 마실 물의 양은 " + am + "ml 입니다.");
            sent2.setText("앞으로 마실 물의 양은 " +"\n" +bm + "ml 입니다.");
        }
        else{
            sent1.setText("먼저 체중과 키를 입력해주십시오.");
            sent2.setText("");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findPwDialogCancelTv:
                cancel();
                break;

        }
    }

}