package com.hh.flippycard;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FlipActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;


    private Sensor mSensor;

    ImageView iv;
    int img1=0,img2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        Intent intent = getIntent();
        img1 =  intent.getIntExtra("img1",0);
        img2 = intent.getIntExtra("img2",0);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        iv = (ImageView) findViewById(R.id.imageView1);
        iv.setImageResource(img1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    int flag=0;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0]==0) {
            if(flag==0)
            {
                iv.setImageResource(img1);
                flag=1;
            }
            else
            {
                iv.setImageResource(img2);
                flag=0;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
