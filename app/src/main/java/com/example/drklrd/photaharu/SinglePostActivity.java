package com.example.drklrd.photaharu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SinglePostActivity extends AppCompatActivity {

    private String post_key = null;
    private DatabaseReference mDatabase;
    private ImageView singleImage;
    private TextView singleTitle;
    private TextView singleDescription;
    private Button deletePostButton;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        post_key = getIntent().getExtras().getString("PostId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photaharu");

        singleTitle = (TextView) findViewById(R.id.singleTitle);
        singleDescription = (TextView) findViewById(R.id.singleDescription);
        singleImage = (ImageView) findViewById(R.id.singleImage);

        mAuth = FirebaseAuth.getInstance();
        deletePostButton = (Button) findViewById(R.id.singleDelete);
        deletePostButton.setVisibility(View.INVISIBLE);

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("description").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

                singleTitle.setText(post_title);
                singleDescription.setText(post_desc);
                Picasso.with(SinglePostActivity.this).load(post_image).into(singleImage);
                if(mAuth.getCurrentUser().getUid().equals(post_uid)){
                    deletePostButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deletePost(View view){
        mDatabase.child(post_key).removeValue();
        Intent mainIntent = new Intent(SinglePostActivity.this,MainActivity.class);
        startActivity(mainIntent);
    }
}
