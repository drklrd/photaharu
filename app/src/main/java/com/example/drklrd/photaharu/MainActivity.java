package com.example.drklrd.photaharu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView photaHaruList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photaHaruList = (RecyclerView) findViewById(R.id.photoHaruList);
        photaHaruList.setHasFixedSize(true);
        photaHaruList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photaharu");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseRecyclerAdapter<Phota,PhotaViewHolder> FBRA = new FirebaseRecyclerAdapter<Phota, PhotaViewHolder>(
//                Phota.class,
//                R.layout.photo_row,
//                PhotaViewHolder.class,
//                mDatabase
//        ) {
//            @Override
//            protected void populateViewHolder(PhotaViewHolder viewHolder, Phota model, int position) {
//                viewHolder.setTitle(model.getTitle());
//                viewHolder.setDescription(model.getDescription());
//            }
//        };
//        photaHaruList.setAdapter(FBRA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class PhotaViewHolder extends RecyclerView.ViewHolder{

        public PhotaViewHolder(View itemView){
            super(itemView);
            View mView = itemView;
        }

        public void setTitle(String title){
            TextView postTitle = (TextView) itemView.findViewById(R.id.imageTitle);
            postTitle.setText(title);
        }

        public void setDescription(String description){
            TextView postDescription = (TextView) itemView.findViewById(R.id.imageDescription);
            postDescription.setText(description);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.addPhoto){
            Intent intent = new Intent(MainActivity.this,PostActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
