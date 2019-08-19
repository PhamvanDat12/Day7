package com.datpv.day7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnCall,btnCamera,btnContact,btnBrowser,btnCalllog, btnGallery,btnDiaPlad;
    EditText editText;
    private static int REQUESt_CODE_CAMERA=69;
   private static int REQUESt_CODE_WEB=68;
    private static final int CALL_PERMISION=0;
    private static final int PICK_CONTACT=1;
    Intent intent;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCall=findViewById(R.id.call);
        btnCamera = findViewById(R.id.camera);
        btnContact=findViewById(R.id.caontact);
        btnBrowser=findViewById(R.id.browser);
        btnCalllog=findViewById(R.id.callog);
        btnGallery=findViewById(R.id.galery);
        btnDiaPlad=findViewById(R.id.diaplad);
        btnCall.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnContact.setOnClickListener(this);
        btnBrowser.setOnClickListener(this);
        btnCalllog.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnDiaPlad.setOnClickListener(this);
        editText=findViewById(R.id.edt_phone);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.call:
                requestCall();
                break;
            case R.id.camera:
             intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUESt_CODE_CAMERA);
                break;
            case R.id.caontact:
                intent= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,PICK_CONTACT);

                break;
            case R.id.browser:
//                intent= new Intent();
//                intent.setAction(Intent.CATEGORY_APP_BROWSER);
//                intent.setData(Uri.parse("https://google.com"));
                Uri uri= Uri.parse("https://google.com");
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
//                intent= new Intent(MediaBrowser.EXTRA_PAGE);
//                startActivityForResult(intent,REQUESt_CODE_WEB);
                startActivity(intent);
                break;
            case R.id.callog:
                intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setType(CallLog.Calls.CONTENT_TYPE);
                startActivity(intent);
                break;
            case R.id.galery:
                intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
            case R.id.diaplad:
                intent= new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:012222222"));
                startActivity(intent);
                break;


        }

    }

    private void requestPermission(){//xin cấp quyền
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISION);
            }

        }

    }

    @Override
//lắng nghe người dùng
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if(requestCode==CALL_PERMISION){
          if(grantResults.length ==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
              //grandresults: mảng trả về
              Toast.makeText(this,"Quyền đã bị từ chối",Toast.LENGTH_SHORT).show();
          }
      }
    }

    private void requestCall() {
        requestPermission();
        phone=editText.getText().toString();
        intent = new Intent();
        String abc="tel:"+phone;
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse(abc));
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"ứng dụng chưa đươc cấp quyền",Toast.LENGTH_SHORT).show();
            return;
        }


        startActivity(intent);
    }
}
