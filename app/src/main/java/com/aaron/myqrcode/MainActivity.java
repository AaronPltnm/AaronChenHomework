package com.aaron.myqrcode;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.graphics.PointF;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.AttributeSet;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.opencv.android.InstallCallbackInterface;
        import org.opencv.android.LoaderCallbackInterface;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.android.Utils;
        import org.opencv.aruco.Aruco;
        import org.opencv.core.Mat;
        import org.opencv.imgproc.Imgproc;
        import org.opencv.objdetect.QRCodeDetector;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.URL;
        import java.nio.charset.MalformedInputException;
        import java.util.ArrayList;
        import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textview;
    drawline drawline;
    PointF pointA = new PointF(30,100);
    PointF pointB = new PointF(30,400);



}

    //
    LoaderCallbackInterface loaderCallbackInterface = new LoaderCallbackInterface() {
        @Override
        public void onManagerConnected(int status) {

        }

        @Override
        public void onPackageInstall(int operation, InstallCallbackInterface callback) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView2);
        textview = findViewById(R.id.textView);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        });

        if (!OpenCVLoader.initDebug()){
            boolean sucess =
                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, loaderCallbackInterface);

    }else {
            loaderCallbackInterface.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        test();
}

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String...urls){
            String url = urls[0];
            Bitmap bitmap = null;
            try{
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                final Bitmap finalbitmap = bitmap;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView.setImageBitmap(finalbitmap);
                    }
                });
                return bitmap;
            }catch (MalformedInputException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);



        }
    }
    void decodeQRCode(Bitmap bitmap){
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        String result =qrCodeDetector.detectAndDecode(mat);
        textview.setText(result);
    }

    void test(){
        DownloadImageTask task = new DownloadImageTask();
        task.execute("https://upload.wikimedia.org/wikipedia/zh/f/f6/OKC_Thunder.png");
    }
}