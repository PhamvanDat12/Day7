package com.datpv.day7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
  private Button btn_chonanh, btn_share;
  private ImageView img;
  private static int GALLERY_REQUEST=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai2_main);
      btn_chonanh=findViewById(R.id.btn_chonanh);
      btn_share=findViewById(R.id.btn_share);
      img=findViewById(R.id.img_anh);
      btn_chonanh.setOnClickListener(this);
       btn_share.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           case  R.id.btn_chonanh :
              Intent intent = new Intent(Intent.ACTION_PICK);
              intent.setType("image/*");
              startActivityForResult(intent,GALLERY_REQUEST);

            break;
            case R.id.btn_share:
                Intent intent1= new Intent(Intent.ACTION_SEND);
                intent1.setType("plain/text");
                intent1.putExtra(Intent.EXTRA_EMAIL,new String[]{"------"});
                intent1.putExtra(Intent.EXTRA_SUBJECT,"-----");
                startActivity(Intent.createChooser(intent1,"Contact Uss"));
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            try {
                final Uri uri=data.getData();
                final InputStream imgStream=getContentResolver().openInputStream(uri);
                final Bitmap selectedImage= BitmapFactory.decodeStream(imgStream);
                img.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }else {

        }
    }
}
