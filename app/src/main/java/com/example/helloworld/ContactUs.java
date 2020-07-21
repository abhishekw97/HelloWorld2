package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    EditText Cname,CEmail,Cfeedback,CMobile;
    Button btnSubmit;
    DatabaseReference databaseCdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Cname=findViewById(R.id.txtName);
        databaseCdata=FirebaseDatabase.getInstance().getReference("contactData");
        CEmail=findViewById(R.id.txtEmail);
        Cfeedback=findViewById(R.id.txtFeedback);
        CMobile=findViewById(R.id.txtMobileNo);
        btnSubmit=findViewById(R.id.btnSubmit);
    }

    public void Submit(View view) {
        String name = Cname.getText().toString().trim();
        String Email=CEmail.getText().toString().trim();
        String Feedback=Cfeedback.getText().toString().trim();
        String Contact=CMobile.getText().toString().trim();

        if(!TextUtils.isEmpty(name))
        {
            String id=databaseCdata.push().getKey();
            Contactdata contactdata=new Contactdata(id,name,Email,Feedback,Contact);
            databaseCdata.child(id).setValue(contactdata);
            Toast.makeText(this,"Thank For Giving Feedback",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"You should Entre Name",Toast.LENGTH_SHORT).show();
        }
    }
}

