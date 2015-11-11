package com.aaa.barcode;

/**
 * Created by Asif on 9/10/2015.
 */


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.database.DB;

        import java.util.ArrayList;


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


    EditText barcodeText;
    TextView barcodeTextView;
    String ProductBarcode;

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
        barcodeText = (EditText) findViewById(R.id.barodeText);

    }

    public void getProducts(View view){
        Intent newScreen = new Intent("com.database.ListActivityClass");
        finish();
        newScreen.putExtra("Product", ProductBarcode);
        startActivity(newScreen);

    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.release();
    }



    private class OnDecoded implements CaptureHandler.OnDecodedCallback {
        @Override
        public void onDecoded(String decodedData) {
           //Toast.makeText(CaptureActivity.this, decodedData, Toast.LENGTH_SHORT).show();
            //Toast.makeText(CaptureActivity.this,"WOW scanned", Toast.LENGTH_SHORT).show();
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(decodedData);


            try{

                ProductBarcode = decodedData;
                barcodeTextView = (TextView)barcodeText;
                barcodeTextView.setText(decodedData);

            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("-----------------------------------------------------------------------");
        }
    }
}

