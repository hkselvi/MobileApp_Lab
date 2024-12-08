package com.example.week9;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText txtUrl;
    Button btnDownload;
    ImageView imgView;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        txtUrl = findViewById(R.id.txtURL);
        imgView = findViewById(R.id.imgView);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

                }
                /*String fileName = "temp.jpg";
                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
                        + "/" + fileName;

                downloadFile(txtUrl.getText().toString(), imagePath);
                preview(imagePath);*/

                else {
                    // DownloadTask backgroundTask = new DownloadTask();
                    // String[] urls = new String[1];
                    // urls[0] = txtUrl.getText().toString();
                    //
                    // backgroundTask.execute(urls);


                    Thread backgroundThread = new Thread(new DownloadRunnable(txtUrl.getText().toString()));
                    backgroundThread.start();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){
            /*String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
                    + "/" + fileName;

            downloadFile(txtUrl.getText().toString(), imagePath);
            preview(imagePath);*/
            //DownloadTask backgroundTask = new DownloadTask();
            //String[] urls = new String[1];
            //urls[0] = txtUrl.getText().toString();

            //backgroundTask.execute(urls);

            Thread backgroundThread = new Thread(new DownloadRunnable(txtUrl.getText().toString()));
            backgroundThread.start();

        }
        else {
            Toast.makeText(this, "External storage permission is not granted", Toast.LENGTH_SHORT).show();
        }
    }



    void downloadFile(String strURL, String imagePath){
        try {
            URL strUrl = new URL(strURL);
            URLConnection connection = strUrl.openConnection();
            connection.connect();

            InputStream inputStream = new BufferedInputStream(strUrl.openStream(), 8192);
            OutputStream output = new FileOutputStream(imagePath);
            byte data[] = new byte[1024]; // how many bytes to read or write
            int count;
            while ((count = inputStream.read(data)) != -1) { //if not -1 th loop will continue
                output.write(data, 0, count); // we do not need any off's so off = 0!!
            }
            output.flush();
            output.close();
            inputStream.close(); // when yo do not need to use streams anymore, yo have to close your streams!!
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap rescaleBitmap(String imagePath){
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        int rescaleWidth = 400;
        int rescaleHeight = (int) ( (imageHeight * rescaleWidth)/ imageWidth);
        Bitmap bitmap = Bitmap.createScaledBitmap(image, rescaleWidth, rescaleHeight, false);
        imgView.setImageBitmap(bitmap);
        return bitmap;
    }


    void preview(String imagePath ){
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        int rescaleWidth = 400;
        int rescaleHeight = (int) ( (imageHeight * rescaleWidth)/ imageWidth);
        Bitmap bitmap = Bitmap.createScaledBitmap(image, rescaleWidth, rescaleHeight, false);
        imgView.setImageBitmap(bitmap);
    }

    class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMax(100);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }


        protected Bitmap doInBackground(String... urls) {
            String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            downloadFile(urls[0], imagePath + "/" + fileName);

            return rescaleBitmap(imagePath + "/" + fileName);
        }

        protected  void onPostExecute(Bitmap bitmap) {
            imgView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }

    class DownloadRunnable implements Runnable {
        String url;

        public DownloadRunnable(String url){
            this.url = url;
        }

        @Override
        public void run() {
            String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            downloadFile(url, imagePath + "/" + fileName);
            Bitmap bitmap = rescaleBitmap(imagePath + "/" + fileName);

            runOnUiThread(new UpdateBitmap(bitmap));

        }

        class UpdateBitmap implements Runnable {
            Bitmap bitmap;

            public UpdateBitmap(Bitmap bitmap){
                this.bitmap = bitmap;
            }

            @Override
            public void run() {
                imgView.setImageBitmap(bitmap);
            }
        }
    }

}