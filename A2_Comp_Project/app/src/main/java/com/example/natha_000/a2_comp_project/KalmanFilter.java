package com.example.natha_000.a2_comp_project;

/**
 * Created by Natha_000 on 27/12/2016.
 */

public class KalmanFilter {

    private float[] P = {10, 0, 0, 10}; // P00, P01, P10, P11
    private float[] x = {0, 0}; // X0 = angle, X1 = gyro rate bias
    private float qAngle = 0.5f;
    private float qGyroBias = 0.00001f;
    private float y;
    private float S;
    private float R = 0.5f;
    private float[] K = {0,0};

    public float kalmanFilter(float gyroRate, float accAngle, float dT) { // gyro rate is agular velocity
        x[0] += (gyroRate - x[1]) * dT;
        P[0] = P[0] - dT * ( P[1] + P[2] - P[3] * dT) + qAngle;
        P[1] = P[1] - P[3] * dT;
        P[2] = P[2] - P[3] * dT;
        P[3] = P[3] + qGyroBias;
        y = accAngle - x[0];
        S = P[0] + R;
        K[0] = P[0] / S;
        K[1] = P[2] / S;
        x[0] = x[0] + K[0]*y;
        x[1] = x[1] + K[1]*y;
        P[0] = P[0] - K[0] * P[0];
        P[1] = P[1] - K[0] * P[1];
        P[2] = P[2] - K[1] * P[0];
        P[3] = P[3] - K[1] * P[1];
        return x[0];
    }

    public void reset() {
        x[0] = 0;
    }
}
