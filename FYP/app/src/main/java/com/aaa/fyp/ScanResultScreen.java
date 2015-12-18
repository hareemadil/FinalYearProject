package com.aaa.fyp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SlideMenu.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
//import com.models.NavItem;
//import com.slidingmenu_tabhostviewpager.*;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class ScanResultScreen extends BaseActivity {

    ImageView scanned;
    TextView bc;
    TextView f;
    String Barcode;
    String format;
    TextView d;

   @Override
    public void onCreate(Bundle state) {

        super.onCreate(state);
       // setContentView(R.layout.scan_screen_with_button);

        getLayoutInflater().inflate(R.layout.scan_screen_with_button, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        ViewGroup layout = (ViewGroup) findViewById(R.id.scanScreenWithButton);
     //   layout.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //layout.setOrientation(LinearLayout.VERTICAL);

//        setContentView(layout);
      // getLayoutInflater().inflate((XmlPullParser) layout, frameLayout);

       Intent prevScreen = getIntent(); // gets the previously created intent

        Barcode=prevScreen.getStringExtra("barcode");
        bc= (TextView)findViewById(R.id.barcode_label);
        bc.setText(Barcode);

        format=prevScreen.getStringExtra("format");
        f=(TextView) findViewById(R.id.format_label);
        f.setText(format);


        d=(TextView)findViewById(R.id.date_label);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        d.setText(formattedDate);
        Bitmap bitmap = null;
        ImageView iv = new ImageView(this);
        try {
                    if(format.equals("AZTEC")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.AZTEC, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("CODABAR")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.CODABAR, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("CODE_39")){

                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.CODE_39, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("CODE_93")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.CODE_93, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("CODE_128")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.CODE_128, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("DATA_MATRIX")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.DATA_MATRIX, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("EAN_8")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.EAN_8, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("EAN_13")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.EAN_13, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("ITF")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.ITF, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("MAXICODE")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.MAXICODE, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("PDF_417")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.PDF_417, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("QR_CODE")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.QR_CODE, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("RSS_14")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.RSS_14, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("RSS_EXPANDED")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.RSS_EXPANDED, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("UPC_A")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.UPC_A, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("UPC_E")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.UPC_E, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }
                    else if(format.equals("UPC_EAN_EXTENSION")){
                        bitmap = encodeAsBitmap(Barcode, BarcodeFormat.UPC_EAN_EXTENSION, 600, 300);
                        iv.setImageBitmap(bitmap);
                    }


        } catch (WriterException e) {
            e.printStackTrace();
        }

        layout.addView(iv);




    }

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }



    //pcall
    public void displayResults(View view){
     //   Intent newScreen = new Intent("com.database.ListActivityClass");
       // newScreen.putExtra("Product", Barcode);
        finish();
       // startActivity(newScreen);
    }

}
