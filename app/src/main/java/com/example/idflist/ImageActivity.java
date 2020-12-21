package com.example.idflist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST = 500;
    private Uri imageUri;
    private ImageView imageView;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.profileImage);
        downloadProfileImage();
    }


    public void pickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            uploadImage();
        }
    }
    //מעלה תמונה לפיירבייס
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("uploading");
        pd.show();
        if(imageUri != null){
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("userLogo").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.d("DownloadUrl", url);
                            pd.dismiss();
                            Toast.makeText(ImageActivity.this, "uploading competed", Toast.LENGTH_LONG).show();
                            downloadProfileImage();
                        }
                    });

                }
            });
        }
        pd.dismiss();
    }

    private void downloadProfileImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference()
                .child("userLogo/")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        imageRef.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(ImageActivity.this, "download complete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUserUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}