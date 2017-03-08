package com.example.natha_000.a2_comp_project.Graphing_3D;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.example.natha_000.a2_comp_project.R;
import com.google.vr.sdk.base.AndroidCompat;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;

/**
 * A class suitable for the drawing of the 3D graph within virtual reality
 */
public class Main3DVRActivity extends GvrActivity implements GvrView.StereoRenderer {

    protected float[] modelCube;
    protected float[] modelPosition;

    private static final String TAG = "TreasureHuntActivity";

    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 100.0f;

    private static final float CAMERA_Z = 0.01f;
    private static final float TIME_DELTA = 0.3f;

    private static final float YAW_LIMIT = 0.12f;
    private static final float PITCH_LIMIT = 0.12f;

    private static final int COORDS_PER_VERTEX = 3;

    // The light will be kept in a position just above the user.
    private static final float[] LIGHT_POS_IN_WORLD_SPACE = new float[] {0.0f, 2.0f, 0.0f, 1.0f};

    // Convenience vector for extracting the position from a matrix via multiplication.
    private static final float[] POS_MATRIX_MULTIPLY_VEC = {0, 0, 0, 1.0f};

    private static final float MIN_MODEL_DISTANCE = 3.0f;
    private static final float MAX_MODEL_DISTANCE = 7.0f;


    private final float[] lightPosInEyeSpace = new float[4];

    private FloatBuffer surfaceVertices;
    private FloatBuffer surfaceColors;
    private FloatBuffer surfaceNormals;

    private HeadTransform ht;

    private int surfaceProgram;

    private int surfacePositionParam;
    private int surfaceNormalParam;
    private int surfaceColorParam;
    private int surfaceModelParam;
    private int surfaceModelViewParam;
    private int surfaceModelViewProjectionParam;
    private int surfaceLightPosParam;

    private float[] camera;
    private float[] view;
    private float[] headView;
    private float[] modelViewProjection;
    private float[] modelView;
    private float[] modelSurface;

    private float[] tempPosition;
    private float[] headRotation;

    private float objectDistance = MAX_MODEL_DISTANCE / 2.0f;
    private float surfaceDepth = 20f;

    private Vibrator vibrator;


    /**
     * Converts a raw text file, saved as a resource, into an OpenGL ES shader.
     *
     * @param type The type of shader I will be creating.
     * @param resId The resource ID of the raw text file about to be turned into a shader.
     * @return The shader object handler.
     */
    private int loadGLShader(int type, int resId) {
        String code = readRawTextFile(resId);
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0) {
            Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }

        if (shader == 0) {
            throw new RuntimeException("Error creating shader.");
        }

        return shader;
    }

    /**
     * Checks if there's an error inside of OpenGL ES, and if so what that error is.
     *
     * @param label Label to report in case of error.
     */
    private static void checkGLError(String label) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, label + ": glError " + error);
            throw new RuntimeException(label + ": glError " + error);
        }
    }

    /**
     * Sets the view to our GvrView and initializes the transformation matrices I will use
     * to render the scene.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Intemediary3D.EXTRA_MESSAGE);

        try {
            WorldLayoutData.setParameters(-10f,10f,-10f,10f,61,61,10f);
            WorldLayoutData.setfunction(message);
            WorldLayoutData.generate();
        } catch (IOException e) {
            Log.e("Parameter Data", e.toString());
        }

        initializeGvrView();

        modelCube = new float[16];
        camera = new float[16];
        view = new float[16];
        modelViewProjection = new float[16];
        modelView = new float[16];
        modelSurface = new float[16];
        tempPosition = new float[4];
        // Model first appears directly in front of user.
        modelPosition = new float[] {0.0f, 0.0f, -MAX_MODEL_DISTANCE / 2.0f};
        headRotation = new float[4];
        headView = new float[16];
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void initializeGvrView() {
        setContentView(R.layout.activity_main3dvr);

        GvrView gvrView = (GvrView) findViewById(R.id.gvr_view);
        gvrView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);

        gvrView.setRenderer(this);
        gvrView.setTransitionViewEnabled(true);

        // Enable Cardboard-trigger feedback with Daydream headsets.
        gvrView.enableCardboardTriggerEmulation();

        if (gvrView.setAsyncReprojectionEnabled(true)) {
            // Async reprojection decouples the app framerate from the display framerate,
            // allowing immersive interaction even at the throttled clockrates set by
            // sustained performance mode.
            AndroidCompat.setSustainedPerformanceMode(this, true);
        }

        setGvrView(gvrView);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRendererShutdown() {
        WorldLayoutData.reset();
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
    }


    private void defineBuffers() {
        Log.i(TAG,Float.toString(WorldLayoutData.SURFACE_COORDS[0])+ ", " + Float.toString(WorldLayoutData.SURFACE_COORDS[1]) + ", " + Float.toString(WorldLayoutData.SURFACE_COORDS[2]));
        Log.i(TAG,Float.toString(WorldLayoutData.getOffset()[0])+ ", " + Float.toString(WorldLayoutData.getOffset()[1]) + ", " + Float.toString(WorldLayoutData.getOffset()[2]));

        ByteBuffer bbSurfaceVertices = ByteBuffer.allocateDirect(WorldLayoutData.SURFACE_COORDS.length * 4);
        bbSurfaceVertices.order(ByteOrder.nativeOrder());
        surfaceVertices = bbSurfaceVertices.asFloatBuffer();
        surfaceVertices.put(WorldLayoutData.SURFACE_COORDS);
        surfaceVertices.position(0);

        ByteBuffer bbSurfaceNormals = ByteBuffer.allocateDirect(WorldLayoutData.SURFACE_NORMALS.length * 4);
        bbSurfaceNormals.order(ByteOrder.nativeOrder());
        surfaceNormals = bbSurfaceNormals.asFloatBuffer();
        surfaceNormals.put(WorldLayoutData.SURFACE_NORMALS);
        surfaceNormals.position(0);

        ByteBuffer bbSurfaceColors = ByteBuffer.allocateDirect(WorldLayoutData.SURFACE_COLORS.length * 4);
        bbSurfaceColors.order(ByteOrder.nativeOrder());
        surfaceColors = bbSurfaceColors.asFloatBuffer();
        surfaceColors.put(WorldLayoutData.SURFACE_COLORS);
        surfaceColors.position(0);
    }

    /**
     * Creates the buffers used to store information about the 3D world.
     *
     * OpenGL doesn't use Java arrays, but rather needs data in a format it can understand.
     * Hence I use ByteBuffers.
     *
     * @param config The EGL configuration used when creating the surface.
     */
    @Override
    public void onSurfaceCreated(EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated");
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f); // Dark background so text shows up well.

        // Defines the buffers responsible for drawing the surface
        defineBuffers();

        int vertexShader = loadGLShader(GLES20.GL_VERTEX_SHADER, R.raw.light_vertex);
        int gridShader = loadGLShader(GLES20.GL_FRAGMENT_SHADER, R.raw.grid_fragment);
        int passthroughShader = loadGLShader(GLES20.GL_FRAGMENT_SHADER, R.raw.passthrough_fragment);

        surfaceProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(surfaceProgram, vertexShader);
        GLES20.glAttachShader(surfaceProgram, passthroughShader);
        GLES20.glLinkProgram(surfaceProgram);
        GLES20.glUseProgram(surfaceProgram);

        checkGLError("Surface program");

        surfaceModelParam = GLES20.glGetUniformLocation(surfaceProgram, "u_Model");
        surfaceModelViewParam = GLES20.glGetUniformLocation(surfaceProgram, "u_MVMatrix");
        surfaceModelViewProjectionParam = GLES20.glGetUniformLocation(surfaceProgram, "u_MVP");
        surfaceLightPosParam = GLES20.glGetUniformLocation(surfaceProgram, "u_LightPos");

        surfacePositionParam = GLES20.glGetAttribLocation(surfaceProgram, "a_Position");
        surfaceNormalParam = GLES20.glGetAttribLocation(surfaceProgram, "a_Normal");
        surfaceColorParam = GLES20.glGetAttribLocation(surfaceProgram, "a_Color");

        checkGLError("Surface program params");

        Matrix.setIdentityM(modelSurface, 0);
        Matrix.translateM(modelSurface, 0, 0, -surfaceDepth, 0); // Surface appears below user.

        checkGLError("onSurfaceCreated");
    }

    /**
     * Converts a raw text file into a string.
     *
     * @param resId The resource ID of the raw text file about to be turned into a shader.
     * @return The context of the text file, or null in case of error.
     */
    private String readRawTextFile(int resId) {
        InputStream inputStream = getResources().openRawResource(resId);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Prepares OpenGL ES before I draw a frame.
     *
     * @param headTransform The head transformation in the new frame.
     */
    @Override
    public void onNewFrame(HeadTransform headTransform) {
//        setCubeRotation();
//        Log.i(TAG, "onNewFrame");
        ht = headTransform;


        // Build the camera matrix and apply it to the ModelView.
        Matrix.setLookAtM(camera, 0, 0.0f, 0.0f, CAMERA_Z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        headTransform.getHeadView(headView, 0);

        // Update the 3d audio engine with the most recent head rotation.
        headTransform.getQuaternion(headRotation, 0);

        checkGLError("onReadyToDraw");
    }


    /**
     * Draws a frame for an eye.
     *
     * @param eye The eye to render. Includes all required transformations.
     */
    @Override
    public void onDrawEye(Eye eye) {

        if (WorldLayoutData.isFlying()) {
            WorldLayoutData.fly(ht);
            try {
                WorldLayoutData.generate();
                defineBuffers();
            } catch (IOException e) {
                Log.e(TAG, "An error has occurred trying to generate new co-ordinates");
            }
        }


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        checkGLError("colorParam");

        // Apply the eye transformation to the camera.
        Matrix.multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);

        // Set the position of the light
        Matrix.multiplyMV(lightPosInEyeSpace, 0, view, 0, LIGHT_POS_IN_WORLD_SPACE, 0);

        // Build the ModelView and ModelViewProjection matrices
        // for calculating cube position and light.
        float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);
//        Matrix.multiplyMM(modelView, 0, view, 0, modelCube, 0);
//        Matrix.multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);
//    drawCube();

        // Set modelView for the surface, so I draw surface in the correct location
        Matrix.multiplyMM(modelView, 0, view, 0, modelSurface, 0);
        Matrix.multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);
        drawSurface();
    }

    @Override
    public void onFinishFrame(Viewport viewport) {}

    /**
     * Draw the surface.
     *
     * <p>This feeds in data for the surface into the shader. Note that this doesn't feed in data about
     * position of the light, so if I rewrite our code to draw the surface first, the lighting might
     * look strange.
     */
    public void drawSurface() {
        GLES20.glUseProgram(surfaceProgram);

        // Set ModelView, MVP, position, normals, and color.
        GLES20.glUniform3fv(surfaceLightPosParam, 1, lightPosInEyeSpace, 0);
        GLES20.glUniformMatrix4fv(surfaceModelParam, 1, false, modelSurface, 0);
        GLES20.glUniformMatrix4fv(surfaceModelViewParam, 1, false, modelView, 0);
        GLES20.glUniformMatrix4fv(surfaceModelViewProjectionParam, 1, false, modelViewProjection, 0);
        GLES20.glVertexAttribPointer(
                surfacePositionParam, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, surfaceVertices);
        GLES20.glVertexAttribPointer(surfaceNormalParam, 3, GLES20.GL_FLOAT, false, 0, surfaceNormals);
        GLES20.glVertexAttribPointer(surfaceColorParam, 4, GLES20.GL_FLOAT, false, 0, surfaceColors);

        GLES20.glEnableVertexAttribArray(surfacePositionParam);
        GLES20.glEnableVertexAttribArray(surfaceNormalParam);
        GLES20.glEnableVertexAttribArray(surfaceColorParam);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, WorldLayoutData.SURFACE_COORDS.length/3);

        GLES20.glDisableVertexAttribArray(surfacePositionParam);
        GLES20.glDisableVertexAttribArray(surfaceNormalParam);
        GLES20.glDisableVertexAttribArray(surfaceColorParam);

        checkGLError("drawing surface");
    }

    /**
     * Called when the Cardboard trigger is pulled.
     */
    @Override
    public void onCardboardTrigger() {
        WorldLayoutData.toggleFlying();
//        WorldLayoutData.changeResolution();
        Log.i(TAG, "onCardboardTrigger: " + Boolean.toString(WorldLayoutData.isFlying()));

        // Always give user feedback.
        vibrator.vibrate(50);
    }


}
