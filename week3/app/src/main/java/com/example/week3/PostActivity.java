package com.example.week3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PostActivity extends AppCompatActivity {

    ImageView img;
    TextView txtMessage;
    static final int CAPTURE_IMAGE=1;
    ImageButton btnOk;
    ImageButton btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);*/
        setContentView(R.layout.activity_post);

        txtMessage = findViewById(R.id.txtMessage);
        img = findViewById(R.id.imageView);
        //open camera and get a static var.

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE);
            }
        });

        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();  //resim ve mesajı mainviewdaki liste göndereceğiz
                bundle.putParcelable("bitmap", ((BitmapDrawable)img.getDrawable()).getBitmap());
                bundle.putCharSequence("msg", txtMessage.getText());
                intent.putExtras(bundle);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        //need data from camera
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE && resultCode == AppCompatActivity.RESULT_OK){
            Bundle bundle = data.getExtras();  //for represent data
            Bitmap image = (Bitmap) bundle.get("data");
            img.setImageBitmap(image);
        }
    }
}