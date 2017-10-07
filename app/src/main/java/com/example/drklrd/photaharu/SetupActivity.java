package com.example.drklrd.photaharu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText displayName;
    private ImageButton profileImage;
    private static final int GALLERY_REQ = 111;
    private Uri mImageUri = null ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseusers;
    private StorageReference mStorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        displayName = (EditText) findViewById(R.id.displayName);
        profileImage = (ImageButton) findViewById(R.id.profileImage);

        mDatabaseusers = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mStorageref = FirebaseStorage.getInstance().getReference().child("profile_image");


    }

    public void profileImage(View view){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,GALLERY_REQ);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 111 && resultCode == RESULT_OK){
            mImageUri = data.getData();
            profileImage.setImageURI(mImageUri);
        }

//        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK){
//            Log.i("Staring Cropping","cropping...");
//            Uri imageUri = data.getData();
//            CropImage.activity(imageUri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1,1)
//                    .start(this);
//        }
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            Log.i("DOne Cropping","cropped...");
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if(resultCode == RESULT_OK){
//                mImageUri = result.getUri();
//                profileImage.setImageURI(mImageUri);
//            }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Exception error = result.getError();
//            }
//        }
    }

    public void doneSetup(View view){
        final String name = displayName.getText().toString().trim();
        final String user_id = mAuth.getCurrentUser().getUid();

        if(!TextUtils.isEmpty(name) && mImageUri !=null){
            StorageReference filepath = mStorageref.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    mDatabaseusers.child(user_id).child("name").setValue(name);
                    mDatabaseusers.child(user_id).child("image").setValue(downloadUrl);
                }
            });

        }
    }
}
