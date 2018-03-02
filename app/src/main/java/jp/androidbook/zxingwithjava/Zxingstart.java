package jp.androidbook.zxingwithjava;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;

/**
 * 今はZxingで実装しているけど、
 * 今回はZxingで実装せずにBarcordDetectorで実装していこう。
 */

public class Zxingstart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxingstart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        IntentIntegrator integrator = new IntentIntegrator(this);

        CameraManager cameraManager = new CameraManager(this);

        DisplayConfiguration displayconfig = new DisplayConfiguration(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cameraManager.setDisplayConfiguration(displayconfig);

        integrator.initiateScan();

        int numberOfCameras = Camera.getNumberOfCameras();
        Log.d("カメラの数＝",String.valueOf(numberOfCameras));

        // 各カメラの情報を取得
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo caminfo = new CameraInfo();
            Camera.getCameraInfo(i, caminfo);

            // カメラの向きを取得
            int facing = caminfo.facing;

            if (facing == CameraInfo.CAMERA_FACING_BACK) {
                // 後部についているカメラの場合
                Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
                        + ", this is a back-facing camera");
            } else if (facing == CameraInfo.CAMERA_FACING_FRONT) {
                // フロントカメラの場合
                Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
                        + ", this is a front-facing camera");
            } else {
                Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
                        + ", unknown camera?");
            }

            // カメラのOrientation(角度) を取得
            int orient = caminfo.orientation;
            Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
                    + ", orientation=" + Integer.toString(orient));
        }
    }

    //インテントの結果をここで受け取っている。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //リクエストコード、レスポンスコード（リザルトコード）、結果のデータ
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
