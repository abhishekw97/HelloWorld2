package com.example.helloworld;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrganizationNeed extends AppCompatActivity {


    ListView listView;
    DatabaseReference databaseOdata;
    List<Odata> odataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_need);
        databaseOdata= FirebaseDatabase.getInstance().getReference("odata");

        listView=findViewById(R.id.listViewOdata);
        odataList=new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseOdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                odataList.clear();
                for(DataSnapshot odataSnapshot : dataSnapshot.getChildren()){
                    Odata odata= odataSnapshot.getValue(Odata.class);
                    odataList.add(odata);
                }
                OdataList adaptor=new OdataList(OrganizationNeed.this,odataList);
                listView.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
