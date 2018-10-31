package com.example.acer.myplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        final TextView txtView = (TextView) findViewById(R.id.txtContent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView myImageView = (ImageView) findViewById(R.id.imgview);
                //load image, decode it into a bitmap
                Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.barcode);
                //set it as the bitmap for MyImageView
                myImageView.setImageBitmap(myBitmap);
                //set up barcode detector and make sure it is working
                BarcodeDetector detector =
                        new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();
                if(!detector.isOperational()){
                    txtView.setText("Could not set up the detector!");
                    return;
                }
                //create frame from bitmap, pass to detector
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                //return array of barcodes (if multiple barcodes are in the camera's frame)
                SparseArray<Barcode> barcodes = detector.detect(frame);
                //this will only process one barcode, and does not account for if there are no barcodes
                //would have to be changed when we actually need it
                Barcode thisCode = barcodes.valueAt(0);
                txtView.setText(thisCode.rawValue);
            }
        });

    }
}
