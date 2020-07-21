package com.example.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class News extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private DatabaseReference mDatabseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabseRef=FirebaseDatabase.getInstance().getReference("Data");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Member, ViewHolder>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, ViewHolder>(
                        Member.class,
                        R.layout.image_items,
                        ViewHolder.class,
                        mDatabseRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Member member, int i) {
                       viewHolder.setdeatils(getApplicationContext(),member.getTitle(),member.getImage());
                    }

                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
