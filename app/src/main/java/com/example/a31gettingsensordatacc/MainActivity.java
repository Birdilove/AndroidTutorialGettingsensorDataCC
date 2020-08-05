package com.example.a31gettingsensordatacc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor lightSensor;
    private ConstraintLayout light;
    private ConstraintLayout proximity;
    private int RED = Color.RED;
    private int GREEN = Color.GREEN;
    private int BLUE = Color.BLUE;
    private TextView lightText, proxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light = findViewById(R.id.lightLayout);
        lightText = findViewById(R.id.lightText);
        proxText = findViewById(R.id.proximityText);
        proximity = findViewById(R.id.proximityLayout);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (proximity != null) {
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        if (sensorType == Sensor.TYPE_LIGHT) {
            Random random = new Random();
            lightText.setText("Light Sensor Data: " + String.valueOf(currentValue));
            int randomNumber = random.nextInt(15 - 1) + 1;
            if (randomNumber < 6) {
                light.setBackgroundColor(RED);
            } else if (randomNumber < 10 && randomNumber > 5) {
                light.setBackgroundColor(GREEN);
            } else if (randomNumber < 15 && randomNumber > 10) {
                light.setBackgroundColor(BLUE);
            }
        }
        if (sensorType == Sensor.TYPE_PROXIMITY) {
            Random random = new Random();
            proxText.setText("Proximity Sensor Data: " + String.valueOf(currentValue));
            if (currentValue < 5) {
                proximity.setMaxHeight(150);
            } else {
                proximity.setMaxHeight(225);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
