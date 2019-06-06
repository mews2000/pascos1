package com.example.pascos;

import android.bluetooth.BluetoothAdapter;

import android.content.Intent;
import android.os.CountDownTimer;
import android.app.Activity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.Toast;

import org.w3c.dom.Text;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MILLISINFUTURE = 10010;
    private static final int COUNT_DOWN_INTERVAL = 10;
    private static final int LAYOUT = R.layout.activity_main;
    private int count = 7000;
    private TextView countTxt;
    private TextView amm;
    private CountDownTimer countDownTimer;
    private BluetoothSPP bt;

    private Button personal;
    private String requestName;
    private Button amount;


    private TextView thermotext;
    private String nameEt;
    private int amo;
    private TextView thermot;
    private TextView humit;
    private TextView dirtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        personal = (Button) findViewById(R.id.personal);
        countTxt = (TextView) findViewById(R.id.count_txt);

        thermot = (TextView) findViewById(R.id.thermot);
        humit = (TextView) findViewById(R.id.humit);
        dirtt = (TextView) findViewById(R.id.dirtt);
        amount = (Button) findViewById(R.id.amount);

        countDownTimer();




        Button kill = (Button) findViewById(R.id.kill);



        personal.setOnClickListener(this);
        amount.setOnClickListener(this);
        thermotext = (TextView) findViewById(R.id.thermotext);

        thermot.setText("온도 : "+"℃");
        humit.setText("습도 : "+"%");
        dirtt.setText("미세먼지 : "+"㎍/㎥");











        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView btnConnect = findViewById(R.id.btnb); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal:

                CustomDialog dialog = new CustomDialog(this, requestName);
                dialog.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(String email, String name) {

                            setResult(email, name);

                    }

                    @Override
                    public void onNegativeClicked() {
                        Log.d("MyDialogListener", "onNegativeClicked");

                    }
                });
                dialog.show();
                break;
            case R.id.amount:
                requestName = Integer.toString(amo);
                CustomDialogDrink dialogDrink = new CustomDialogDrink(this, requestName);
                dialogDrink.setPopup(new Mypopup() {
                    @Override
                    public void confiremd() {
                        Log.d("Mypopup", "confirmed");
                    }
                });



                dialogDrink.show();
                break;
        }
    }



    private void setResult(String email,String name) {
        amo = (Integer.parseInt(email)+Integer.parseInt(name))*10 ;

    }

    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                countTxt.setText(String.valueOf(1+count/700 +" "+"초"));

                ProgressBar timer = (ProgressBar) findViewById(R.id.timer);
                ProgressBar gage = (ProgressBar) findViewById(R.id.gage);
                gage.setProgress(0);
                timer.setProgress(count);
                count = count -12;
            }
            public void onFinish() {
                ProgressBar timer = (ProgressBar) findViewById(R.id.timer);

                timer.setProgress(0);
                countTxt.setText(String.valueOf("살균 끝"));
                count = 7000;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        countTxt.setText("");




                    }
                },2000);

            }

        };
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
        bt.stopService();
    }


    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    public void setup() {

        Button kill = findViewById(R.id.kill); //데이터 전송
        final Handler handler = new Handler();
        kill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (count == 7000) {
                    countDownTimer.start();

                    ProgressBar timer = (ProgressBar) findViewById(R.id.timer);
                    ProgressBar gage = (ProgressBar) findViewById(R.id.gage);
                    gage.setProgress(0);
                    timer.setProgress(count);
                    bt.send("1", true);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bt.send("0", true);
                        }
                    }, 10000);
                }

            }

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }




}





