package com.example.sensorproximity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnfin;
    SensorManager misensorManager;
    Sensor misensor;
    SensorEventListener milistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnfin = (Button) findViewById(R.id.btn_fin);
        btnfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        misensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        misensor = misensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(misensor == null)
            finish();

        milistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0]< misensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        comenzar();

    }

    public void comenzar(){
        misensorManager.registerListener(milistener, misensor, 2000000);
    }

    public void detener(){
        misensorManager.unregisterListener(milistener);
    }

    @Override
    protected void onPause() {
        detener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        comenzar();
        super.onResume();
    }
}