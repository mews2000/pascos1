package com.example.pascos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by charlie on 2017. 8. 18..
 */

public class CustomDialog extends Dialog implements View.OnClickListener{

    private MyDialogListener dialogListener;

    private static final int LAYOUT = R.layout.dialog_custom;

    private Context context;

    private TextInputEditText nameEt;
    private TextInputEditText emailEt;

    private TextView cancelTv;
    private TextView searchTv;

    private String name;


    public CustomDialog(Context context,String name){
        super(context);
        this.context = context;
        this.name = name;

    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        nameEt = (TextInputEditText) findViewById(R.id.findPwDialogNameEt);
        emailEt = (TextInputEditText) findViewById(R.id.findPwDialogEmailEt);

        cancelTv = (TextView) findViewById(R.id.findPwDialogCancelTv);
        searchTv = (TextView) findViewById(R.id.findPwDialogFindTv);

        cancelTv.setOnClickListener(this);
        searchTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findPwDialogCancelTv:
                cancel();
                break;
            case R.id.findPwDialogFindTv:
                if(emailEt == null || emailEt.length() ==0) {
                    dialogListener.onPositiveClicked("0","0");
                    dismiss();
                    break;
                }
                else {
                    String email = emailEt.getText().toString();
                    String name = nameEt.getText().toString();
                    dialogListener.onPositiveClicked(email, name);
                    dismiss();
                    break;
                }
        }
    }

}