package com.example.natha_000.a2_comp_project;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;

/**
 * Created by Natha_000 on 23/12/2016.
 */
public class Rotation {
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    private KalmanFilter kalmanFilterX = new KalmanFilter();
    private KalmanFilter kalmanFilterY = new KalmanFilter();
    private KalmanFilter kalmanFilterZ = new KalmanFilter();

    private float xRotation;
    private float yRotation;
    private float zRotation;
    private float xRotationGyro;
    private float yRotationGyro;
    private float zRotationGyro;
    private float xAngleGyro;
    private float yAngleGyro;
    private float zAngleGyro;
    private float cycles = 0;
    private float xRotationAcc;
    private float yRotationAcc;
    private float zRotationAcc;
    private float timestampGyro;
    private float timestampRot;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private static final float EPSILON = 0.1f;
    private static final float EPSILONSQ = EPSILON*EPSILON;
    private static final float TAU = (float) 2*(float) Math.PI;
    private static final float SENSITIVITY = (float) 10.0f;
    private final float[] deltaRotationVector = new float[4];
    private float[] accOffset = new float[3];
    private boolean calibrated = false;
    private boolean stationary = false;
    private boolean[] gotSensorValue = new boolean[2];
    private static final float tau = 0.075f;
    float[] testRotZ = new float[100];
    float[] gyroValuesx = new float[1000];
    float[] gyroValuesy = new float[1000];
    float[] gyroValuesz = new float[1000];
    int gyrocounter = 0;
    float[] accValuesx = new float[1000];
    float[] accValuesy = new float[1000];
    float[] accValuesz = new float[1000];
    int acccounter = 0;
    boolean accCalibrated = false;

    public Rotation(float x, float y, float z){
        xRotation = x;
        yRotation = y;
        zRotation = z;
    }

    public Rotation(){
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }

    public void add(float x, float y, float z){
        xRotation += x;
        yRotation += y;
        zRotation += z;
    }

    public void addGyroRot(float x, float y, float z){
        xRotationGyro += x;
        yRotationGyro += y;
        zRotationGyro += z;
    }

    public void addGyroAng(float x, float y, float z){
        float fraction = cycles/(cycles + 1);
        xAngleGyro = fraction * xAngleGyro + (1-fraction) * x;
        yAngleGyro = fraction * yAngleGyro + (1-fraction) * y;
        zAngleGyro = fraction * zAngleGyro + (1-fraction) * z;
    }

    public boolean getCalibrated(){
        return calibrated;
    }

    public void trigger(TriggerEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STATIONARY_DETECT) {
            stationary = true;
            Log.d("Stationary Angle", "Device is now Stationary");
        }
        if (event.sensor.getType() == Sensor.TYPE_SIGNIFICANT_MOTION) {
            stationary = false;
            Log.d("Stationary Angle", "Device is not Stationary");
        }
    }

    private void gyroAngReset() {
        xAngleGyro = 0;
        yAngleGyro = 0;
        zAngleGyro = 0;
        cycles = 0;
    }

    public void calibrate() {
        stationary = true;
    }

    private float complementary(float currentAngle,float accAngle, float gyroAngle, float dT) {
        float a = tau/ (tau + dT);
        currentAngle = a * (currentAngle + gyroAngle) + (1-a) * (accAngle);
//        Log.d("Comp", "Doing the Comp thing");
        return currentAngle;
    }

    public void compFull(float[] gyroAngles, float[] accAngles, float dT) {
        xRotation = complementary(xRotation, accAngles[0], gyroAngles[0], dT);
        yRotation = complementary(yRotation, accAngles[1], gyroAngles[1], dT);
        zRotation = complementary(zRotation, accAngles[2], gyroAngles[2], dT);
    }

    public void kalmFull(float[] gyroAngles, float[] accAngles, float dT) {
        xRotation = kalmanFilterX.kalmanFilter(gyroAngles[0], accAngles[0], dT);
        yRotation = kalmanFilterY.kalmanFilter(gyroAngles[1], accAngles[1], dT);
        zRotation = kalmanFilterZ.kalmanFilter(gyroAngles[2], accAngles[2], dT);
    }



    public void sensorChange(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            if (timestampGyro != 0) {
                final float dT = (event.timestamp - timestampGyro) * NS2S;
                float axisXGyro = event.values[0];
                float axisYGyro = event.values[1];
                float axisZGyro = event.values[2];

                gotSensorValue[0] = true;

                float omegaMagnitudeSq = axisXGyro * axisXGyro + axisYGyro * axisYGyro + axisZGyro * axisZGyro;
                if (omegaMagnitudeSq > EPSILONSQ) {
//                    add(axisXGyro * dT, axisYGyro * dT, axisZGyro * dT);
                    addGyroRot(axisXGyro * dT, axisYGyro * dT, axisZGyro * dT);
                    addGyroAng(axisXGyro, axisYGyro, axisZGyro);
                }
            }
            Log.d("Gyro Angles", Float.toString(xRotationGyro) + ", " + Float.toString(yRotationGyro) + ", " + Float.toString(zRotationGyro));
            timestampGyro = event.timestamp;
        }
        if (timestampRot == 0) {
            timestampRot = event.timestamp;
        }
        if (gotSensorValue[0] && gotSensorValue[1]) {
            float[] accRot = {xRotationAcc,yRotationAcc,zRotationAcc};
            float dT = (event.timestamp-timestampRot) * NS2S;
            float[] gyroRot = {xAngleGyro * dT,yAngleGyro  * dT,zAngleGyro  * dT};
            if (stationary && !calibrated && gyrocounter < gyroValuesx.length) {
                gyroValuesx[gyrocounter] = gyroRot[0];
                gyroValuesy[gyrocounter] = gyroRot[1];
                gyroValuesz[gyrocounter] = gyroRot[2];
                gyrocounter++;
                accValuesx[acccounter] = accRot[0];
                accValuesy[acccounter] = accRot[1];
                accValuesz[acccounter] = accRot[2];
                acccounter++;
                Log.d("Callibrating",Integer.toString(gyrocounter));
            } else if (gyrocounter == gyroValuesx.length) {
                calibrated = true;
                Log.d("GyroX Variance", Float.toString(variance(gyroValuesx)));
                Log.d("GyroY Variance", Float.toString(variance(gyroValuesy)));
                Log.d("GyroZ Variance", Float.toString(variance(gyroValuesz)));
                Log.d("AccX Variance", Float.toString(variance(accValuesx)));
                Log.d("AccY Variance", Float.toString(variance(accValuesy)));
                Log.d("AccZ Variance", Float.toString(variance(accValuesz)));
                gyrocounter++;
            }
            //compFull(gyroRot,accRot,dT);
            kalmFull(gyroRot,accRot,dT);
            gotSensorValue[0] = false;
            gotSensorValue[1] = false;
            timestampRot = event.timestamp;
            gyroAngReset();
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
//            Log.d("Mag", Float.toString(event.values[0]));
        }
    }

    public float variance(float[] values) {
        int n = values.length;
        float sumX = 0;
        float sumX2 = 0;
        for (float x: values) {
            sumX += x;
            sumX2 += x*x;
        }
        float var = sumX2/n - (sumX/n)*(sumX/n);
        return var;
    }

    public void updateOrientationAngles(SensorManager mSensorManager) {
        // Update rotation matrix, which is needed to update orientation angles.
        mSensorManager.getRotationMatrix(mRotationMatrix, null,mAccelerometerReading, mMagnetometerReading);

        // "mRotationMatrix" now has up-to-date information.

        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
        gotSensorValue[1] = true;
        // "mOrientationAngles" now has up-to-date information.

        if (stationary && !calibrated & !accCalibrated) {
            accOffset[0] = mOrientationAngles[2];
            accOffset[1] = mOrientationAngles[0];
            accOffset[2] = mOrientationAngles[1];
//            calibrated = true;
//            stationary = false;
            xRotationGyro = 0;
            yRotationGyro = 0;
            zRotationGyro = 0;
            xRotation = 0;
            yRotation = 0;
            zRotation = 0;
            accCalibrated = true;
        }

        yRotationAcc = (mOrientationAngles[2] - accOffset[0]);
        xRotationAcc = -(mOrientationAngles[0] - accOffset[1]);
        zRotationAcc = mOrientationAngles[1] - accOffset[2];
        Log.d("Acc",Float.toString(xRotationAcc) + ", " + Float.toString(yRotationAcc) + ", " + Float.toString(zRotationAcc));
    }



    public float getXRot(){ return xRotation; }
    public float getYRot(){ return yRotation; }
    public float getZRot(){ return zRotation; }
    public float getXAcc(){ return xRotationAcc; }
    public float getYAcc(){ return yRotationAcc; }
    public float getZAcc(){ return zRotationAcc; }
    public float getXGyro(){ return xRotationGyro; }
    public float getYGyro(){ return yRotationGyro; }
    public float getZGyro(){ return zRotationGyro; }
}
