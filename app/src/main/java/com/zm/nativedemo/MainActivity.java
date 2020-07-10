package com.zm.nativedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zm.nativedemo.view.RendererJNI;

public class MainActivity extends AppCompatActivity {
    private final int CONTEXT_CLIENT_VERSION = 3;

    private GLSurfaceView mGLSurfaceView;
    // Used to load the 'native-lib' library on application startup.
    static {
        //System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);

        if (detectOpenGLES30()) {

            mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);

            mGLSurfaceView.setRenderer(new RendererJNI(this));

        } else {

            Log.e("opengles30", "OpenGL ES 3.0 not supported on device.  Exiting...");

            finish();

        }
        setContentView(mGLSurfaceView);
        //setContentView(R.layout.activity_main);

        // Example of a call to a native method
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    @Override

    protected void onResume() {
        // TODO Auto-generated method stub

        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private boolean detectOpenGLES30() {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
