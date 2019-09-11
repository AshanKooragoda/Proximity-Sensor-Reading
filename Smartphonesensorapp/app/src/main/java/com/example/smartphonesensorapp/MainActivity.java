package com.example.smartphonesensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListner;
    private boolean buttonEnable;
    private Button startButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startButton = findViewById(R.id.btn_start);
        this.textView = findViewById(R.id.textView2);
        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.buttonEnable = !MainActivity.this.buttonEnable;
                if (MainActivity.this.buttonEnable){
                    MainActivity.this.startButton.setText("STOP");
                    sensorManager.registerListener(proximitySensorListner,proximitySensor,2*1000*1000);
                } else{
                    MainActivity.this.startButton.setText("START");
                    sensorManager.unregisterListener(proximitySensorListner);
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    MainActivity.this.textView.setText("");
                }
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor == null){
            Toast.makeText(this,"Proximity Sensor is not available", Toast.LENGTH_LONG).show();
            finish();
        }

        proximitySensorListner = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                MainActivity.this.textView.setText(String.valueOf(sensorEvent.values[0]));
                if (sensorEvent.values[0]< proximitySensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else{
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
}
