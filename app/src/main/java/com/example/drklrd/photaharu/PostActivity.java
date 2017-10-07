package com.example.drklrd.photaharu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST =222;
    private Uri uri = null;
    private ImageButton imageButton;
    private EditText imageTitle;
    private EditText imageDescription;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageTitle = (EditText) findViewById(R.id.imageTitle);
        imageDescription = (EditText) findViewById(R.id.imageDescription);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void pickImage(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 222 && resultCode == RESULT_OK){
            uri = data.getData();
            imageButton = (ImageButton) findViewById(R.id.selectedImage);
            imageButton.setImageURI(uri);
        }
    }

    public void postNew(View view){
        String title = imageTitle.getText().toString().trim();
        String description = imageDescription.getText().toString().trim();
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)){
           StorageReference filePath = storageReference.child("PostImage").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"Successfully uploaded !",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
