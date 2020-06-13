package com.example.atm003;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasswd;
    private int REQUEST_CODE_CAMERA = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        int permission = ContextCompat.checkSelfPermission(this , Manifest.permission.CAMERA);
//        if( permission == PackageManager.PERMISSION_GRANTED){
//            takePhoto();
//        }else{
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
//        }

        edUserid = findViewById(R.id.ed_userid);
        edPasswd = findViewById(R.id.ed_passwd);

        //CheckBox remember ID
        CheckBox cbRemId = findViewById(R.id.cb_rememberId);
        cbRemId.setChecked(getSharedPreferences("atm",MODE_PRIVATE).getBoolean("REMEMBER_ID",false));
        cbRemId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("atm",MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_ID",isChecked)
                        .apply();
            }
        });
        String remUserid = getSharedPreferences("atm",MODE_PRIVATE)
                .getString("USER_ID","");
        edUserid.setText(remUserid);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CODE_CAMERA){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                takePhoto();
//            }
//        }
//    }
//
//    private void takePhoto() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivity(intent);
//    }

    public void login(View view){
        final String userid = edUserid.getText().toString();
        final String passwd = edPasswd.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String wd = dataSnapshot.getValue().toString();
                if(wd.equals(passwd)){
                    Boolean remember = getSharedPreferences("atm",MODE_PRIVATE)
                            .getBoolean("REMEMBER_ID",false);
                    if(remember){
                        getSharedPreferences("atm",MODE_PRIVATE)
                                .edit()
                                .putString("USER_ID",userid)
                                .apply();
                    }
                    setResult(RESULT_OK);
                    finish();
                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Login result")
                            .setMessage("No user id or wrong password")
                            .setPositiveButton("OK",null)
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cancel(View view){
        finish();
    }
}
