package com.aaa.barcode;

/**
 * Created by Asif on 9/10/2015.
 */


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.widget.Toast;
        import com.aaa.barcode.CameraManager;
        import com.aaa.barcode.CaptureHandler;
        import com.aaa.barcode.PreviewCallback;
        import com.aaa.barcode.BoundingView;
        import com.aaa.barcode.CameraPreviewView;
        import com.database.DB;
        import com.database.CustomListView;


/**
 * Capture activity (camera barcode activity)
 */
public class CaptureActivity extends Activity {
    /**
     * Camera preview view
     */
    private CameraPreviewView cameraPreview;
    /**
     * Camera manager
     */
    private CameraManager cameraManager;
    /**
     * Capture handler
     */
    private Handler captureHandler;

    DB dbObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        // Create an instance of Camera
        cameraManager = new CameraManager();
        captureHandler = new CaptureHandler(cameraManager, this, new OnDecoded());
        //requesting next frame for decoding
        cameraManager.requestNextFrame(new PreviewCallback(captureHandler, cameraManager));

        // Create our Preview view and set it as the content of our activity.
        cameraPreview = (CameraPreviewView) findViewById(R.id.camera_preview);
        cameraPreview.setCameraManager(cameraManager);
        ((BoundingView) findViewById(R.id.bounding_view)).setCameraManager(cameraManager);

        try{
            dbObject=  new DB(this);

        }catch(Exception e)
        {e.printStackTrace();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.release();
    }

    private class OnDecoded implements CaptureHandler.OnDecodedCallback {
        @Override
        public void onDecoded(String decodedData) {
            Toast.makeText(CaptureActivity.this, decodedData, Toast.LENGTH_SHORT).show();
            Toast.makeText(CaptureActivity.this,"WOW scanned", Toast.LENGTH_SHORT).show();
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(decodedData);
            //will only work for aquafina barcode
            dbObject.pullData(decodedData);
            System.out.println("-----------------------------------------------------------------------");
            try{
              //  Intent intent= new Intent(this, ListView.class);
               // startActivity(intent);
               // setContentView(R.layout.activity_list_view);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

